package com.moodly.payment.toss;

import com.fasterxml.jackson.databind.JsonNode;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public record TossConfirmResult(
        boolean success,
        String errorCode,
        String errorMessage,
        String rawBody,
        String paymentMethod,
        LocalDateTime approvedAt
) {

    public static TossConfirmResult success(JsonNode root, String rawBody) {
        String method = root.path("method").asText("");
        LocalDateTime approvedAt = LocalDateTime.now();
        if (root.hasNonNull("approvedAt")) {
            try {
                approvedAt = OffsetDateTime.parse(root.get("approvedAt").asText()).toLocalDateTime();
            } catch (Exception ignored) {
                // 파싱 실패 시 현재 시각
            }
        }
        return new TossConfirmResult(true, null, null, rawBody, method, approvedAt);
    }

    public static TossConfirmResult failure(String code, String message, String rawBody) {
        return new TossConfirmResult(false, code, message, rawBody, null, null);
    }
}
