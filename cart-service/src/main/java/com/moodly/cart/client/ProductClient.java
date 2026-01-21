package com.moodly.cart.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "product-service",
        url = "${product-service.base_url}"
)
public interface ProductClient {

    @GetMapping("/internal/product/{productId}")
    ProductResponse getProductById(@PathVariable Long productId);
}
