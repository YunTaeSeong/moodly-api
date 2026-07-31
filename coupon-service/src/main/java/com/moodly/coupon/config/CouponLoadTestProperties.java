package com.moodly.coupon.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "moodly.coupon.load-test")
public class CouponLoadTestProperties {

    /** local/docker 부하 테스트 전용 internal API 활성화 */
    private boolean enabled = false;
}
