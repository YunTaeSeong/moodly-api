package com.moodly.user.verification;

import java.time.Duration;
import java.util.Optional;

public interface FindIdVerificationStore {
    void save(String phoneKey, FindIdVerificationValue value, Duration ttl);
    Optional<FindIdVerificationValue> get(String phoneKey);
    void delete(String phoneKey);
}
