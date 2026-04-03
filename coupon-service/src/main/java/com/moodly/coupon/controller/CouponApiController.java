package com.moodly.coupon.controller;

import com.moodly.common.security.principal.AuthPrincipal;
import com.moodly.coupon.dto.CouponDto;
import com.moodly.coupon.response.UserCouponDetailResponse;
import com.moodly.coupon.response.UserCouponResponse;
import com.moodly.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/coupon")
@RequiredArgsConstructor
public class CouponApiController {

    private final CouponService couponService;

    // 받을 수 있는 쿠폰 : 경로가 userCouponId와 겹치지 않아야 함
    @GetMapping("/receivable")
    public List<CouponDto> getReceivable(@AuthenticationPrincipal AuthPrincipal principal) {
        return couponService.getReceivableCoupons(principal.getUserId());
    }

    // 단건 조회
    @GetMapping("/{userCouponId}")
    public UserCouponResponse getUserCoupon(
            @AuthenticationPrincipal AuthPrincipal principal,
            @PathVariable Long userCouponId
    ) {
        return UserCouponResponse.response(couponService.getUserCoupon(principal.getUserId(), userCouponId));
    }

    // 전체 조회 (쿠폰명·할인율 등 표시용)
    @GetMapping
    public List<UserCouponDetailResponse> getAllUserCoupon(
            @AuthenticationPrincipal AuthPrincipal principal
    ) {
        return couponService.getAllUserCouponDetails(principal.getUserId());
    }

//    // 쿠폰 발급
//    @PostMapping("/{couponId}/issue")
//    public UserCouponResponse issue(
//            @AuthenticationPrincipal AuthPrincipal principal,
//            @PathVariable Long couponId
//    ) {
//        return UserCouponResponse.response(couponService.issue(principal.getUserId(), couponId));
//    }

    // 쿠폰 발급 부하테스트용
    // JWT 있으면 → principal userId 사용
    // JWT 없으면 → userId = 1 (Locust 테스트용)
    @PostMapping("/{couponId}/issue")
    public UserCouponResponse issue(
            @AuthenticationPrincipal AuthPrincipal principal,
            @PathVariable Long couponId
    ) {

        Long userId = principal != null ? principal.getUserId() : 1L;

        return UserCouponResponse.response(
                couponService.issue(userId, couponId)
        );
    }

    // 쿠폰 사용
    @PostMapping("/{userCouponId}/use")
    public UserCouponResponse use(
            @AuthenticationPrincipal AuthPrincipal principal,
            @PathVariable Long userCouponId,
            @RequestParam String orderId,
            @RequestParam BigDecimal orderAmount
    ) {
        return UserCouponResponse.response(couponService.use(principal.getUserId(), userCouponId, orderId, orderAmount));
    }

    // 쿠폰 취소
    @PostMapping("/{userCouponId}/cancel")
    public UserCouponResponse cancel(
            @AuthenticationPrincipal AuthPrincipal principal,
            @PathVariable Long userCouponId
    ) {
        return UserCouponResponse.response(couponService.cancel(principal.getUserId(), userCouponId));
    }

    // 쿠폰 만료
    @PostMapping("/{userCouponId}/expire")
    public void expire(
            @AuthenticationPrincipal AuthPrincipal principal,
            @PathVariable Long userCouponId
    ) {
        couponService.expire(principal.getUserId(), userCouponId);
    }
}
