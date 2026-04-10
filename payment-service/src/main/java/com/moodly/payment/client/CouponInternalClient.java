package com.moodly.payment.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "coupon-service", url = "${coupon-service.base_url}")
public interface CouponInternalClient {

    @GetMapping("/internal/coupon/payment/validate")
    void validateForOrderPayment(
            @RequestParam("userId") Long userId,
            @RequestParam("userCouponId") Long userCouponId,
            @RequestParam("orderProductTotalAmount") BigDecimal orderProductTotalAmount
    );
}
