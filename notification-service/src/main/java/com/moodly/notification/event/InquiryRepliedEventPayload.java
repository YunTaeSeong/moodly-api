package com.moodly.notification.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Kafka inquiry-replied 메시지 역직렬화용
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class InquiryRepliedEventPayload {
    private String eventId;
    private Long inquiryId;
    private Long productId;
    private Long targetUserId;
    private String replyPreview;
}
