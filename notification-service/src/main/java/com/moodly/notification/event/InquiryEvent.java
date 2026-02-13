package com.moodly.notification.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
