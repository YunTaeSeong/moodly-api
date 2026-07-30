package com.moodly.coupon.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "moodly.coupon.flash")
public class CouponFlashProperties {

    private String couponName = "선착순 70% 할인";
    private int totalQuantity = 1000;
}
