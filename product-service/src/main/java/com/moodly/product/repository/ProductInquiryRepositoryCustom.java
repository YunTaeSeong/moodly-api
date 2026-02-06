package com.moodly.product.repository;

import com.moodly.product.domain.ProductInquiry;
import com.moodly.product.enums.ProductInquiryStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductInquiryRepositoryCustom {
    Page<ProductInquiry> searchMyInquiries(Long userId, Long productId, ProductInquiryStatus status, String content, Pageable pageable);
}
