package com.moodly.product.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InquiryEvent {
    private String eventId; // 이벤트 UUID, 멱등 처리
    private Long inquiryId;
    private Long productId;
    private String productName;
    private Long authorUserId; // 문의 작성자 Id
    private Long targetUserId; // 문의 수신자 Id
    private String type; // INQUIRY_CREATED, INQUIRY_REPLIED
    private String replyPreview;

    public static InquiryEvent inquiryCreated(Long inquiryId, Long productId, String productName, Long authorUserId) {
        return new InquiryEvent(
                UUID.randomUUID().toString(),
                inquiryId,
                productId,
                productName,
                authorUserId,
                null,
                "INQUIRY_CREATED",
                null
        );
    }

    public static InquiryEvent inquiryReplied(Long inquiryId, Long productId, String productName, Long targetUserId, String replyPreview) {
        return new InquiryEvent(
                UUID.randomUUID().toString(),
                inquiryId,
                productId,
                productName,
                null,
                targetUserId,
                "INQUIRY_REPLIED",
                replyPreview
        );
    }
}
