package com.moodly.cart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(scanBasePackages = "com.moodly")
@EnableJpaAuditing(auditorAwareRef = "cartAuditAwareImpl")
@EnableFeignClients
@ConfigurationPropertiesScan(basePackages = {
        "com.moodly.common.security"
})
public class CartServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CartServiceApplication.class, args);
    }
}
