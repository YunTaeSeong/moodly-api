package com.moodly.notification.repository;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class EmitterRepository {
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    public SseEmitter save(String emitterId, SseEmitter emitter) {
        emitters.put(emitterId, emitter);
        return emitter;
    }

    public void deleteById(String emitterId) {
        emitters.remove(emitterId);
    }

    public Map<String, SseEmitter> findAllEmitterStartWithById(String userId) {
        return emitters.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(userId + "_"))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void deleteAllEmitterStartWithId(String userId) {
        emitters.entrySet().removeIf(entry -> entry.getKey().startsWith(userId + "_"));
    }
}
