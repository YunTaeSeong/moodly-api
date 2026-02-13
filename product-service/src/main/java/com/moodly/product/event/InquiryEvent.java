package com.moodly.product.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InquiryEvent {
    private String eventId;
    private Long inquiryId;
    private Long productId;
    private String productName;
    private Long authorUserId;
    private Long targetUserId;
    private String type;
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
