package com.moodly.product.controller;

import com.moodly.common.security.principal.AuthPrincipal;
import com.moodly.product.dto.ProductReviewDto;
import com.moodly.product.request.ProductReviewReplyRequest;
import com.moodly.product.response.ProductReviewResponse;
import com.moodly.product.service.ProductReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping({"/internal/product/review", "/product/admin/review"})
public class ProductReviewAdminApiController {

    private final ProductReviewService productReviewService;

    @GetMapping
    public Page<ProductReviewResponse> getAllAdminReviews(
            @PageableDefault(size = 50, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return productReviewService.getAllAdminReviews(pageable)
                .map(ProductReviewResponse::from);
    }

    @PostMapping("/{reviewId}/reply")
    public ProductReviewResponse adminReply(
            @AuthenticationPrincipal AuthPrincipal principal,
            @PathVariable Long reviewId,
            @Valid @RequestBody ProductReviewReplyRequest request
    ) {
        ProductReviewDto dto = productReviewService.adminReply(principal, reviewId, request.reply());
        return ProductReviewResponse.from(dto);
    }

    @DeleteMapping("/{reviewId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void adminDelete(
            @AuthenticationPrincipal AuthPrincipal principal,
            @PathVariable Long reviewId
    ) {
        productReviewService.adminDelete(principal, reviewId);
    }
}
