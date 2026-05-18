package com.moodly.product.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "order-service", path = "/internal/order")
public interface OrderServiceClient {

    @GetMapping("/item/{orderItemId}/review-eligibility")
    OrderItemReviewEligibilityDto getReviewEligibility(
            @PathVariable("orderItemId") Long orderItemId,
            @RequestParam("userId") Long userId
    );
}
