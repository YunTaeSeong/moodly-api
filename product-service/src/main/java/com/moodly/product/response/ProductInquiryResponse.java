package com.moodly.product.response;

import com.moodly.product.domain.ProductInquiry;
import com.moodly.product.dto.ProductInquiryDto;
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
public class ProductInquiryResponse {
    private Long id;
    private Long productId;
    private Long userId;
    private String content;
    private ProductInquiryStatus status;
    private String reply;
    private LocalDateTime replyDate;
    private Long replyId;
    private String replyName;

    // BaseEntity에서 가져올 것
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    public static ProductInquiryResponse response(ProductInquiryDto dto) {
        return ProductInquiryResponse.builder()
                .id(dto.getId())
                .userId(dto.getUserId())
                .productId(dto.getProductId())
                .content(dto.getContent())
                .status(dto.getStatus())
                .reply(dto.getReply())
                .replyDate(dto.getReplyDate())
                .replyId(dto.getReplyId())
                .replyName(dto.getReplyName())
                .createdDate(dto.getCreatedDate())
                .lastModifiedDate(dto.getLastModifiedDate())
                .build();
    }
}
