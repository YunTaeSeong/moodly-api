package com.moodly.product.controller;

import com.moodly.common.security.principal.AuthPrincipal;
import com.moodly.product.dto.ProductInquiryDto;
import com.moodly.product.enums.ProductInquiryStatus;
import com.moodly.product.request.ProductInquiryCreateRequest;
import com.moodly.product.request.ProductInquiryUpdateRequest;
import com.moodly.product.response.ProductInquiryResponse;
import com.moodly.product.service.ProductInquiryService;
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
@RequestMapping("/product/inquiry")
public class ProductInquiryApiController {

    private final ProductInquiryService productInquiryService;

    @PostMapping("/{productId}")
    public ProductInquiryResponse createProductInquiry(
            @PathVariable Long productId,
            @Valid @RequestBody ProductInquiryCreateRequest request,
            @AuthenticationPrincipal AuthPrincipal principal
    ) {
        ProductInquiryDto dto = productInquiryService.createProductInquiry(
                principal.getUserId(), productId, request.content()
        );
        return ProductInquiryResponse.response(dto);
    }

    @GetMapping("/{productInquiryId}")
    public ProductInquiryResponse getProductInquiry(
            @PathVariable Long productInquiryId,
            @AuthenticationPrincipal AuthPrincipal authPrincipal
    ) {
        ProductInquiryDto dto = productInquiryService.getProductInquiry(authPrincipal.getUserId(), productInquiryId
        );
        return ProductInquiryResponse.response(dto);
    }

    @GetMapping("/all")
    public Page<ProductInquiryResponse> getAllProductInquiry(
            @AuthenticationPrincipal AuthPrincipal authPrincipal,
            @RequestParam(required = false) Long productId,
            @RequestParam(required = false) ProductInquiryStatus status,
            @RequestParam(required = false) String content,
            @PageableDefault(size = 20, sort = "createdDate", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        return productInquiryService.getAllProductInquiry(
                        authPrincipal.getUserId(),
                        productId,
                        status,
                        content,
                        pageable
                )
                .map(ProductInquiryResponse::response);
    }

    @PatchMapping("/{productInquiryId}/update")
    public ProductInquiryResponse updateProductInquiry(
            @PathVariable Long productInquiryId,
            @AuthenticationPrincipal AuthPrincipal authPrincipal,
            @Valid @RequestBody ProductInquiryUpdateRequest request
    ) {
        ProductInquiryDto dto = productInquiryService.updateProductInquiry(
                authPrincipal.getUserId(), productInquiryId, request.content()
        );
        return ProductInquiryResponse.response(dto);
    }

    @DeleteMapping("/{productInquiryId}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProductInquiry(
            @PathVariable Long productInquiryId,
            @AuthenticationPrincipal AuthPrincipal authPrincipal
    ) {
        productInquiryService.deleteProductInquiry(authPrincipal.getUserId(), productInquiryId);
    }
}
