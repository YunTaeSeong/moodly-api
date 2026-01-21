package com.moodly.product.controller;

import com.moodly.product.dto.ProductDto;
import com.moodly.product.response.ProductResponse;
import com.moodly.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal/product")
@RequiredArgsConstructor
public class InternalProductController {

    private final ProductService productService;

    @GetMapping("/{productId}")
    public ProductResponse getProductById(@PathVariable Long productId) {
        ProductDto dto = productService.getProductById(productId);
        return ProductResponse.response(dto);
    }
}
