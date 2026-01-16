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

    /**
     * 전체 상품조회, 카테고리별 상품 조회(RequestParam)
     */
    @GetMapping
    public List<ProductResponse> getProducts(
            @RequestParam(required = false) Long categoryId
    ) {
        List<ProductDto> productDtos;
        if (categoryId != null) {
            productDtos = productService.getProductsByCategoryId(categoryId);
        } else {
            productDtos = productService.getAllProducts();
        }

        return productDtos.stream()
                .map(ProductResponse::response)
                .toList();
    }


    /**
     * 상품 상세 조회
     */
    @GetMapping("/{productId}")
    public ProductResponse getProductById(
            @PathVariable("productId") Long productId
    ) {
        ProductDto dto = productService.getProductById(productId);
        return ProductResponse.response(dto);
    }

    /**
     * 상품명 검색 (자동완성용)
     */
    @GetMapping("/search")
    public List<ProductResponse> searchProducts(
            @RequestParam String keyword
    ) {
        List<ProductDto> productDtos = productService.searchByName(keyword);
        return productDtos.stream()
                .map(ProductResponse::response)
                .toList();
    }
}
