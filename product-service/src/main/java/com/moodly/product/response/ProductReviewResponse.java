package com.moodly.product.response;

import com.moodly.product.dto.ProductReviewDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductReviewResponse {

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

    public static ProductReviewResponse from(ProductReviewDto dto) {
        return ProductReviewResponse.builder()
                .id(dto.getId())
                .orderItemId(dto.getOrderItemId())
                .productId(dto.getProductId())
                .userId(dto.getUserId())
                .productName(dto.getProductName())
                .productImage(dto.getProductImage())
                .rating(dto.getRating())
                .content(dto.getContent())
                .reply(dto.getReply())
                .replyDate(dto.getReplyDate())
                .replyName(dto.getReplyName())
                .createdAt(dto.getCreatedAt())
                .images(dto.getImages())
                .build();
    }
}
