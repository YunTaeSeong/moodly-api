package com.moodly.payment.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentCancelResponse {

    private String orderId;
    private boolean cancelled;
    private boolean idempotentReplay;

    public static PaymentCancelResponse ok(String orderId) {
        return PaymentCancelResponse.builder()
                .orderId(orderId)
                .cancelled(true)
                .idempotentReplay(false)
                .build();
    }

    public static PaymentCancelResponse idempotent(String orderId) {
        return PaymentCancelResponse.builder()
                .orderId(orderId)
                .cancelled(true)
                .idempotentReplay(true)
                .build();
    }
}
