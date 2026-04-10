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
class PaymentCancelServiceTest {

    @InjectMocks
    private PaymentCancelService paymentCancelService;

    @Mock
    private OrderServiceClient orderServiceClient;

    @Mock
    private TossPaymentsClient tossPaymentsClient;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private RefundRepository refundRepository;

    private Long userId;
    private String orderId;
    private String paymentKey;

    @BeforeEach
    void init() {
        userId = 13L;
        orderId = "ORDER-C-1";
        paymentKey = "pk_cancel_1";
    }

    private OrderPaymentSnapshotDto completedOrder() {
        OrderPaymentSnapshotDto d = new OrderPaymentSnapshotDto();
        d.setOrderId(orderId);
        d.setUserId(userId);
        d.setStatus("PAYMENT_COMPLETED");
        d.setPaymentKey(paymentKey);
        return d;
    }

    private Payment approvedPayment() {
        Payment p = Payment.createApproved(orderId, userId, new BigDecimal("15000"), paymentKey,
                "CARD", java.time.LocalDateTime.now(), "주문", "고객");
        ReflectionTestUtils.setField(p, "id", 300L);
        return p;
    }

    @Test
    @DisplayName("결제 취소 : 멱등 — 주문이 이미 결제취소")
    void 결제취소_멱등_주문취소됨() {
        OrderPaymentSnapshotDto d = completedOrder();
        d.setStatus("PAYMENT_CANCELLED");
        when(orderServiceClient.getOrderForPayment(orderId)).thenReturn(d);

        PaymentCancelRequest req = new PaymentCancelRequest();
        req.setOrderId(orderId);

        PaymentCancelResponse res = paymentCancelService.cancel(userId, req);

        assertTrue(res.isIdempotentReplay());
        verify(tossPaymentsClient, never()).cancel(anyString(), anyString());
    }

    @Test
    @DisplayName("결제 취소 : 실패 — 결제완료가 아님")
    void 결제취소_실패_상태() {
        OrderPaymentSnapshotDto d = completedOrder();
        d.setStatus("PENDING_PAYMENT");
        when(orderServiceClient.getOrderForPayment(orderId)).thenReturn(d);

        PaymentCancelRequest req = new PaymentCancelRequest();
        req.setOrderId(orderId);

        BaseException ex = assertThrows(BaseException.class, () -> paymentCancelService.cancel(userId, req));
        assertEquals(GlobalErrorCode.ORDER_NOT_CANCELLABLE, ex.getErrorCode());
    }

    @Test
    @DisplayName("결제 취소 : 실패 — 본인 주문 아님")
    void 결제취소_실패_본인아님() {
        OrderPaymentSnapshotDto d = completedOrder();
        d.setUserId(999L);
        when(orderServiceClient.getOrderForPayment(orderId)).thenReturn(d);

        PaymentCancelRequest req = new PaymentCancelRequest();
        req.setOrderId(orderId);

        BaseException ex = assertThrows(BaseException.class, () -> paymentCancelService.cancel(userId, req));
        assertEquals(GlobalErrorCode.MISSING_AUTHORIZATION, ex.getErrorCode());
    }

    @Test
    @DisplayName("결제 취소 : 실패 — paymentKey 없음")
    void 결제취소_실패_payment없음() {
        OrderPaymentSnapshotDto d = completedOrder();
        d.setPaymentKey(null);
        when(orderServiceClient.getOrderForPayment(orderId)).thenReturn(d);
        when(paymentRepository.findFirstByOrderIdAndStatus(orderId, PaymentStatus.CANCELLED)).thenReturn(Optional.empty());
        when(paymentRepository.findFirstByOrderIdAndStatus(orderId, PaymentStatus.APPROVED)).thenReturn(Optional.empty());

        PaymentCancelRequest req = new PaymentCancelRequest();
        req.setOrderId(orderId);

        BaseException ex = assertThrows(BaseException.class, () -> paymentCancelService.cancel(userId, req));
        assertEquals(GlobalErrorCode.PAYMENT_NOT_FOUND, ex.getErrorCode());
    }

    @Test
    @DisplayName("결제 취소 : 성공 — Toss 취소·payment·refund·주문연동")
    void 결제취소_성공() {
        when(orderServiceClient.getOrderForPayment(orderId)).thenReturn(completedOrder());
        when(paymentRepository.findFirstByOrderIdAndStatus(orderId, PaymentStatus.CANCELLED)).thenReturn(Optional.empty());
        Payment pay = approvedPayment();
        when(paymentRepository.findFirstByOrderIdAndStatus(orderId, PaymentStatus.APPROVED)).thenReturn(Optional.of(pay));
        when(tossPaymentsClient.cancel(eq(paymentKey), anyString()))
                .thenReturn(TossCancelResult.ok("{}", "tx-refund-1", new BigDecimal("15000")));
        when(refundRepository.existsByPaymentIdAndStatus(300L, RefundStatus.COMPLETED)).thenReturn(false);
        when(paymentRepository.save(any(Payment.class))).thenReturn(pay);

        PaymentCancelRequest req = new PaymentCancelRequest();
        req.setOrderId(orderId);
        req.setCancelReason("테스트 취소");

        PaymentCancelResponse res = paymentCancelService.cancel(userId, req);

        assertFalse(res.isIdempotentReplay());
        assertTrue(res.isCancelled());
        verify(tossPaymentsClient).cancel(eq(paymentKey), anyString());
        verify(paymentRepository).save(pay);
        verify(refundRepository).save(any(Refund.class));
        verify(orderServiceClient).cancelOrderPayment(orderId);
    }

    @Test
    @DisplayName("결제 취소 : 실패 — Toss 거절")
    void 결제취소_실패_Toss() {
        when(orderServiceClient.getOrderForPayment(orderId)).thenReturn(completedOrder());
        when(paymentRepository.findFirstByOrderIdAndStatus(orderId, PaymentStatus.CANCELLED)).thenReturn(Optional.empty());
        when(paymentRepository.findFirstByOrderIdAndStatus(orderId, PaymentStatus.APPROVED)).thenReturn(Optional.of(approvedPayment()));
        when(tossPaymentsClient.cancel(anyString(), anyString()))
                .thenReturn(TossCancelResult.failure("FAIL", "거절", "{}", false, null, null));

        PaymentCancelRequest req = new PaymentCancelRequest();
        req.setOrderId(orderId);

        assertThrows(BaseException.class, () -> paymentCancelService.cancel(userId, req));
        verify(orderServiceClient, never()).cancelOrderPayment(anyString());
    }

    @Test
    @DisplayName("주문 조회 : 실패 — Feign 404")
    void 주문조회_404() {
        FeignException ex = mock(FeignException.class);
        when(ex.status()).thenReturn(404);
        when(orderServiceClient.getOrderForPayment(orderId)).thenThrow(ex);

        PaymentCancelRequest req = new PaymentCancelRequest();
        req.setOrderId(orderId);

        BaseException be = assertThrows(BaseException.class, () -> paymentCancelService.cancel(userId, req));
        assertEquals(GlobalErrorCode.ORDER_NOT_FOUND, be.getErrorCode());
    }
}
