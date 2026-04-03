package com.moodly.coupon.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "moodly.coupon.welcome")
public class CouponWelcomeProperties {

    // DB 신규회원 쿠폰 이름 CouponDataInitializer.issue 동일
    private String couponName = "신규회원 10% 할인";
}
