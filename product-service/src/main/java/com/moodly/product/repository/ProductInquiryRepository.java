package com.moodly.product.repository;

import com.moodly.product.domain.ProductInquiry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductInquiryRepository extends ProductInquiryRepositoryCustom, JpaRepository<ProductInquiry, Long> {
    Page<ProductInquiry> findAllByUserId(Long userId, Pageable pageable);

    Page<ProductInquiry> findAllByProductId(Long productId, Pageable pageable);
}
