package com.moodly.payment.toss;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moodly.common.exception.BaseException;
import com.moodly.common.exception.GlobalErrorCode;
import com.moodly.payment.config.TossPaymentsProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class TossPaymentsClient {

    private final RestTemplate tossRestTemplate;
    private final TossPaymentsProperties properties;
    private final ObjectMapper objectMapper;

    public TossConfirmResult confirm(String paymentKey, String orderId, BigDecimal amount) {
        if (properties.getSecretKey() == null || properties.getSecretKey().isBlank()) {
            throw new BaseException(GlobalErrorCode.PAYMENT_CONFIG_ERROR, "Toss secret key is not configured");
        }

        long amountLong = amount.longValue();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String auth = Base64.getEncoder().encodeToString(
                (properties.getSecretKey() + ":").getBytes(StandardCharsets.UTF_8)
        );
        headers.set(HttpHeaders.AUTHORIZATION, "Basic " + auth);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("paymentKey", paymentKey);
        body.put("orderId", orderId);
        body.put("amount", amountLong);

        try {
            String json = objectMapper.writeValueAsString(body);
            HttpEntity<String> entity = new HttpEntity<>(json, headers);
            String url = properties.getBaseUrl().replaceAll("/$", "") + "/v1/payments/confirm";
            ResponseEntity<String> response = tossRestTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    String.class
            );
            String respBody = response.getBody();
            JsonNode root = objectMapper.readTree(respBody != null ? respBody : "{}");
            log.info("[Toss] confirm success orderId={}", orderId);
            return TossConfirmResult.success(root, respBody);
        } catch (HttpStatusCodeException e) {
            String resp = e.getResponseBodyAsString(StandardCharsets.UTF_8);
            log.warn("[Toss] confirm failed orderId={} status={} body={}", orderId, e.getStatusCode(), resp);
            try {
                JsonNode err = objectMapper.readTree(resp.isEmpty() ? "{}" : resp);
                String code = err.path("code").asText("UNKNOWN");
                String message = err.path("message").asText(e.getMessage());
                return TossConfirmResult.failure(code, message, resp);
            } catch (Exception ex) {
                return TossConfirmResult.failure("UNKNOWN", e.getMessage(), resp);
            }
        } catch (Exception e) {
            log.error("[Toss] confirm error orderId={}", orderId, e);
            throw new BaseException(GlobalErrorCode.PAYMENT_GATEWAY_ERROR, e.getMessage(), e);
        }
    }

    /**
     * 전액 결제 취소 — POST /v1/payments/{paymentKey}/cancel
     */
    public TossCancelResult cancel(String paymentKey, String cancelReason) {
        if (properties.getSecretKey() == null || properties.getSecretKey().isBlank()) {
            throw new BaseException(GlobalErrorCode.PAYMENT_CONFIG_ERROR, "Toss secret key is not configured");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String auth = Base64.getEncoder().encodeToString(
                (properties.getSecretKey() + ":").getBytes(StandardCharsets.UTF_8)
        );
        headers.set(HttpHeaders.AUTHORIZATION, "Basic " + auth);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("cancelReason", cancelReason != null && !cancelReason.isBlank() ? cancelReason : "고객 요청 취소");

        try {
            String json = objectMapper.writeValueAsString(body);
            HttpEntity<String> entity = new HttpEntity<>(json, headers);
            String base = properties.getBaseUrl().replaceAll("/$", "");
            String url = base + "/v1/payments/" + paymentKey + "/cancel";
            ResponseEntity<String> response = tossRestTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    String.class
            );
            String respBody = response.getBody() != null ? response.getBody() : "";
            JsonNode root = objectMapper.readTree(respBody.isEmpty() ? "{}" : respBody);
            log.info("[Toss] cancel success paymentKey={}", paymentKey);
            return TossCancelResult.ok(
                    respBody,
                    extractRefundTransactionKey(root),
                    extractCancelAmount(root)
            );
        } catch (HttpStatusCodeException e) {
            String resp = e.getResponseBodyAsString(StandardCharsets.UTF_8);
            log.warn("[Toss] cancel failed paymentKey={} status={} body={}", paymentKey, e.getStatusCode(), resp);
            try {
                JsonNode err = objectMapper.readTree(resp.isEmpty() ? "{}" : resp);
                String code = err.path("code").asText("UNKNOWN");
                String message = err.path("message").asText(e.getMessage());
                boolean already = isAlreadyCanceledTossError(code, message);
                String refundTx = null;
                BigDecimal cancelAmt = null;
                if (already && !resp.isBlank()) {
                    try {
                        JsonNode root = objectMapper.readTree(resp);
                        refundTx = extractRefundTransactionKey(root);
                        cancelAmt = extractCancelAmount(root);
                    } catch (Exception ignored) {
                        // 오류 본문에 결제 객체가 없을 수 있음
                    }
                }
                return TossCancelResult.failure(code, message, resp, already, refundTx, cancelAmt);
            } catch (Exception ex) {
                return TossCancelResult.failure("UNKNOWN", e.getMessage(), resp, false, null, null);
            }
        } catch (Exception e) {
            log.error("[Toss] cancel error paymentKey={}", paymentKey, e);
            throw new BaseException(GlobalErrorCode.PAYMENT_GATEWAY_ERROR, e.getMessage(), e);
        }
    }

    private static boolean isAlreadyCanceledTossError(String code, String message) {
        if (code != null) {
            String c = code.toUpperCase();
            if (c.contains("ALREADY_CANCELED") || c.contains("ALREADY_CANCELLED")) {
                return true;
            }
        }
        if (message != null) {
            String m = message.toLowerCase();
            return m.contains("이미 취소") || m.contains("already canceled");
        }
        return false;
    }

    /** Toss 결제 객체 우선 */
    private static String extractRefundTransactionKey(JsonNode root) {
        if (root == null || root.isMissingNode()) {
            return null;
        }
        JsonNode cancels = root.path("cancels");
        if (cancels.isArray() && cancels.size() > 0) {
            JsonNode last = cancels.get(cancels.size() - 1);
            String tx = last.path("transactionKey").asText(null);
            if (tx != null && !tx.isBlank()) {
                return tx;
            }
        }
        String direct = root.path("transactionKey").asText(null);
        return (direct != null && !direct.isBlank()) ? direct : null;
    }

    /** 마지막 취소 건의 cancelAmount, 없으면 totalAmount */
    private static BigDecimal extractCancelAmount(JsonNode root) {
        if (root == null || root.isMissingNode()) {
            return null;
        }
        JsonNode cancels = root.path("cancels");
        if (cancels.isArray() && cancels.size() > 0) {
            JsonNode last = cancels.get(cancels.size() - 1);
            if (last.hasNonNull("cancelAmount")) {
                try {
                    return new BigDecimal(last.get("cancelAmount").asText());
                } catch (Exception ignored) {
                    // fall through
                }
            }
        }
        if (root.hasNonNull("totalAmount")) {
            try {
                return new BigDecimal(root.get("totalAmount").asText());
            } catch (Exception ignored) {
                return null;
            }
        }
        return null;
    }
}
