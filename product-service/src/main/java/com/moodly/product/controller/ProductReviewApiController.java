package com.moodly.product.controller;

import com.moodly.common.security.principal.AuthPrincipal;
import com.moodly.product.dto.ProductReviewDto;
import com.moodly.product.request.ProductReviewCreateRequest;
import com.moodly.product.response.ProductReviewResponse;
import com.moodly.product.service.ProductReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product/review")
public class ProductReviewApiController {

    private final ProductReviewService productReviewService;

    @PostMapping
    public ProductReviewResponse createReview(
            @Valid @RequestBody ProductReviewCreateRequest request,
            @AuthenticationPrincipal AuthPrincipal principal
    ) {
        ProductReviewDto dto = productReviewService.createReview(principal.getUserId(), request);
        return ProductReviewResponse.from(dto);
    }

    @GetMapping("/product/{productId}")
    public Page<ProductReviewResponse> getReviewsByProduct(
            @PathVariable Long productId,
            @PageableDefault(size = 50, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return productReviewService.getReviewsByProduct(productId, pageable)
                .map(ProductReviewResponse::from);
    }

    @GetMapping("/my")
    public List<ProductReviewResponse> getMyReviews(
            @AuthenticationPrincipal AuthPrincipal principal
    ) {
        return productReviewService.getMyReviews(principal.getUserId()).stream()
                .map(ProductReviewResponse::from)
                .toList();
    }

    @GetMapping("/exists")
    public Map<String, Boolean> existsByOrderItem(@RequestParam Long orderItemId) {
        return Map.of("exists", productReviewService.existsByOrderItem(orderItemId));
    }
}
