package com.moodly.coupon.controller;

import com.moodly.common.security.principal.AuthPrincipal;
import com.moodly.coupon.dto.CouponDto;
import com.moodly.coupon.request.CouponCreateRequest;
import com.moodly.coupon.response.CouponResponse;
import com.moodly.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/admin/coupon")
@RequiredArgsConstructor
public class CouponAdminApiController {

    private final CouponService couponService;

    @PostMapping
    public CouponResponse createCoupon(
            @AuthenticationPrincipal AuthPrincipal authPrincipal,
            @RequestBody CouponCreateRequest request
    ) {
        CouponDto dto = couponService.createCoupon(authPrincipal, request);
        return CouponResponse.response(dto);
    }
}
