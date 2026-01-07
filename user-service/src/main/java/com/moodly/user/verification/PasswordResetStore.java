package com.moodly.user.verification;

import java.time.Duration;
import java.util.Optional;

public interface PasswordResetStore {
    Optional<PasswordResetValue> get(String key);
    void save(String key, PasswordResetValue value, Duration ttl);
    void delete(String key);
}
