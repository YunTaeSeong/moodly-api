package com.moodly.product.service;

import com.moodly.product.event.InquiryEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class InquiryEventService {

    @Autowired(required = false)
    private KafkaTemplate<String, InquiryEvent> kafkaTemplate;

    private static final String TOPIC = "inquiry-events";

    public void publishEvent(InquiryEvent event) {
        log.info("====이벤트 발행 시도 ====");
        log.info("[Kafka] eventId={}, type={}, inquiryId={}, productId={}, productName={}, authorUserId={}, targetUserId={}", 
                event.getEventId(), event.getType(), event.getInquiryId(), event.getProductId(), 
                event.getProductName(), event.getAuthorUserId(), event.getTargetUserId());
        
        if (kafkaTemplate == null) {
            log.error("[Kafka] KafkaTemplate이 null입니다. KAFKA_ENABLED=true로 설정하고 KafkaConfig가 활성화되었는지 확인하세요.");
            return;
        }

        try {
            var future = kafkaTemplate.send(TOPIC, event.getEventId(), event);
            future.whenComplete((result, ex) -> {
                if (ex != null) {
                    log.error("===== 이벤트 발행 실패 (비동기) =====");
                    log.error("[Kafka] eventId={}, error={}", event.getEventId(), ex.getMessage(), ex);
                } else {
                    log.info("===== 이벤트 발행 성공 (비동기) =====");
                    log.info("[Kafka] topic={}, partition={}, offset={}, eventId={}, type={}", 
                            TOPIC, result.getRecordMetadata().partition(), result.getRecordMetadata().offset(), 
                            event.getEventId(), event.getType());
                }
            });
            log.info("===== 이벤트 발행 요청 완료 =====");
            log.info("[Kafka] topic={}, eventId={}, type={}", TOPIC, event.getEventId(), event.getType());
        } catch (Exception e) {
            log.error("===== 이벤트 발행 실패 (동기) =====");
            log.error("eventId={}, error={}", event.getEventId(), e.getMessage(), e);
        }
    }
}
