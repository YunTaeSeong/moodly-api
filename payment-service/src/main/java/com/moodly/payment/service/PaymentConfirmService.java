package com.moodly.payment.service;

import com.moodly.common.exception.BaseException;
import com.moodly.common.exception.GlobalErrorCode;
import com.moodly.payment.client.CouponInternalClient;
import com.moodly.payment.client.OrderPaymentSnapshotDto;
import com.moodly.payment.client.OrderServiceClient;
import com.moodly.payment.domain.Payment;
import com.moodly.payment.enums.PaymentStatus;
import com.moodly.payment.repository.PaymentRepository;
import com.moodly.payment.request.PaymentConfirmRequest;
import com.moodly.payment.response.PaymentConfirmResponse;
import com.moodly.payment.toss.TossConfirmResult;
import com.moodly.payment.toss.TossPaymentsClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentConfirmService {

    private static final String STATUS_PENDING_PAYMENT = "PENDING_PAYMENT";
    private static final String STATUS_PAYMENT_COMPLETED = "PAYMENT_COMPLETED";

    private final OrderServiceClient orderServiceClient;
    private final CouponInternalClient couponInternalClient;
    private final TossPaymentsClient tossPaymentsClient;
    private final PaymentRepository paymentRepository;
    private final PaymentFailureRecorder paymentFailureRecorder;
    private final PaymentPostConfirmService paymentPostConfirmService;

    /**
     * 프론트에서 전달한 paymentKey, orderId, amount 로 Toss 승인 및 주문·결제 반영
     */
    public PaymentConfirmResponse confirm(Long userId, PaymentConfirmRequest request) {
        String orderId = request.getOrderId();
        String paymentKey = request.getPaymentKey();
        BigDecimal amount = request.getAmount();

        OrderPaymentSnapshotDto order = fetchOrder(orderId);

        assertBaseOrderConstraints(userId, amount, order);

        if (STATUS_PAYMENT_COMPLETED.equals(order.getStatus())
                || paymentRepository.existsByOrderIdAndStatus(orderId, PaymentStatus.APPROVED)) {
            log.info("[Payment] idempotent confirm orderId={}", orderId);
            return PaymentConfirmResponse.idempotent(orderId, paymentKey);
        }

        if (!STATUS_PENDING_PAYMENT.equals(order.getStatus())) {
            throw new BaseException(GlobalErrorCode.ORDER_NOT_PAYABLE);
        }

        if (order.getCouponId() != null) {
            validateCoupon(order);
        }

        TossConfirmResult toss = tossPaymentsClient.confirm(paymentKey, orderId, amount);
        if (!toss.success()) {
            String orderName = "주문 " + orderId;
            paymentFailureRecorder.recordGatewayFailure(
                    orderId,
                    order.getUserId(),
                    amount,
                    paymentKey,
                    orderName,
                    order.getCustomerName() != null ? order.getCustomerName() : "",
                    toss.errorCode(),
                    toss.errorMessage(),
                    toss.rawBody()
            );
            throw new BaseException(GlobalErrorCode.PAYMENT_GATEWAY_ERROR, toss.errorMessage());
        }

        Payment saved = paymentPostConfirmService.persistApprovedAndCompleteOrder(order, paymentKey, amount, toss);
        return PaymentConfirmResponse.from(saved);
    }

    private OrderPaymentSnapshotDto fetchOrder(String orderId) {
        try {
            return orderServiceClient.getOrderForPayment(orderId);
        } catch (FeignException e) {
            if (e.status() == 404) {
                throw new BaseException(GlobalErrorCode.ORDER_NOT_FOUND);
            }
            log.error("[Payment] order-service Feign error status={}", e.status(), e);
            throw new BaseException(GlobalErrorCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private void assertBaseOrderConstraints(Long userId, BigDecimal paidAmount, OrderPaymentSnapshotDto order) {
        if (!Objects.equals(order.getUserId(), userId)) {
            throw new BaseException(GlobalErrorCode.MISSING_AUTHORIZATION);
        }
        if (order.getFinalAmount() == null || order.getFinalAmount().compareTo(paidAmount) != 0) {
            throw new BaseException(GlobalErrorCode.PAYMENT_AMOUNT_MISMATCH);
        }
        if (order.getDeliveryAddress() == null || order.getDeliveryAddress().isBlank()) {
            throw new BaseException(GlobalErrorCode.DELIVERY_ADDRESS_REQUIRED);
        }
    }

    private void validateCoupon(OrderPaymentSnapshotDto order) {
        try {
            couponInternalClient.validateForOrderPayment(
                    order.getUserId(),
                    order.getCouponId(),
                    order.getTotalAmount() != null ? order.getTotalAmount() : BigDecimal.ZERO
            );
        } catch (FeignException e) {
            if (e.status() == 404) {
                throw new BaseException(GlobalErrorCode.COUPON_NOT_FOUND);
            }
            if (e.status() == 400) {
                throw new BaseException(GlobalErrorCode.COUPON_NOT_USE, e.getMessage());
            }
            log.error("[Payment] coupon-service Feign error status={}", e.status(), e);
            throw new BaseException(GlobalErrorCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
