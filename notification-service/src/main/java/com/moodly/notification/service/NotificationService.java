package com.moodly.notification.service;

import com.moodly.common.exception.BaseException;
import com.moodly.common.exception.GlobalErrorCode;
import com.moodly.notification.domain.Notification;
import com.moodly.notification.domain.NotificationType;
import com.moodly.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    /**
     * 알림 저장
     */
    @Transactional
    public Notification save(Long userId, String eventId, NotificationType type, String title, String message, String link) {
        log.info("[NotificationService] 알림 저장 시도: userId={}, eventId={}, type={}, title={}", userId, eventId, type, title);
        
        try {
            // 멱등성: event_id 이미 있으면 스킵
            if (notificationRepository.existsByEventId(eventId)) {
                log.warn("[NotificationService] 중복 이벤트 스킵: eventId={}", eventId);
                return notificationRepository.findByEventId(eventId).orElse(null);
            }

            Notification notification = Notification.builder()
                    .userId(userId)
                    .eventId(eventId)
                    .notificationType(type)
                    .title(title)
                    .notificationMessage(message)
                    .link(link)
                    .isRead(false)
                    .build();

            Notification saved = notificationRepository.save(notification);

            log.info("[NotificationService] 알림 저장 성공: notificationId={}, userId={}, eventId={}", saved.getId(), userId, eventId);
            return saved;
        } catch (Exception e) {
            log.error("[NotificationService] 알림 저장 실패: userId={}, eventId={}, error={}", userId, eventId, e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 모든 알림 조회
     */
    @Transactional(readOnly = true)
    public Page<Notification> getNotifications(Long userId, Pageable pageable) {
        log.info("[NotificationService] 알림 조회: userId={}, page={}, size={}", userId, pageable.getPageNumber(), pageable.getPageSize());
        Page<Notification> result = notificationRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable);
        log.info("[NotificationService] 조회 결과: 총 개수={}, 현재 페이지 개수={}", result.getTotalElements(), result.getContent().size());
        return result;
    }

    /**
     * 읽지 않은 알림 조회
     */
    @Transactional(readOnly = true)
    public long getUnreadCount(Long userId) {
        long count = notificationRepository.countUnreadByUserId(userId);
        log.info("[NotificationService] 읽지 않은 알림 개수: userId={}, count={}", userId, count);
        return count;
    }

    /**
     * 읽은 알림 조회(개수)
     */
    @Transactional
    public void markAsRead(Long userId, Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new BaseException(GlobalErrorCode.NOTIFICATION_NOT_FOUND));
        if (!notification.getUserId().equals(userId)) {
            throw new BaseException(GlobalErrorCode.MISSING_AUTHORIZATION);
        }
        notification.setIsRead(true);
        notificationRepository.save(notification);
    }

    /**
     * 알림 중 읽지 않은 것만 전부 읽음 처리
     */
    @Transactional
    public void markAllAsRead(Long userId) {
        notificationRepository.findByUserIdOrderByCreatedAtDesc(userId, Pageable.unpaged())
                .getContent()
                .stream()
                .filter(n -> Boolean.FALSE.equals(n.getIsRead())) // 안 읽은 알림: false
                .forEach(n -> {
                    n.setIsRead(true); // 읽음 처림: true
                    notificationRepository.save(n);
                });
    }
}
