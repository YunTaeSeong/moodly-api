package com.moodly.payment.service;

import com.moodly.payment.domain.Payment;
import com.moodly.payment.domain.PaymentLog;
import com.moodly.payment.log.PaymentLogEventType;
import com.moodly.payment.repository.PaymentLogRepository;
import com.moodly.payment.repository.PaymentRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentFailureRecorderTest {

    @InjectMocks
    private PaymentFailureRecorder paymentFailureRecorder;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private PaymentLogRepository paymentLogRepository;

    @Test
    @DisplayName("실패 기록 : payment_key 이미 있으면 스킵")
    void 실패기록_스킵_중복키() {
        when(paymentRepository.existsByPaymentKey("pk_dup")).thenReturn(true);

        paymentFailureRecorder.recordGatewayFailure(
                "ORDER-1", 1L, new BigDecimal("1000"), "pk_dup",
                "주문", "고객", "ERR", "실패", "{}");

        verify(paymentRepository, never()).save(any());
        verify(paymentLogRepository, never()).save(any());
    }

    @Test
    @DisplayName("실패 기록 : 성공 — payment·로그 저장")
    void 실패기록_성공() {
        when(paymentRepository.existsByPaymentKey("pk_fail")).thenReturn(false);
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> {
            Payment p = invocation.getArgument(0);
            ReflectionTestUtils.setField(p, "id", 88L);
            return p;
        });

        paymentFailureRecorder.recordGatewayFailure(
                "ORDER-2", 2L, new BigDecimal("5000"), "pk_fail",
                "주문ORDER-2", "이름", "CODE", "메시지", "{\"x\":1}");

        ArgumentCaptor<Payment> payCap = ArgumentCaptor.forClass(Payment.class);
        verify(paymentRepository).save(payCap.capture());
        assertEquals("CODE", payCap.getValue().getFailCode());

        ArgumentCaptor<PaymentLog> logCap = ArgumentCaptor.forClass(PaymentLog.class);
        verify(paymentLogRepository).save(logCap.capture());
        assertEquals(PaymentLogEventType.CONFIRM_FAILED, logCap.getValue().getEventType());
        assertEquals(88L, logCap.getValue().getPaymentId());
    }

    @Test
    @DisplayName("실패 기록 : 저장 시 유니크 충돌 — 예외 삼키고 종료")
    void 실패기록_유니크_충돌() {
        when(paymentRepository.existsByPaymentKey("pk_race")).thenReturn(false);
        when(paymentRepository.save(any(Payment.class))).thenThrow(new DataIntegrityViolationException("dup"));

        assertDoesNotThrow(() -> paymentFailureRecorder.recordGatewayFailure(
                "ORDER-3", 3L, new BigDecimal("100"), "pk_race",
                "주문", "고객", "E", "M", "{}"));

        verify(paymentLogRepository, never()).save(any());
    }
}
