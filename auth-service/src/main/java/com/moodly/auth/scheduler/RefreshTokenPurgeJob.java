package com.moodly.auth.scheduler;

import com.moodly.auth.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class RefreshTokenPurgeJob {

    private final RefreshTokenRepository refreshTokenRepository;

    /**
     * refresh-token-ttl-seconds : 7일
     * 새벽 3시 삭제
     */
    @Transactional
    @Scheduled(cron = "0 0 3 * * *")
    public void purgeExpiredRefreshTokens() {
        LocalDateTime now = LocalDateTime.now();
        int deleted = refreshTokenRepository.deleteByExpiredAtLessThanEqual(now);

        if(deleted > 0) {
            log.info("[purgeExpiredRefreshTokens] deletedExpiredTokens={}", deleted);
        }
    }
}
