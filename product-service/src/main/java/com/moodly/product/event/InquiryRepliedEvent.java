package com.moodly.product.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * 관리자 답변 등록 시 발행 이벤트 (ADMIN → 해당 USER 알림용)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InquiryRepliedEvent {

    /** 멱등성 키 */
    private String eventId;

    private Long inquiryId;
    private Long productId;
    /** 알림 받을 사용자(문의 작성자) */
    private Long targetUserId;
    private String replyPreview;

    public static InquiryRepliedEvent of(Long inquiryId, Long productId, Long targetUserId, String replyPreview) {
        return InquiryRepliedEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .inquiryId(inquiryId)
                .productId(productId)
                .targetUserId(targetUserId)
                .replyPreview(replyPreview != null && replyPreview.length() > 50 ? replyPreview.substring(0, 50) + "..." : replyPreview)
                .build();
    }
}
