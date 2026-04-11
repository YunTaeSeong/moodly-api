package com.moodly.product.service;

import com.moodly.product.event.InquiryEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class InquiryEventService {

    private final ObjectProvider<KafkaTemplate<String, Object>> kafkaTemplateProvider;

    private static final String TOPIC = "inquiry-events";

    /**
     * 문의 알림 Kafka 발행. 브로커 미기동/장애 시에도 예외를 밖으로 넘기지 않음.
     */
    public void publishEvent(InquiryEvent event) {
        KafkaTemplate<String, Object> kafkaTemplate = kafkaTemplateProvider.getIfAvailable();
        if (kafkaTemplate == null) {
            log.debug("[Kafka] KafkaTemplate 없음(spring.kafka.enabled=false 등). eventId={} 생략", event.getEventId());
            return;
        }

        log.info("[Kafka] eventId={}, type={}, inquiryId={}, productId={}",
                event.getEventId(), event.getType(), event.getInquiryId(), event.getProductId());

        try {
            var future = kafkaTemplate.send(TOPIC, event.getEventId(), event);
            future.whenComplete((result, ex) -> {
                if (ex != null) {
                    log.warn("[Kafka] 발행 실패(비동기) eventId={}, msg={}", event.getEventId(), ex.getMessage());
                    log.debug("[Kafka] 발행 실세", ex);
                } else {
                    log.info("[Kafka] 발행 성공 partition={}, offset={}, eventId={}",
                            result.getRecordMetadata().partition(), result.getRecordMetadata().offset(),
                            event.getEventId());
                }
            });
        } catch (Throwable t) {
            log.warn("[Kafka] 발행 실패(동기) eventId={}, msg={} — 문의 처리는 유지됩니다.",
                    event.getEventId(), t.getMessage());
            log.debug("[Kafka] 동기 실패 상세", t);
        }
    }
}
