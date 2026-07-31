package com.moodly.coupon.controller;

import com.moodly.coupon.response.FlashCouponStatusResponse;
import com.moodly.coupon.response.UserCouponResponse;
import com.moodly.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Locust 등 부하 테스트 전용 (local/docker, moodly.coupon.load-test.enabled=true)
 */
@RestController
@RequestMapping("/internal/coupon/load-test")
@RequiredArgsConstructor
@ConditionalOnProperty(name = "moodly.coupon.load-test.enabled", havingValue = "true")
public class CouponLoadTestController {

    private final CouponService couponService;

    /** 선착순 70% 쿠폰 발급 (userId만 지정, JWT 불필요) */
    @PostMapping("/flash/issue")
    public UserCouponResponse issueFlashCoupon(@RequestParam Long userId) {
        Long couponId = couponService.getFlashCouponId();
        return UserCouponResponse.response(couponService.issue(userId, couponId));
    }

    /** 테스트 재실행용: 선착순 쿠폰 발급 수량·user_coupons 초기화 */
    @PostMapping("/flash/reset")
    public ResponseEntity<FlashCouponStatusResponse> resetFlashCoupon() {
        FlashCouponStatusResponse status = couponService.resetFlashCouponForLoadTest();
        return ResponseEntity.ok(status);
    }
}
