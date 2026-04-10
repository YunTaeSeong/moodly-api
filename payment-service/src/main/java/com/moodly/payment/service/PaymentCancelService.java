package com.moodly.payment.service;

import com.moodly.common.exception.BaseException;
import com.moodly.common.exception.GlobalErrorCode;
import com.moodly.payment.client.OrderPaymentSnapshotDto;
import com.moodly.payment.client.OrderServiceClient;
import com.moodly.payment.domain.Payment;
import com.moodly.payment.domain.Refund;
import com.moodly.payment.enums.PaymentStatus;
import com.moodly.payment.enums.RefundStatus;
import com.moodly.payment.repository.PaymentRepository;
import com.moodly.payment.repository.RefundRepository;
import com.moodly.payment.request.PaymentCancelRequest;
import com.moodly.payment.response.PaymentCancelResponse;
import com.moodly.payment.toss.TossCancelResult;
import com.moodly.payment.toss.TossPaymentsClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentCancelService {

    private final OrderServiceClient orderServiceClient;
    private final TossPaymentsClient tossPaymentsClient;
    private final PaymentRepository paymentRepository;
    private final RefundRepository refundRepository;

    @Transactional
    public PaymentCancelResponse cancel(Long userId, PaymentCancelRequest request) {
        String orderId = request.getOrderId();
        OrderPaymentSnapshotDto order = fetchOrder(orderId);

        if (!Objects.equals(order.getUserId(), userId)) {
            throw new BaseException(GlobalErrorCode.MISSING_AUTHORIZATION);
        }

        String st = order.getStatus();
        if ("PAYMENT_CANCELLED".equals(st)) {
            return PaymentCancelResponse.idempotent(orderId);
        }
        if (!"PAYMENT_COMPLETED".equals(st)) {
            throw new BaseException(GlobalErrorCode.ORDER_NOT_CANCELLABLE);
        }

        Optional<Payment> cancelledExisting = paymentRepository.findFirstByOrderIdAndStatus(orderId, PaymentStatus.CANCELLED);
        if (cancelledExisting.isPresent()) {
            syncOrderCancelled(orderId);
            return PaymentCancelResponse.idempotent(orderId);
        }

        Optional<Payment> approved = paymentRepository.findFirstByOrderIdAndStatus(orderId, PaymentStatus.APPROVED);
        String paymentKey = approved.map(Payment::getPaymentKey).orElse(order.getPaymentKey());
        if (paymentKey == null || paymentKey.isBlank()) {
            throw new BaseException(GlobalErrorCode.PAYMENT_NOT_FOUND);
        }

        String reason = request.getCancelReason();
        if (reason == null || reason.isBlank()) {
            reason = "고객 결제취소 요청";
        }

        TossCancelResult toss = tossPaymentsClient.cancel(paymentKey, reason);
        if (!toss.proceedWithLocalCancel()) {
            throw new BaseException(GlobalErrorCode.PAYMENT_GATEWAY_ERROR,
                    toss.errorMessage() != null ? toss.errorMessage() : "결제 취소 실패");
        }

        Payment pay = approved.orElseGet(() -> paymentRepository.findByPaymentKey(paymentKey).orElse(null));
        if (pay != null && pay.getStatus() == PaymentStatus.APPROVED) {
            pay.markAsCancelled();
            paymentRepository.save(pay);
        }

        persistRefundAfterCancel(paymentKey, reason, pay, toss);

        syncOrderCancelled(orderId);
        return PaymentCancelResponse.ok(orderId);
    }

    /**
     * Toss 취소(또는 이미 취소된 결제) 이후 PG 환불 이력을 refunds 테이블에 남김. 멱등: COMPLETED 환불이 있으면 생략.
     */
    private void persistRefundAfterCancel(String paymentKey, String reason, Payment pay, TossCancelResult toss) {
        Payment p = pay;
        if (p == null) {
            p = paymentRepository.findByPaymentKey(paymentKey).orElse(null);
        }
        if (p == null || p.getId() == null) {
            log.warn("[Payment] refunds : payment 없음 paymentKey={}", paymentKey);
            return;
        }
        if (refundRepository.existsByPaymentIdAndStatus(p.getId(), RefundStatus.COMPLETED)) {
            return;
        }
        BigDecimal amt = toss.cancelAmount() != null ? toss.cancelAmount() : p.getAmount();
        Refund refund = Refund.builder()
                .paymentId(p.getId())
                .amount(amt)
                .reason(reason)
                .status(RefundStatus.COMPLETED)
                .refundKey(toss.refundTransactionKey())
                .completedAt(LocalDateTime.now())
                .build();
        refundRepository.save(refund);
        log.info("[Payment] refund row saved paymentId={} amount={} refundKey={}", p.getId(), amt, toss.refundTransactionKey());
    }

    private void syncOrderCancelled(String orderId) {
        try {
            orderServiceClient.cancelOrderPayment(orderId);
        } catch (FeignException e) {
            log.error("[Payment] order cancel Feign orderId={} status={}", orderId, e.status(), e);
            throw new BaseException(GlobalErrorCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private OrderPaymentSnapshotDto fetchOrder(String orderId) {
        try {
            return orderServiceClient.getOrderForPayment(orderId);
        } catch (FeignException e) {
            if (e.status() == 404) {
                throw new BaseException(GlobalErrorCode.ORDER_NOT_FOUND);
            }
            log.error("[Payment] cancel fetch order Feign status={}", e.status(), e);
            throw new BaseException(GlobalErrorCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
