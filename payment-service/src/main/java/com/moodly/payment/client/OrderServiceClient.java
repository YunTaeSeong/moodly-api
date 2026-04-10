package com.moodly.payment.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "order-service", url = "${order-service.base_url}")
public interface OrderServiceClient {

    @GetMapping("/internal/order/payment/{orderId}")
    OrderPaymentSnapshotDto getOrderForPayment(@PathVariable("orderId") String orderId);

    @PostMapping("/internal/order/payment/{orderId}/complete")
    void completePayment(
            @PathVariable("orderId") String orderId,
            @RequestBody OrderPaymentCompleteFeignRequest body
    );
}
