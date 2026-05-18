package com.moodly.product.dto;

import com.moodly.product.domain.ProductReview;
import com.moodly.product.domain.ReviewImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductReviewDto {

    private Long id;
    private Long orderItemId;
    private Long productId;
    private Long userId;
    private String productName;
    private String productImage;
    private Integer rating;
    private String content;
    private String reply;
    private LocalDateTime replyDate;
    private String replyName;
    private LocalDateTime createdAt;
    private List<String> images;

    public static ProductReviewDto fromEntity(ProductReview entity) {
        List<String> imageUrls = entity.getImages().stream()
                .sorted(Comparator.comparing(ReviewImage::getDisplayOrder))
                .map(ReviewImage::getImageUrl)
                .toList();
        return ProductReviewDto.builder()
                .id(entity.getId())
                .orderItemId(entity.getOrderItemId())
                .productId(entity.getProductId())
                .userId(entity.getUserId())
                .productName(entity.getProductName())
                .productImage(entity.getProductImage())
                .rating(entity.getRating())
                .content(entity.getContent())
                .reply(entity.getReply())
                .replyDate(entity.getReplyDate())
                .replyName(entity.getReplyName())
                .createdAt(entity.getCreatedAt())
                .images(imageUrls)
                .build();
    }
}
