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

    /**
     * 오늘의 핫딜 상품 조회 (할인율 높은 순)
     */
    @GetMapping("/hot-deal")
    public List<ProductResponse> getHotDealProducts(
            @RequestParam(defaultValue = "10") int limit
    ) {
        List<ProductDto> productDtos = productService.getHotDealProducts(limit);
        return productDtos.stream()
                .map(ProductResponse::response)
                .toList();
    }

    /**
     * 오늘의 특가 상품 조회 (최신순)
     */
    @GetMapping("/today-special")
    public List<ProductResponse> getTodaySpecialProducts(
            @RequestParam(defaultValue = "10") int limit
    ) {
        List<ProductDto> productDtos = productService.getTodaySpecialProducts(limit);
        return productDtos.stream()
                .map(ProductResponse::response)
                .toList();
    }
}
