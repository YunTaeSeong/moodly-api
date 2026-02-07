package com.moodly.product.controller;

import com.moodly.common.security.principal.AuthPrincipal;
import com.moodly.product.dto.ProductInquiryDto;
import com.moodly.product.enums.ProductInquiryStatus;
import com.moodly.product.request.ProductInquiryReplyRequest;
import com.moodly.product.request.ProductInquiryUpdateRequest;
import com.moodly.product.response.ProductInquiryResponse;
import com.moodly.product.service.ProductInquiryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/product/inquiry")
public class ProductInquiryAdminApiController {

    private final ProductInquiryService productInquiryService;

    @GetMapping
    public Page<ProductInquiryResponse> getAllAdminProductInquiry(
            @AuthenticationPrincipal AuthPrincipal authPrincipal,
            @RequestParam(required = false) Long productId,
            @RequestParam(required = false) ProductInquiryStatus status,
            @RequestParam(required = false) String content,
            @PageableDefault(size = 20, sort = "createdDate", direction = Sort.Direction.DESC)Pageable pageable
    ) {
        return productInquiryService.getAllAdminProductInquiry(authPrincipal, productId, status, content, pageable)
                .map(ProductInquiryResponse::response);
    }

    @PostMapping("/{productInquiryId}/reply")
    public ProductInquiryResponse getAdminReply(
            @AuthenticationPrincipal AuthPrincipal principal,
            @PathVariable Long productInquiryId,
            @Valid @RequestBody ProductInquiryReplyRequest request
    ) {
        ProductInquiryDto dto = productInquiryService.getAdminReply(principal, productInquiryId, request.reply());
        return ProductInquiryResponse.response(dto);
    }

    @PatchMapping("/{productInquiryId}")
    public ProductInquiryResponse updateAdminReply(
            @AuthenticationPrincipal AuthPrincipal principal,
            @PathVariable  Long productInquiryId,
            @Valid @RequestBody ProductInquiryUpdateRequest request
    ) {
        ProductInquiryDto dto = productInquiryService.getAdminUpdate(principal, productInquiryId, request.content());
        return ProductInquiryResponse.response(dto);
    }

    @DeleteMapping("/{productInquiryId}")
    public void deleteAdminReply(
            @AuthenticationPrincipal AuthPrincipal principal,
            @PathVariable Long productInquiryId
    ) {
        productInquiryService.getAdminDelete(principal, productInquiryId);
    }
}
