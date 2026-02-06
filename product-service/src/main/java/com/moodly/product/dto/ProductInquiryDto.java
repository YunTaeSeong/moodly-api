package com.moodly.product.dto;

import com.moodly.product.domain.ProductInquiry;
import com.moodly.product.enums.ProductInquiryStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductInquiryDto {

    private Long id;

    private Long productId;

    private Long userId;

    private String content;

    private ProductInquiryStatus status = ProductInquiryStatus.PENDING; // 답변대기 Default

    private String reply;

    private LocalDateTime replyDate;

    private Long replyId;

    private String replyName;

    // BaseEntity에서 가져올 것
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    public static ProductInquiryDto fromEntity(ProductInquiry entity) {
        return ProductInquiryDto.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .productId(entity.getProductId())
                .content(entity.getContent())
                .status(entity.getStatus())
                .reply(entity.getReply())
                .replyDate(entity.getReplyDate())
                .replyId(entity.getReplyId())
                .replyName(entity.getReplyName())
                .createdDate(entity.getCreatedDate())
                .lastModifiedDate(entity.getLastModifiedDate())
                .build();
    }

}
