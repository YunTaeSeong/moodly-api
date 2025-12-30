package com.moodly.auth.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "refresh_tokens",
        indexes = {
                @Index(name = "idx_refresh_user_id", columnList = "user_id"),
                @Index(name = "idx_refresh_expired_at", columnList = "expired_at")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_refresh_token_hash", columnNames = "refresh_token_hash")
        }
)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FK를 엔티티 연관관계로 걸지 않고 userId만 저장
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "refresh_token_hash", nullable = false, length = 64)
    private String refreshTokenHash;

    @Column(name = "expired_at", nullable = false)
    private LocalDateTime expiredAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        if(this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

    @Builder
    private RefreshToken(Long userId, String refreshTokenHash, LocalDateTime expiredAt) {
        this.userId = userId;
        this.refreshTokenHash = refreshTokenHash;
        this.expiredAt = expiredAt;
    }

    public boolean isExpired(LocalDateTime now) {
        return expiredAt.isBefore(now) || expiredAt.equals(now);
    }

}
