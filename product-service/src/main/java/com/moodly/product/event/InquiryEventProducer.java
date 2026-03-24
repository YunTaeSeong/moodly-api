package com.moodly.product.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * 상품문의 이벤트 Kafka 발행 (POC: 문의 등록 / 답변 등록)
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class InquiryEventProducer {

    public static final String TOPIC_INQUIRY_CREATED = "inquiry-created";
    public static final String TOPIC_INQUIRY_REPLIED = "inquiry-replied";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishInquiryCreated(InquiryCreatedEvent event) {
        try {
            kafkaTemplate.send(TOPIC_INQUIRY_CREATED, event.getEventId(), event);
            log.info("[Kafka] inquiry-created published, eventId={}, inquiryId={}", event.getEventId(), event.getInquiryId());
        } catch (Exception e) {
            log.error("[Kafka] inquiry-created publish failed", e);
        }
    }

    public void publishInquiryReplied(InquiryRepliedEvent event) {
        try {
            kafkaTemplate.send(TOPIC_INQUIRY_REPLIED, event.getEventId(), event);
            log.info("[Kafka] inquiry-replied published, eventId={}, inquiryId={}, targetUserId={}", event.getEventId(), event.getInquiryId(), event.getTargetUserId());
        } catch (Exception e) {
            log.error("[Kafka] inquiry-replied publish failed", e);
        }
    }
}
