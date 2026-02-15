package com.moodly.notification.service;

import com.moodly.common.exception.BaseException;
import com.moodly.notification.domain.Notification;
import com.moodly.notification.domain.NotificationType;
import com.moodly.notification.repository.NotificationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @InjectMocks
    private NotificationService notificationService;

    @Mock
    private NotificationRepository notificationRepository;

    @Test
    @DisplayName("알림 저장 : 성공")
    void 알림_저장_성공() {
        // given
        Long userId = 1L;
        String eventId = "event-1";

        when(notificationRepository.existsByEventId(eventId)).thenReturn(false);

        Notification saved = Notification.builder()
                .id(10L)
                .userId(userId)
                .eventId(eventId)
                .notificationType(NotificationType.INQUIRY_CREATED)
                .isRead(false)
                .build();

        // when
        when(notificationRepository.save(any(Notification.class))).thenReturn(saved);

        Notification result = notificationService.save(
                userId,
                eventId,
                NotificationType.INQUIRY_CREATED,
                "title",
                "message",
                "/link"
        );

        // then
        assertNotNull(result);
        assertEquals(10L, result.getId());
        verify(notificationRepository).save(any(Notification.class));
    }

    @Test
    @DisplayName("알림 저장 : 실패(DB 저장 중 예외)")
    void 알림_저장_실패() {
        // given
        Long userId = 1L;
        String eventId = "event-1";

        when(notificationRepository.existsByEventId(eventId)).thenReturn(false);
        // when
        when(notificationRepository.save(any(Notification.class)))
                .thenThrow(new RuntimeException("DB 오류"));

        // then
        assertThrows(RuntimeException.class, () ->
                notificationService.save(
                        userId,
                        eventId,
                        NotificationType.INQUIRY_CREATED,
                        "title",
                        "message",
                        "/link"
                )
        );
    }

    @Test
    @DisplayName("알림 조회 : 성공")
    void 알림_조회_성공() {
        // given
        Long userId = 1L;
        Pageable pageable = PageRequest.of(0, 10);

        Page<Notification> page = new PageImpl<>(List.of(Notification.builder().id(1L).build()));
        // when
        when(notificationRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable))
                .thenReturn(page);

        Page<Notification> result = notificationService.getNotifications(userId, pageable);

        // then
        assertEquals(1, result.getTotalElements());
    }

    @Test
    @DisplayName("알림 조회 : 실패(DB 예외)")
    void 알림_조회_실패() {
        // given
        Long userId = 1L;
        Pageable pageable = PageRequest.of(0, 10);

        // when
        when(notificationRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable))
                .thenThrow(new RuntimeException("DB 오류"));

        // then
        assertThrows(RuntimeException.class,
                () -> notificationService.getNotifications(userId, pageable));
    }

    @Test
    @DisplayName("읽지 않은 알림 개수 조회 : 성공")
    void 읽지않은알림_조회_성공() {
        // given
        Long userId = 1L;

        when(notificationRepository.countUnreadByUserId(userId)).thenReturn(5L);

        // when
        long result = notificationService.getUnreadCount(userId);

        // then
        assertEquals(5L, result);
    }

    @Test
    @DisplayName("읽지 않은 알림 개수 조회 : 실패(DB 예외)")
    void 읽지않은알림_조회_실패() {
        // given
        Long userId = 1L;

        // when
        when(notificationRepository.countUnreadByUserId(userId))
                .thenThrow(new RuntimeException());

        // then
        assertThrows(RuntimeException.class,
                () -> notificationService.getUnreadCount(userId));
    }


    @Test
    @DisplayName("알림 단건 읽음 처리 : 성공")
    void 알림_단건_읽음처리_성공() {
        // given
        Long userId = 1L;
        Long notificationId = 10L;

        Notification notification = Notification.builder()
                .id(notificationId)
                .userId(userId)
                .isRead(false)
                .build();

        when(notificationRepository.findById(notificationId))
                .thenReturn(Optional.of(notification));

        // when
        notificationService.markAsRead(userId, notificationId);

        // then
        assertTrue(notification.getIsRead());
        verify(notificationRepository).save(notification);
    }

    @Test
    @DisplayName("알림 단건 읽음 처리 : 실패(권한 없음)")
    void 알림_단건_읽음처리_실패() {
        // given
        Long userId = 1L;
        Long notificationId = 10L;

        Notification notification = Notification.builder()
                .id(notificationId)
                .userId(999L) // 다른 사용자
                .isRead(false)
                .build();

        // when
        when(notificationRepository.findById(notificationId))
                .thenReturn(Optional.of(notification));

        // then
        assertThrows(BaseException.class,
                () -> notificationService.markAsRead(userId, notificationId));
    }

    @Test
    @DisplayName("전체 읽음 처리 : 성공")
    void 전체_읽음처리_성공() {
        // given
        Long userId = 1L;

        Notification n1 = Notification.builder().id(1L).userId(userId).isRead(false).build();
        Notification n2 = Notification.builder().id(2L).userId(userId).isRead(false).build();

        Page<Notification> page = new PageImpl<>(List.of(n1, n2));

        when(notificationRepository.findByUserIdOrderByCreatedAtDesc(userId, Pageable.unpaged()))
                .thenReturn(page);

        // when
        notificationService.markAllAsRead(userId);

        // then
        assertTrue(n1.getIsRead());
        assertTrue(n2.getIsRead());
        verify(notificationRepository, times(2)).save(any(Notification.class));
    }

    @Test
    @DisplayName("전체 읽음 처리 : 실패(DB 예외)")
    void 전체_읽음처리_실패() {
        // given
        Long userId = 1L;

        // when
        when(notificationRepository.findByUserIdOrderByCreatedAtDesc(userId, Pageable.unpaged()))
                .thenThrow(new RuntimeException());

        // then
        assertThrows(RuntimeException.class,
                () -> notificationService.markAllAsRead(userId));
    }
}