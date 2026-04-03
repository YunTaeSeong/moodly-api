package com.moodly.coupon;

import org.springframework.boot.SpringApplication;
import com.moodly.coupon.config.CouponWelcomeProperties;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(scanBasePackages = "com.moodly")
@EnableConfigurationProperties(CouponWelcomeProperties.class)
@ConfigurationPropertiesScan(basePackages = {
        "com.moodly.common.security"
})
@EnableJpaAuditing
public class CouponServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CouponServiceApplication.class, args);
    }
}
