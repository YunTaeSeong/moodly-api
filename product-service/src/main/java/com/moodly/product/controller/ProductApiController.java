package com.moodly.product.controller;

import com.moodly.product.dto.ProductDto;
import com.moodly.product.response.ProductResponse;
import com.moodly.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductApiController {

    private final ProductService productService;

    @GetMapping
    public List<ProductResponse> getAllProducts() {
        List<ProductDto> allProducts = productService.getAllProducts();
        return allProducts.stream()
                .map(ProductResponse::response)
                .toList();
    }

    @GetMapping("/{productId}")
    public ProductResponse getProductById(
            @PathVariable Long productId
    ) {
        ProductDto dto = productService.getProductById(productId);
        return ProductResponse.response(dto);
    }
}
