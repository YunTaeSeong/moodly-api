package com.moodly.notification.controller;

import com.moodly.notification.service.SsePushService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * SSE 구독: POC에서는 userId 쿼리 파라미터로 구독 (실서비스에서는 JWT에서 추출)
 */
@Slf4j
@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationSseController {

    private final SsePushService ssePushService;

    @GetMapping(value = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(@RequestParam Long userId) {
        SseEmitter emitter = new SseEmitter(0L);
        ssePushService.register(userId, emitter);
        log.info("[SSE] subscribed userId={}", userId);
        return emitter;
    }
}
