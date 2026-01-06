package com.moodly.user.verification;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Optional;

@Component
public class RedisFindIdVerificationStore implements  FindIdVerificationStore {

    private final RedisTemplate<String, FindIdVerificationValue> redisTemplate;

    public RedisFindIdVerificationStore(
            @Qualifier("findIdRedisTemplate")
            RedisTemplate<String, FindIdVerificationValue> redisTemplate
    ) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void save(String phoneKey, FindIdVerificationValue value, Duration ttl) {
        redisTemplate.opsForValue().set(phoneKey, value, ttl);
    }

    @Override
    public Optional<FindIdVerificationValue> get(String phoneKey) {
        Object key = redisTemplate.opsForValue().get(phoneKey);
        if (key == null) return Optional.empty();
        return Optional.of((FindIdVerificationValue) key);
    }

    @Override
    public void delete(String phoneKey) {
        redisTemplate.delete(phoneKey);
    }
}
