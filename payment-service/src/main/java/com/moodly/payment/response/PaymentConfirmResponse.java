package com.moodly.payment.response;

import com.moodly.payment.domain.Payment;
import com.moodly.payment.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentConfirmResponse {

    private String orderId;
    private String paymentKey;
    private PaymentStatus status;
    private Long paymentId;
    /** 이미 결제 완료된 주문에 대한 재요청 */
    private boolean idempotentReplay;

    public static PaymentConfirmResponse idempotent(String orderId, String paymentKey) {
        return PaymentConfirmResponse.builder()
                .orderId(orderId)
                .paymentKey(paymentKey)
                .status(PaymentStatus.APPROVED)
                .idempotentReplay(true)
                .build();
    }

    public static PaymentConfirmResponse from(Payment payment) {
        return PaymentConfirmResponse.builder()
                .orderId(payment.getOrderId())
                .paymentKey(payment.getPaymentKey())
                .status(payment.getStatus())
                .paymentId(payment.getId())
                .idempotentReplay(false)
                .build();
    }
}
