package com.moodly.payment.toss;

import java.math.BigDecimal;

/**
 * Toss POST /v1/payments/{paymentKey}/cancel 응답 파싱 결과
 */
public record TossCancelResult(
        /** HTTP 2xx 로 취소 API 성공 */
        boolean httpSuccess,
        /** Toss 가 "이미 취소됨" 등으로 돌려도 로컬 정합을 맞출 수 있는 경우 */
        boolean alreadyCanceledAtGateway,
        String errorCode,
        String errorMessage,
        String rawBody,
        /** cancels[].transactionKey 등 */
        String refundTransactionKey,
        BigDecimal cancelAmount
) {

    public static TossCancelResult ok(String rawBody, String refundTransactionKey, BigDecimal cancelAmount) {
        return new TossCancelResult(true, false, null, null, rawBody, refundTransactionKey, cancelAmount);
    }

    public static TossCancelResult failure(
            String code,
            String message,
            String rawBody,
            boolean alreadyCanceledAtGateway,
            String refundTransactionKey,
            BigDecimal cancelAmount
    ) {
        return new TossCancelResult(false, alreadyCanceledAtGateway, code, message, rawBody, refundTransactionKey, cancelAmount);
    }

    /** 로컬에서 결제 취소·환불 이력 반영까지 진행할지 */
    public boolean proceedWithLocalCancel() {
        return httpSuccess || alreadyCanceledAtGateway;
    }

    public boolean alreadyCanceled() {
        return alreadyCanceledAtGateway;
    }
}
