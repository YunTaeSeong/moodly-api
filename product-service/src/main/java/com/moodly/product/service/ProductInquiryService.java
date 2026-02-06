package com.moodly.product.service;

import com.moodly.common.exception.BaseException;
import com.moodly.common.exception.GlobalErrorCode;
import com.moodly.product.domain.ProductInquiry;
import com.moodly.product.dto.ProductInquiryDto;
import com.moodly.product.enums.ProductInquiryStatus;
import com.moodly.product.repository.ProductInquiryRepository;
import com.moodly.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductInquiryService {

    private final ProductInquiryRepository productInquiryRepository;
    private final ProductRepository productRepository;

    /**
     * 상품 문의 등록
     */
    @Transactional
    public ProductInquiryDto createProductInquiry(Long userId, Long productId, String content) {
        // 상품 문의 등록은 상품을 구입을 안해도 되고, 로그인 한 경우에만 작성 가능
        if(userId == null) {
            throw new BaseException(GlobalErrorCode.MISSING_AUTHORIZATION);
        }

        // 상품 존재 여부
        if(!productRepository.existsById(productId)) {
            throw new BaseException(GlobalErrorCode.PRODUCTID_NOT_FOUND);
        }

        // 상품 등록
        ProductInquiry save = productInquiryRepository.save(ProductInquiry.of(userId, productId, content));

        return ProductInquiryDto.fromEntity(save);
    }

    /**
     * 상품 문의 단건 조회
     */
    @Transactional(readOnly = true)
    public ProductInquiryDto getProductInquiry(Long userId, Long productInquiryId) {
        if (userId == null) {
            throw new BaseException(GlobalErrorCode.MISSING_AUTHORIZATION);
        }

        ProductInquiry inquiry = productInquiryRepository.findById(productInquiryId)
                .orElseThrow(() -> new BaseException(GlobalErrorCode.PRODUCTID_INQUIRY_NOT_FOUND));

        // 내 문의만 허용
        if (!inquiry.getUserId().equals(userId)) {
            throw new BaseException(GlobalErrorCode.MISSING_AUTHORIZATION);
        }

        return ProductInquiryDto.fromEntity(inquiry);

    }

    /**
     * 상품 문의 전체 조회
     */
    @Transactional(readOnly = true)
    public Page<ProductInquiryDto> getAllProductInquiry(
            Long userId,
            Long productId,
            ProductInquiryStatus status,
            String content,
            Pageable pageable
    ) {
        if (userId == null) {
            throw new BaseException(GlobalErrorCode.MISSING_AUTHORIZATION);
        }

        return productInquiryRepository.searchMyInquiries(userId, productId, status, content, pageable)
                .map(ProductInquiryDto::fromEntity);
    }


    /**
     * 상품 문의 수정
     */
    @Transactional
    public ProductInquiryDto updateProductInquiry(Long userId, Long productInquiryId, String content) {
        ProductInquiry productInquiry = productInquiryRepository.findById(productInquiryId)
                .orElseThrow(() -> new BaseException(GlobalErrorCode.PRODUCTID_INQUIRY_NOT_FOUND));

        if(!productInquiry.getUserId().equals(userId)) {
            throw new BaseException(GlobalErrorCode.MISSING_AUTHORIZATION);
        }

        productInquiry.setContent(content);

        return ProductInquiryDto.fromEntity(productInquiry);
    }

    /**
     * 상품 문의 삭제
     */
    @Transactional
    public void deleteProductInquiry(Long userId, Long productInquiryId) {
        ProductInquiry productInquiry = productInquiryRepository.findById(productInquiryId)
                .orElseThrow(() -> new BaseException(GlobalErrorCode.PRODUCTID_INQUIRY_NOT_FOUND));

        if(!productInquiry.getUserId().equals(userId)) {
            throw new BaseException(GlobalErrorCode.MISSING_AUTHORIZATION);
        }

        productInquiryRepository.delete(productInquiry);
    }
}
