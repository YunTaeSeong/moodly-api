package com.moodly.payment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moodly.payment.client.OrderPaymentCompleteFeignRequest;
import com.moodly.payment.client.OrderPaymentSnapshotDto;
import com.moodly.payment.client.OrderServiceClient;
import com.moodly.payment.domain.Payment;
import com.moodly.payment.domain.PaymentLog;
import com.moodly.payment.log.PaymentLogEventType;
import com.moodly.payment.repository.PaymentLogRepository;
import com.moodly.payment.repository.PaymentRepository;
import com.moodly.payment.toss.TossConfirmResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentPostConfirmServiceTest {

    @InjectMocks
    private PaymentPostConfirmService paymentPostConfirmService;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private PaymentLogRepository paymentLogRepository;

    @Mock
    private OrderServiceClient orderServiceClient;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private OrderPaymentSnapshotDto order;
    private String paymentKey;
    private BigDecimal amount;
    private TossConfirmResult toss;

    @BeforeEach
    void init() throws Exception {
        paymentKey = "pk_save_1";
        amount = new BigDecimal("10000");
        order = new OrderPaymentSnapshotDto();
        order.setOrderId("ORDER-X");
        order.setUserId(1L);
        order.setCustomerName("테스트");
        var root = objectMapper.readTree("{\"method\":\"EASY_PAY\",\"approvedAt\":\"2024-06-01T10:00:00+09:00\"}");
        toss = TossConfirmResult.success(root, "{\"ok\":true}");
    }

    @Test
    @DisplayName("승인 후 저장 : 성공 — payment 저장·로그·주문완료 호출")
    void 승인_후_저장_성공() {
        Payment toSave = Payment.createApproved(order.getOrderId(), order.getUserId(), amount, paymentKey,
                "EASY_PAY", java.time.LocalDateTime.now(), "주문" + order.getOrderId(), "테스트");
        ReflectionTestUtils.setField(toSave, "id", 200L);

        when(paymentRepository.save(any(Payment.class))).thenReturn(toSave);

        Payment result = paymentPostConfirmService.persistApprovedAndCompleteOrder(order, paymentKey, amount, toss);

        assertNotNull(result);
        assertEquals(200L, result.getId());
        ArgumentCaptor<PaymentLog> logCap = ArgumentCaptor.forClass(PaymentLog.class);
        verify(paymentLogRepository).save(logCap.capture());
        assertEquals(PaymentLogEventType.CONFIRM_SUCCESS, logCap.getValue().getEventType());
        ArgumentCaptor<OrderPaymentCompleteFeignRequest> completeCap =
                ArgumentCaptor.forClass(OrderPaymentCompleteFeignRequest.class);
        verify(orderServiceClient).completePayment(eq(order.getOrderId()), completeCap.capture());
        assertEquals(paymentKey, completeCap.getValue().getPaymentKey());
    }

    @Test
    @DisplayName("승인 후 저장 : 동시성 — 유니크 충돌 시 기존 payment 사용")
    void 승인_후_저장_유니크_충돌() {
        Payment existing = Payment.createApproved(order.getOrderId(), order.getUserId(), amount, paymentKey,
                "EASY_PAY", java.time.LocalDateTime.now(), "주문", "테스트");
        ReflectionTestUtils.setField(existing, "id", 777L);

        when(paymentRepository.save(any(Payment.class))).thenThrow(new DataIntegrityViolationException("dup"));
        when(paymentRepository.findByPaymentKey(paymentKey)).thenReturn(Optional.of(existing));

        Payment result = paymentPostConfirmService.persistApprovedAndCompleteOrder(order, paymentKey, amount, toss);

        assertEquals(777L, result.getId());
        verify(paymentLogRepository).save(any(PaymentLog.class));
        ArgumentCaptor<OrderPaymentCompleteFeignRequest> completeCap =
                ArgumentCaptor.forClass(OrderPaymentCompleteFeignRequest.class);
        verify(orderServiceClient).completePayment(eq(order.getOrderId()), completeCap.capture());
        assertEquals(paymentKey, completeCap.getValue().getPaymentKey());
    }
}
