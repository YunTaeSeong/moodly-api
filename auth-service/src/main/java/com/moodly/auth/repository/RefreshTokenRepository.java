package com.moodly.auth.repository;

import com.moodly.auth.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByRefreshTokenHash(String refreshTokenHash);

    boolean existsByRefreshTokenHash(String refreshTokenHash);

    void deleteByUserId(Long userId);

    void deleteAllByUserId(Long userId);

    void deleteByRefreshTokenHash(String refreshTokenHash);

    // 만료된 토큰 삭제
    @Modifying
    @Query("delete from RefreshToken rt where rt.expiredAt <= :now")
    int deleteByExpiredAtLessThanEqual(LocalDateTime now);
}
