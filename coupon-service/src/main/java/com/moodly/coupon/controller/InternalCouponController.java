package com.moodly.coupon.controller;

import com.moodly.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/coupon")
@RequiredArgsConstructor
public class InternalCouponController {

    private final CouponService couponService;

    /**
     * 필요 시 내부 호출 (멱등: 이미 있으면 무시). 일반 발급은 GET /coupon/receivable + POST /coupon/{id}/issue 사용.
     */
    @PostMapping("/users/{userId}/welcome")
    public ResponseEntity<Void> issueWelcomeCoupon(@PathVariable Long userId) {
        couponService.issueWelcomeCouponForNewUser(userId);
        return ResponseEntity.ok().build();
    }
}
