package com.moodly.payment.service;

import com.moodly.payment.domain.Payment;
import com.moodly.payment.domain.PaymentLog;
import com.moodly.payment.log.PaymentLogEventType;
import com.moodly.payment.repository.PaymentLogRepository;
import com.moodly.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Toss 승인 실패 등 메인 트랜잭션이 롤백되어도 실패 이력을 남기기 위해 REQUIRES_NEW 사용
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentFailureRecorder {

    private final PaymentRepository paymentRepository;
    private final PaymentLogRepository paymentLogRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void recordGatewayFailure(
            String orderId,
            Long userId,
            BigDecimal amount,
            String paymentKey,
            String orderName,
            String customerName,
            String failCode,
            String failReason,
            String rawPayload
    ) {
        if (paymentRepository.existsByPaymentKey(paymentKey)) {
            log.warn("[Payment] skip failure record; payment_key already exists (concurrent confirm): {}", paymentKey);
            return;
        }
        Payment failed = Payment.createFailed(
                orderId,
                userId,
                amount,
                paymentKey,
                orderName,
                customerName,
                failCode,
                failReason
        );
        try {
            paymentRepository.save(failed);
            paymentLogRepository.save(PaymentLog.of(
                    orderId,
                    PaymentLogEventType.CONFIRM_FAILED,
                    paymentKey,
                    failed.getId(),
                    rawPayload
            ));
        } catch (DataIntegrityViolationException e) {
            log.warn("[Payment] duplicate payment_key while recording failure (race), ignored: {}", paymentKey, e);
        }
    }
}
