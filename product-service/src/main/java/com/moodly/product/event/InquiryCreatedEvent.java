package com.moodly.product.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * 상품 문의 등록 시 발행 이벤트 (USER → 관리자 알림용)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InquiryCreatedEvent {

    /** 멱등성 키 (Kafka 메시지 1건당 1개) */
    private String eventId;

    private Long inquiryId;
    private Long productId;
    private Long authorUserId;
    private String productName;
    private String contentPreview;

    public static InquiryCreatedEvent of(Long inquiryId, Long productId, Long authorUserId, String productName, String contentPreview) {
        return InquiryCreatedEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .inquiryId(inquiryId)
                .productId(productId)
                .authorUserId(authorUserId)
                .productName(productName != null ? productName : "")
                .contentPreview(contentPreview != null && contentPreview.length() > 50 ? contentPreview.substring(0, 50) + "..." : contentPreview)
                .build();
    }
}
