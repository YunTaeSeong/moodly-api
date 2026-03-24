package com.moodly.notification.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Kafka inquiry-created 메시지 역직렬화용 (product-service 이벤트와 필드 일치)
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class InquiryCreatedEventPayload {
    private String eventId;
    private Long inquiryId;
    private Long productId;
    private Long authorUserId;
    private String productName;
    private String contentPreview;
}
