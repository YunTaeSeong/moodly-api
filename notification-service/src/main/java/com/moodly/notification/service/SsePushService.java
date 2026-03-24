package com.moodly.notification.service;

import com.moodly.notification.domain.Notification;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * user_id별 SseEmitter 보관, 알림 저장 시 해당 유저에게 즉시 push
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SsePushService {

    private final ObjectMapper objectMapper;

    /** user_id -> 해당 유저의 SseEmitter 목록 */
    private final Map<Long, List<SseEmitter>> userEmitters = new ConcurrentHashMap<>();

    public void register(Long userId, SseEmitter emitter) {
        userEmitters.computeIfAbsent(userId, k -> new CopyOnWriteArrayList<>()).add(emitter);
        emitter.onCompletion(() -> remove(userId, emitter));
        emitter.onTimeout(() -> remove(userId, emitter));
    }

    private void remove(Long userId, SseEmitter emitter) {
        List<SseEmitter> list = userEmitters.get(userId);
        if (list != null) {
            list.remove(emitter);
            if (list.isEmpty()) userEmitters.remove(userId);
        }
    }

    /**
     * 저장된 알림을 해당 user_id 구독자에게 즉시 전송
     */
    public void pushToUser(Long userId, Notification notification) {
        List<SseEmitter> list = userEmitters.get(userId);
        if (list == null || list.isEmpty()) return;
        String data = toJson(notification);
        if (data == null) return;
        list.forEach(emitter -> {
            try {
                emitter.send(SseEmitter.event().name("notification").data(data));
            } catch (IOException e) {
                log.warn("SSE send failed for userId={}", userId, e);
                remove(userId, emitter);
            }
        });
    }

    private String toJson(Notification n) {
        try {
            return objectMapper.writeValueAsString(Map.of(
                    "id", n.getId(),
                    "title", n.getTitle(),
                    "message", n.getNotificationMessage(),
                    "type", n.getNotificationType().name(),
                    "link", n.getLink() != null ? n.getLink() : "",
                    "createdAt", n.getCreatedAt() != null ? n.getCreatedAt().toString() : ""
            ));
        } catch (JsonProcessingException e) {
            log.warn("notification toJson failed", e);
            return null;
        }
    }
}
