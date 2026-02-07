package com.moodly.product.repository;

import com.moodly.product.domain.ProductInquiry;
import com.moodly.product.enums.ProductInquiryStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductInquiryRepositoryCustom {
    // 유저: 내 문의 목록
    Page<ProductInquiry> searchMyInquiries(Long userId, Long productId, ProductInquiryStatus status, String content, Pageable pageable);

    // 관리자: 전체 문의 목록
    Page<ProductInquiry> searchAllAdminInquiries(Long productId, ProductInquiryStatus status, String content, Pageable pageable);
}
