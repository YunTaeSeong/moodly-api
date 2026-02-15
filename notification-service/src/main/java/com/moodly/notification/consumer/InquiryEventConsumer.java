package com.moodly.notification.consumer;

import com.moodly.notification.domain.Notification;
import com.moodly.notification.domain.NotificationType;
import com.moodly.notification.event.InquiryEvent;
import com.moodly.notification.service.NotificationService;
import com.moodly.notification.service.SseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "spring.kafka.enabled", havingValue = "true", matchIfMissing = false)
public class InquiryEventConsumer {

    private final NotificationService notificationService;
    private final SseService sseService;
    private final RestTemplate restTemplate;

    @Value("${user.service.url:http://localhost:8082}")
    private String userServiceUrl;

    @PostConstruct
    public void init() {
        log.info("[Kafka] ===== InquiryEventConsumer 초기화 완료 =====");
        log.info("[Kafka] user-service URL: {}", userServiceUrl);
        log.info("[Kafka] Consumer가 활성화되었습니다. inquiry-events 토픽을 구독합니다.");
    }

    //특정 토픽을 구독하고, 메시지가 오면 자동으로 메서드를 실행
    @KafkaListener(topics = "inquiry-events", groupId = "notification-service", containerFactory = "kafkaListenerContainerFactory")
    public void handleInquiryEvent(InquiryEvent event) {
        log.info("[Kafka] ===== 이벤트 수신 시작 =====");
        log.info("[Kafka] eventId={}, type={}, inquiryId={}, productId={}, productName={}, authorUserId={}, targetUserId={}", 
                event.getEventId(), event.getType(), event.getInquiryId(), event.getProductId(), 
                event.getProductName(), event.getAuthorUserId(), event.getTargetUserId());

        try {
            if (event == null) {
                log.error("[Kafka] 이벤트가 null입니다!");
                return;
            }

            if (event.getType() == null || event.getType().isEmpty()) {
                log.error("[Kafka] 이벤트 타입이 없습니다: eventId={}", event.getEventId());
                return;
            }

            NotificationType type;
            try {
                type = NotificationType.valueOf(event.getType());
            } catch (IllegalArgumentException e) {
                log.error("[Kafka] 알 수 없는 이벤트 타입: type={}, eventId={}", event.getType(), event.getEventId());
                return;
            }

            String title;
            String message;
            String link = "/product/" + event.getProductId();

            if ("INQUIRY_CREATED".equals(event.getType())) {
                title = "새로운 상품 문의가 등록되었습니다";
                message = String.format("%s에 대한 새로운 상품 문의가 등록되었습니다.", event.getProductName());
                
                List<Long> adminUserIds = getAdminUserIdsFromUserService();
                log.info("[Kafka] ADMIN 역할 사용자 ID 목록: {}", adminUserIds);
                
                if (adminUserIds.isEmpty()) {
                    log.warn("[Kafka] ADMIN 사용자가 없어서 알림을 저장하지 않습니다.");
                    return;
                }
                
                int successCount = 0;
                for (Long adminUserId : adminUserIds) {
                    if (sendNotificationToUser(adminUserId, event.getEventId(), type, title, message, link)) {
                        successCount++;
                    }
                }
                log.info("[Kafka] 알림 저장 완료: 총 {}명 중 {}명 성공", adminUserIds.size(), successCount);
            } else if ("INQUIRY_REPLIED".equals(event.getType())) {
                title = "상품 문의에 답변이 등록되었습니다";
                message = String.format("%s 상품 문의에 답변이 등록되었습니다: %s", 
                        event.getProductName(), event.getReplyPreview());
                
                if (event.getTargetUserId() == null) {
                    log.warn("[Kafka] targetUserId가 null이어서 알림을 저장하지 않습니다: eventId={}", event.getEventId());
                    return;
                }
                
                sendNotificationToUser(event.getTargetUserId(), event.getEventId(), type, title, message, link);
            } else {
                log.warn("[Kafka] 처리할 수 없는 이벤트 타입: type={}, eventId={}", event.getType(), event.getEventId());
            }
        } catch (Exception e) {
            log.error("[Kafka] ===== 이벤트 처리 실패 =====");
            log.error("[Kafka] eventId={}, error={}", event != null ? event.getEventId() : "null", e.getMessage(), e);
        }
    }

    private boolean sendNotificationToUser(Long userId, String eventId, NotificationType type, 
                                       String title, String message, String link) {
        try {
            String uniqueEventId = eventId + "_" + userId;
            log.info("[Kafka] 알림 저장 시도: userId={}, uniqueEventId={}", userId, uniqueEventId);
            
            Notification notification = notificationService.save(
                    userId,
                    uniqueEventId,
                    type,
                    title,
                    message,
                    link
            );

            if (notification == null) {
                log.error("[Kafka] 알림 저장 실패: notification이 null입니다. userId={}, eventId={}", userId, uniqueEventId);
                return false;
            }

            log.info("[Kafka] 알림 저장 성공: notificationId={}, userId={}, eventId={}", notification.getId(), userId, uniqueEventId);
            
            try {
                sseService.sendNotification(userId, notification);
                log.info("[SSE] 알림 전송 완료: userId={}, notificationId={}", userId, notification.getId());
            } catch (Exception e) {
                log.error("[SSE] 알림 전송 실패: userId={}, notificationId={}", userId, notification.getId(), e);
            }
            
            return true;
        } catch (Exception e) {
            log.error("[Kafka] 알림 저장 중 예외 발생: userId={}, eventId={}", userId, eventId, e);
            return false;
        }
    }

    private List<Long> getAdminUserIdsFromUserService() {
        try {
            String url = userServiceUrl + "/internal/admin/user-ids";
            log.info("[Kafka] user-service API 호출: {}", url);
            Long[] adminUserIds = restTemplate.getForObject(url, Long[].class);
            if (adminUserIds != null && adminUserIds.length > 0) {
                List<Long> result = Arrays.asList(adminUserIds);
                log.info("[Kafka] ADMIN 역할 사용자 ID 목록 조회 성공: {}", result);
                return result;
            }
            log.warn("[Kafka] ADMIN 역할 사용자가 없음");
            return List.of();
        } catch (Exception e) {
            log.error("[Kafka] user-service API 호출 실패, ADMIN 사용자 목록 조회 불가", e);
            return List.of();
        }
    }
}
