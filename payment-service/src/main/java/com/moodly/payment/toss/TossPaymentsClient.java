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
}
