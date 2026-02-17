package com.moodly.notification.controller;

import com.moodly.notification.domain.Notification;
import com.moodly.notification.service.NotificationService;
import com.moodly.notification.service.SseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationApiController {

    private final NotificationService notificationService;
    private final SseService sseService;

    // produces = MediaType.TEXT_EVENT_STREAM_VALUE 설정해야 브라우저 SSE 인식, 없으면 일반 HTTP 응답으로 종료
    @GetMapping(value = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter connectSse(@RequestParam Long userId) {
        log.info("[SSE] 연결 요청: userId={}", userId);
        return sseService.addEmitter(userId);
    }

    @GetMapping
    public ResponseEntity<Page<Notification>> getNotifications(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "20") int size,
            @RequestParam Long userId
    ) {
        log.info("[NotificationController] 알림 조회 요청: userId={}, page={}, size={}", userId, page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<Notification> notifications = notificationService.getNotifications(userId, pageable);
        log.info("[NotificationController] 알림 조회 결과: 총 개수={}, 현재 페이지 개수={}", 
                notifications.getTotalElements(), notifications.getContent().size());
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/unread-count")
    public ResponseEntity<Long> getUnreadCount(@RequestParam Long userId) {
        long count = notificationService.getUnreadCount(userId);
        return ResponseEntity.ok(count);
    }

    @PatchMapping("/{id}/read")
    public ResponseEntity<Void> markAsRead(
            @PathVariable Long id,
            @RequestParam Long userId
    ) {
        notificationService.markAsRead(userId, id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/read-all")
    public ResponseEntity<Void> markAllAsRead(@RequestParam Long userId) {
        notificationService.markAllAsRead(userId);
        return ResponseEntity.ok().build();
    }

}
