package com.moodly.product.service;

import com.moodly.common.exception.BaseException;
import com.moodly.common.exception.GlobalErrorCode;
import com.moodly.common.security.principal.AuthPrincipal;
import com.moodly.product.client.OrderItemReviewEligibilityDto;
import com.moodly.product.client.OrderServiceClient;
import com.moodly.product.domain.ProductReview;
import com.moodly.product.dto.ProductReviewDto;
import com.moodly.product.repository.ProductRepository;
import com.moodly.product.repository.ProductReviewRepository;
import com.moodly.product.request.ProductReviewCreateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductReviewService {

    private final ProductReviewRepository productReviewRepository;
    private final ProductRepository productRepository;
    private final OrderServiceClient orderServiceClient;

    // ----------------------
    // USER
    // ----------------------

    /**
     * 구매후기 작성 (주문 항목 검증 후 저장, 첨부 이미지 최대 3장)
     */
    @Transactional
    public ProductReviewDto createReview(Long userId, ProductReviewCreateRequest request) {
        if (userId == null) {
            throw new BaseException(GlobalErrorCode.MISSING_AUTHORIZATION);
        }
        if (!productRepository.existsById(request.productId())) {
            throw new BaseException(GlobalErrorCode.PRODUCTID_NOT_FOUND);
        }
        if (productReviewRepository.existsByOrderItemId(request.orderItemId())) {
            throw new BaseException(GlobalErrorCode.REVIEW_ALREADY_EXISTS);
        }

        OrderItemReviewEligibilityDto eligibility;
        try {
            eligibility = orderServiceClient.getReviewEligibility(request.orderItemId(), userId);
        } catch (Exception e) {
            log.warn("[Review] order eligibility check failed: {}", e.getMessage());
            throw new BaseException(GlobalErrorCode.REVIEW_ORDER_ITEM_INVALID);
        }
        if (eligibility == null
                || !eligibility.isEligible()
                || eligibility.getProductId() == null
                || !eligibility.getProductId().equals(request.productId())) {
            throw new BaseException(GlobalErrorCode.REVIEW_ORDER_ITEM_INVALID);
        }

        ProductReview review = ProductReview.builder()
                .orderItemId(request.orderItemId())
                .productId(request.productId())
                .userId(userId)
                .productName(eligibility.getProductName())
                .productImage(eligibility.getProductImage())
                .rating(request.rating())
                .content(request.content().trim())
                .build();

        List<String> images = request.images() != null ? request.images() : List.of();
        int order = 0;
        for (String imageUrl : images) {
            if (imageUrl != null && !imageUrl.isBlank()) {
                review.addImage(imageUrl.trim(), order++);
            }
            if (order >= 3) {
                break;
            }
        }

        ProductReview saved = productReviewRepository.save(review);
        return ProductReviewDto.fromEntity(saved);
    }

    /**
     * 상품별 구매후기 목록 조회 (최신순, 페이징)
     */
    @Transactional(readOnly = true)
    public Page<ProductReviewDto> getReviewsByProduct(Long productId, Pageable pageable) {
        if (!productRepository.existsById(productId)) {
            throw new BaseException(GlobalErrorCode.PRODUCTID_NOT_FOUND);
        }
        return productReviewRepository.findByProductIdOrderByCreatedAtDesc(productId, pageable)
                .map(ProductReviewDto::fromEntity);
    }

    /**
     * 내가 작성한 구매후기 목록 조회
     */
    @Transactional(readOnly = true)
    public List<ProductReviewDto> getMyReviews(Long userId) {
        if (userId == null) {
            throw new BaseException(GlobalErrorCode.MISSING_AUTHORIZATION);
        }
        return productReviewRepository.findByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(ProductReviewDto::fromEntity)
                .toList();
    }

    /**
     * 주문 항목에 대한 구매후기 작성 여부 확인
     */
    @Transactional(readOnly = true)
    public boolean existsByOrderItem(Long orderItemId) {
        return productReviewRepository.existsByOrderItemId(orderItemId);
    }

    // ----------------------
    // ADMIN
    // ----------------------

    /**
     * 관리자용 전체 구매후기 목록 조회 (최신순, 페이징)
     */
    @Transactional(readOnly = true)
    public Page<ProductReviewDto> getAllAdminReviews(Pageable pageable) {
        return productReviewRepository.findAllByOrderByCreatedAtDesc(pageable)
                .map(ProductReviewDto::fromEntity);
    }

    /**
     * 관리자 답변 등록 (이미 답변이 있으면 예외)
     */
    @Transactional
    public ProductReviewDto adminReply(AuthPrincipal principal, Long reviewId, String reply) {
        isAdmin(principal);
        ProductReview review = productReviewRepository.findWithImagesById(reviewId)
                .orElseThrow(() -> new BaseException(GlobalErrorCode.REVIEW_NOT_FOUND));
        if (review.getReply() != null && !review.getReply().isBlank()) {
            throw new BaseException(GlobalErrorCode.REVIEW_ALREADY_REPLIED);
        }
        review.setAdminReply("ADMIN", reply.trim());
        return ProductReviewDto.fromEntity(review);
    }

    /**
     * 관리자 구매후기 삭제
     */
    @Transactional
    public void adminDelete(AuthPrincipal principal, Long reviewId) {
        isAdmin(principal);
        ProductReview review = productReviewRepository.findById(reviewId)
                .orElseThrow(() -> new BaseException(GlobalErrorCode.REVIEW_NOT_FOUND));
        productReviewRepository.delete(review);
    }

    /**
     * 관리자 권한 여부 검증
     */
    private void isAdmin(AuthPrincipal principal) {
        if (principal == null || !principal.isAdmin()) {
            throw new BaseException(GlobalErrorCode.MISSING_AUTHORIZATION);
        }
    }
}
