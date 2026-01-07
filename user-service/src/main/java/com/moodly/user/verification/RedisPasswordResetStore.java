package com.moodly.user.verification;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RedisPasswordResetStore implements PasswordResetStore {

    @Qualifier("passwordResetRedisTemplate")
    private final RedisTemplate<String, PasswordResetValue> redisTemplate;

    @Override
    public Optional<PasswordResetValue> get(String key) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(key));
    }

    @Override
    public void save(String key, PasswordResetValue value, Duration ttl) {
        redisTemplate.opsForValue().set(key, new PasswordResetValue(value.userId(), value.email()), ttl);
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
