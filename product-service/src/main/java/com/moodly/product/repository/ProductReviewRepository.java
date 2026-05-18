package com.moodly.product.repository;

import com.moodly.product.domain.ProductReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {

    boolean existsByOrderItemId(Long orderItemId);

    @EntityGraph(attributePaths = "images")
    Page<ProductReview> findByProductIdOrderByCreatedAtDesc(Long productId, Pageable pageable);

    @EntityGraph(attributePaths = "images")
    Page<ProductReview> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);

    @EntityGraph(attributePaths = "images")
    Page<ProductReview> findAllByOrderByCreatedAtDesc(Pageable pageable);

    @EntityGraph(attributePaths = "images")
    Optional<ProductReview> findWithImagesById(Long id);

    @EntityGraph(attributePaths = "images")
    List<ProductReview> findByUserIdOrderByCreatedAtDesc(Long userId);
}
