package com.moodly.payment.service;

import com.moodly.payment.client.OrderPaymentCompleteFeignRequest;
import com.moodly.payment.client.OrderPaymentSnapshotDto;
import com.moodly.payment.client.OrderServiceClient;
import com.moodly.payment.domain.Payment;
import com.moodly.payment.domain.PaymentLog;
import com.moodly.payment.log.PaymentLogEventType;
import com.moodly.payment.repository.PaymentLogRepository;
import com.moodly.payment.repository.PaymentRepository;
import com.moodly.payment.toss.TossConfirmResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Toss 승인 성공 이후 로컬 DB 저장 + order-service 상태 반영
 */
@Service
@RequiredArgsConstructor
public class PaymentPostConfirmService {

    private final PaymentRepository paymentRepository;
    private final PaymentLogRepository paymentLogRepository;
    private final OrderServiceClient orderServiceClient;

    private static final String ORDERS = "주문";

    @Transactional
    public Payment persistApprovedAndCompleteOrder(
            OrderPaymentSnapshotDto order,
            String paymentKey,
            BigDecimal amount,
            TossConfirmResult toss
    ) {
        String orderId = order.getOrderId();
        String orderName = ORDERS + orderId;
        Payment payment = Payment.createApproved(
                orderId,
                order.getUserId(),
                amount,
                paymentKey,
                toss.paymentMethod() != null ? toss.paymentMethod() : "",
                toss.approvedAt(),
                orderName,
                order.getCustomerName() != null ? order.getCustomerName() : ""
        );
        paymentRepository.save(payment);
        paymentLogRepository.save(PaymentLog.of(
                orderId,
                PaymentLogEventType.CONFIRM_SUCCESS,
                paymentKey,
                payment.getId(),
                toss.rawBody()
        ));
        orderServiceClient.completePayment(orderId, new OrderPaymentCompleteFeignRequest(paymentKey));
        return payment;
    }
}
