package com.moodly.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(scanBasePackages = "com.moodly")
@EnableJpaAuditing(auditorAwareRef = "paymentAuditAwareImpl")
@ConfigurationPropertiesScan(basePackages = {
        "com.moodly.common.security",
        "com.moodly.payment"
})
@EnableFeignClients(basePackages = "com.moodly.payment.client")
public class PaymentServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaymentServiceApplication.class, args);
    }
}
