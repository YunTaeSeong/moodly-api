package com.moodly.payment.controller;

import com.moodly.common.security.principal.AuthPrincipal;
import com.moodly.payment.request.PaymentCancelRequest;
import com.moodly.payment.request.PaymentConfirmRequest;
import com.moodly.payment.response.PaymentCancelResponse;
import com.moodly.payment.response.PaymentConfirmResponse;
import com.moodly.payment.service.PaymentCancelService;
import com.moodly.payment.service.PaymentConfirmService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentApiController {

    private final PaymentConfirmService paymentConfirmService;
    private final PaymentCancelService paymentCancelService;

    /**
     * Toss success URL 이후 프론트가 호출 — {PaymentConfirmService -> confirm} 위임.
     * (4) 검증 실패는 토스 호출 전 4xx · (2) PG 승인 거절은 502 + DB 실패 이력.
     */
    @PostMapping("/confirm")
    public PaymentConfirmResponse confirm(
            @AuthenticationPrincipal AuthPrincipal principal,
            @Valid @RequestBody PaymentConfirmRequest request
    ) {
        return paymentConfirmService.confirm(principal.getUserId(), request);
    }

    /**
     * 결제완료 직후 전액 취소(Toss 취소 API + 주문/결제 상태 반영)
     */
    @PostMapping("/cancel")
    public PaymentCancelResponse cancel(
            @AuthenticationPrincipal AuthPrincipal principal,
            @Valid @RequestBody PaymentCancelRequest request
    ) {
        return paymentCancelService.cancel(principal.getUserId(), request);
    }
}
