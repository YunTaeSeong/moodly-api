package com.moodly.payment.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentConfirmServiceTest {

    @InjectMocks
    private PaymentConfirmService paymentConfirmService;

    @Mock
    private OrderServiceClient orderServiceClient;

    @Mock
    private CouponInternalClient couponInternalClient;

    @Mock
    private TossPaymentsClient tossPaymentsClient;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private PaymentFailureRecorder paymentFailureRecorder;

    @Mock
    private PaymentPostConfirmService paymentPostConfirmService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private String orderId;
    private String paymentKey;
    private BigDecimal amount;
    private Long userId;

    @BeforeEach
    void init() {
        orderId = "ORDER-1";
        paymentKey = "pk_test_1";
        amount = new BigDecimal("15000");
        userId = 13L;
    }

    private OrderPaymentSnapshotDto pendingSnapshot() {
        OrderPaymentSnapshotDto d = new OrderPaymentSnapshotDto();
        d.setOrderId(orderId);
        d.setUserId(userId);
        d.setStatus("PENDING_PAYMENT");
        d.setFinalAmount(amount);
        d.setTotalAmount(amount);
        d.setDeliveryAddress("{\"address\":\"서울\"}");
        d.setCustomerName("고객");
        d.setCouponId(null);
        return d;
    }

    private TossConfirmResult tossOk() throws Exception {
        JsonNode root = objectMapper.readTree("{\"method\":\"CARD\",\"approvedAt\":\"2024-01-01T12:00:00+09:00\"}");
        return TossConfirmResult.success(root, "{}");
    }

    @Test
    @DisplayName("결제 확정 : 성공")
    void 결제_확정_성공() throws Exception {
        PaymentConfirmRequest req = new PaymentConfirmRequest(paymentKey, orderId, amount);
        when(orderServiceClient.getOrderForPayment(orderId)).thenReturn(pendingSnapshot());
        when(paymentRepository.findByPaymentKey(paymentKey)).thenReturn(Optional.empty());
        when(paymentRepository.existsByOrderIdAndStatus(orderId, PaymentStatus.APPROVED)).thenReturn(false);
        when(tossPaymentsClient.confirm(paymentKey, orderId, amount)).thenReturn(tossOk());

        Payment saved = Payment.createApproved(orderId, userId, amount, paymentKey, "CARD",
                java.time.LocalDateTime.now(), "주문" + orderId, "고객");
        ReflectionTestUtils.setField(saved, "id", 100L);
        when(paymentPostConfirmService.persistApprovedAndCompleteOrder(any(), eq(paymentKey), eq(amount), any()))
                .thenReturn(saved);

        PaymentConfirmResponse result = paymentConfirmService.confirm(userId, req);

        assertNotNull(result);
        assertEquals(100L, result.getPaymentId());
        verify(tossPaymentsClient).confirm(paymentKey, orderId, amount);
        verify(paymentPostConfirmService).persistApprovedAndCompleteOrder(any(), eq(paymentKey), eq(amount), any());
    }

    @Test
    @DisplayName("결제 확정 : paymentKey 멱등(이미 승인)")
    void 결제_확정_멱등_paymentKey() {
        PaymentConfirmRequest req = new PaymentConfirmRequest(paymentKey, orderId, amount);
        Payment existing = Payment.createApproved(orderId, userId, amount, paymentKey, "CARD",
                java.time.LocalDateTime.now(), "주문", "고객");
        ReflectionTestUtils.setField(existing, "id", 50L);

        when(orderServiceClient.getOrderForPayment(orderId)).thenReturn(pendingSnapshot());
        when(paymentRepository.findByPaymentKey(paymentKey)).thenReturn(Optional.of(existing));

        PaymentConfirmResponse result = paymentConfirmService.confirm(userId, req);

        assertNotNull(result);
        assertEquals(50L, result.getPaymentId());
        verify(tossPaymentsClient, never()).confirm(anyString(), anyString(), any());
        verify(paymentPostConfirmService, never()).persistApprovedAndCompleteOrder(any(), anyString(), any(), any());
    }

    @Test
    @DisplayName("결제 확정 : 실패 — paymentKey 주문번호 불일치")
    void 결제_확정_실패_paymentKey_주문_불일치() {
        PaymentConfirmRequest req = new PaymentConfirmRequest(paymentKey, orderId, amount);
        Payment existing = Payment.createApproved("OTHER-ORDER", userId, amount, paymentKey, "CARD",
                java.time.LocalDateTime.now(), "주문", "고객");
        when(orderServiceClient.getOrderForPayment(orderId)).thenReturn(pendingSnapshot());
        when(paymentRepository.findByPaymentKey(paymentKey)).thenReturn(Optional.of(existing));

        BaseException ex = assertThrows(BaseException.class, () -> paymentConfirmService.confirm(userId, req));
        assertEquals(GlobalErrorCode.MISSING_AUTHORIZATION, ex.getErrorCode());
    }

    @Test
    @DisplayName("결제 확정 : 멱등 — 주문 이미 결제완료")
    void 결제_확정_멱등_주문_결제완료() {
        PaymentConfirmRequest req = new PaymentConfirmRequest(paymentKey, orderId, amount);
        OrderPaymentSnapshotDto d = pendingSnapshot();
        d.setStatus("PAYMENT_COMPLETED");
        when(orderServiceClient.getOrderForPayment(orderId)).thenReturn(d);
        when(paymentRepository.findByPaymentKey(paymentKey)).thenReturn(Optional.empty());

        PaymentConfirmResponse result = paymentConfirmService.confirm(userId, req);

        assertTrue(result.isIdempotentReplay());
        verify(tossPaymentsClient, never()).confirm(anyString(), anyString(), any());
    }

    @Test
    @DisplayName("결제 확정 : 실패 — 결제 불가 상태")
    void 결제_확정_실패_주문_상태() {
        PaymentConfirmRequest req = new PaymentConfirmRequest(paymentKey, orderId, amount);
        OrderPaymentSnapshotDto d = pendingSnapshot();
        d.setStatus("SHIPPED");
        when(orderServiceClient.getOrderForPayment(orderId)).thenReturn(d);
        when(paymentRepository.findByPaymentKey(paymentKey)).thenReturn(Optional.empty());
        when(paymentRepository.existsByOrderIdAndStatus(orderId, PaymentStatus.APPROVED)).thenReturn(false);

        BaseException ex = assertThrows(BaseException.class, () -> paymentConfirmService.confirm(userId, req));
        assertEquals(GlobalErrorCode.ORDER_NOT_PAYABLE, ex.getErrorCode());
    }

    @Test
    @DisplayName("결제 확정 : 실패 — 금액 불일치")
    void 결제_확정_실패_금액() {
        PaymentConfirmRequest req = new PaymentConfirmRequest(paymentKey, orderId, new BigDecimal("1"));
        when(orderServiceClient.getOrderForPayment(orderId)).thenReturn(pendingSnapshot());

        BaseException ex = assertThrows(BaseException.class, () -> paymentConfirmService.confirm(userId, req));
        assertEquals(GlobalErrorCode.PAYMENT_AMOUNT_MISMATCH, ex.getErrorCode());
    }

    @Test
    @DisplayName("결제 확정 : 실패 — 본인 주문 아님")
    void 결제_확정_실패_본인아님() {
        PaymentConfirmRequest req = new PaymentConfirmRequest(paymentKey, orderId, amount);
        OrderPaymentSnapshotDto d = pendingSnapshot();
        d.setUserId(999L);
        when(orderServiceClient.getOrderForPayment(orderId)).thenReturn(d);

        BaseException ex = assertThrows(BaseException.class, () -> paymentConfirmService.confirm(userId, req));
        assertEquals(GlobalErrorCode.MISSING_AUTHORIZATION, ex.getErrorCode());
    }

    @Test
    @DisplayName("결제 확정 : 실패 — 배송지 없음")
    void 결제_확정_실패_배송지() {
        PaymentConfirmRequest req = new PaymentConfirmRequest(paymentKey, orderId, amount);
        OrderPaymentSnapshotDto d = pendingSnapshot();
        d.setDeliveryAddress("");
        when(orderServiceClient.getOrderForPayment(orderId)).thenReturn(d);

        BaseException ex = assertThrows(BaseException.class, () -> paymentConfirmService.confirm(userId, req));
        assertEquals(GlobalErrorCode.DELIVERY_ADDRESS_REQUIRED, ex.getErrorCode());
    }

    @Test
    @DisplayName("결제 확정 : 실패 — Toss 승인 거절")
    void 결제_확정_실패_Toss() {
        PaymentConfirmRequest req = new PaymentConfirmRequest(paymentKey, orderId, amount);
        when(orderServiceClient.getOrderForPayment(orderId)).thenReturn(pendingSnapshot());
        when(paymentRepository.findByPaymentKey(paymentKey)).thenReturn(Optional.empty());
        when(paymentRepository.existsByOrderIdAndStatus(orderId, PaymentStatus.APPROVED)).thenReturn(false);
        when(tossPaymentsClient.confirm(paymentKey, orderId, amount))
                .thenReturn(TossConfirmResult.failure("REJECT", "거절", "{}"));

        BaseException ex = assertThrows(BaseException.class, () -> paymentConfirmService.confirm(userId, req));
        assertEquals(GlobalErrorCode.PAYMENT_GATEWAY_ERROR, ex.getErrorCode());
        verify(paymentFailureRecorder).recordGatewayFailure(
                eq(orderId), eq(userId), eq(amount), eq(paymentKey),
                eq("주문 " + orderId), eq("고객"), eq("REJECT"), eq("거절"), anyString());
    }

    @Test
    @DisplayName("주문 조회 : 실패 — 404")
    void 주문_조회_404() {
        PaymentConfirmRequest req = new PaymentConfirmRequest(paymentKey, orderId, amount);
        FeignException ex = mock(FeignException.class);
        when(ex.status()).thenReturn(404);
        when(orderServiceClient.getOrderForPayment(orderId)).thenThrow(ex);

        BaseException be = assertThrows(BaseException.class, () -> paymentConfirmService.confirm(userId, req));
        assertEquals(GlobalErrorCode.ORDER_NOT_FOUND, be.getErrorCode());
    }
}
