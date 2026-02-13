package com.moodly.notification.dto;

import com.moodly.notification.domain.Notification;
import com.moodly.notification.domain.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDto {
    private Long id;

    private Long userId;

    private String eventId;

    private NotificationType notificationType;

    private String title;

    private String notificationMessage;

    private String link;

    private Boolean isRead = false;

    private LocalDateTime createdAt;

    public static NotificationDto fromEntity(Notification notification) {
        return NotificationDto.builder()
                .id(notification.getId())
                .userId(notification.getUserId())
                .eventId(notification.getEventId())
                .notificationType(notification.getNotificationType())
                .title(notification.getTitle())
                .notificationMessage(notification.getNotificationMessage())
                .link(notification.getLink())
                .isRead(notification.getIsRead())
                .build();
    }
}
