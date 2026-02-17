package com.moodly.notification.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moodly.notification.domain.Notification;
import com.moodly.notification.repository.EmitterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class SseService {

    private final EmitterRepository emitterRepository;
    private final ObjectMapper objectMapper;
    private static final long DEFAULT_TIMEOUT = 60 * 60 * 1000L; // 1시간

    // lifecycle
    public SseEmitter addEmitter(Long userId) {
        String emitterId = userId + "_" + System.currentTimeMillis();
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);

        // 클라이언트 정상 종료 시 호출
        emitter.onCompletion(() -> {
            log.info("[SSE] Emitter 완료: emitterId={}", emitterId);
            emitterRepository.deleteById(emitterId);
        });

        // DEFAULT_TIMEOUT -> 1시간 지나면 호출
        emitter.onTimeout(() -> {
            log.info("[SSE] Emitter 타임아웃: emitterId={}", emitterId);
            emitterRepository.deleteById(emitterId);
        });

        // 네트워크 끊김 / send 실패 / IO 오류 시 호출
        emitter.onError((ex) -> {
            log.error("[SSE] Emitter 오류: emitterId={}", emitterId, ex);
            emitterRepository.deleteById(emitterId);
        });

        emitterRepository.save(emitterId, emitter);
        log.info("[SSE] Emitter 추가: emitterId={}, userId={}", emitterId, userId);

        // 연결 확인 메시지 전송
        sendHeartbeat(userId);
        
        return emitter;
    }

    public void sendNotification(Long userId, Notification notification) {
        Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithById(String.valueOf(userId));
        
        if (emitters.isEmpty()) {
            log.warn("[SSE] 연결된 emitter가 없습니다: userId={}", userId);
            return;
        }

        String eventData;
        try {
            eventData = objectMapper.writeValueAsString(notification);
        } catch (Exception e) {
            log.error("[SSE] 알림 직렬화 실패: notificationId={}", notification.getId(), e);
            return;
        }

        emitters.forEach((emitterId, emitter) -> {
            try {
                emitter.send(SseEmitter.event()
                        .name("notification")
                        .data(eventData));
                log.info("[SSE] 알림 전송 성공: emitterId={}, notificationId={}", emitterId, notification.getId());
            } catch (IOException e) {
                log.error("[SSE] 알림 전송 실패: emitterId={}, notificationId={}", emitterId, notification.getId(), e);
                emitterRepository.deleteById(emitterId);
            }
        });
    }

    @Scheduled(fixedRate = 30000) // 30초마다
    // SSE 연결을 끊기지 않게 유지하기 위한 heartbeat
    public void sendHeartbeat() {
        // 모든 emitter에 heartbeat 전송
        emitterRepository.findAllEmitterStartWithById("").forEach((emitterId, emitter) -> {
            try {
                emitter.send(SseEmitter.event()
                        .name("heartbeat")
                        .data("ping"));
            } catch (IOException e) {
                log.error("[SSE] Heartbeat 전송 실패: emitterId={}", emitterId, e);
                emitterRepository.deleteById(emitterId);
            }
        });
    }

    private void sendHeartbeat(Long userId) {
        Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithById(String.valueOf(userId));
        emitters.forEach((emitterId, emitter) -> {
            try {
                emitter.send(SseEmitter.event()
                        .name("heartbeat")
                        .data("connected"));
            } catch (IOException e) {
                log.error("[SSE] Heartbeat 전송 실패: emitterId={}", emitterId, e);
            }
        });
    }
}
