package com.moodly.coupon.entity;

import com.moodly.common.exception.BaseException;
import com.moodly.common.exception.GlobalErrorCode;
import com.moodly.coupon.enums.UserCouponStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "user_coupons",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_user_coupon",
                        columnNames = {"user_id", "coupon_id"}
                )
        },
        indexes = {
                @Index(name = "idx_user_coupons_user_status", columnList = "user_id, status"),
                @Index(name = "idx_user_coupons_coupon_id", columnList = "coupon_id"),
                @Index(name = "idx_user_coupons_expires_at", columnList = "expired_at")
        }
)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "coupon_id", nullable = false)
    private Long couponId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private UserCouponStatus status = UserCouponStatus.ISSUED;

    @Column(nullable = false, updatable = false)
    private LocalDateTime receivedAt;

    private LocalDateTime expiredAt;

    private LocalDateTime usedAt;

    @Column(length = 100)
    private String orderId;

    // == 비즈니스 검증 로직 == //

    // 쿠폰 사용
    public void use(String orderId) {

        if (this.status != UserCouponStatus.ISSUED) {
            throw new BaseException(GlobalErrorCode.COUPON_ALREADY_USED);
        }

        LocalDateTime now = LocalDateTime.now();

        if (expiredAt != null && now.isAfter(expiredAt)) {
            throw new BaseException(GlobalErrorCode.COUPON_EXPIRED);
        }

        this.status = UserCouponStatus.USED;
        this.usedAt = now;
        this.orderId = orderId;
    }

    // 취소 시 쿠폰 복구
    public void restore() {
        if (this.status != UserCouponStatus.USED) {
            throw new BaseException(GlobalErrorCode.COUPON_CANCEL_INVALID);
        }

        this.status = UserCouponStatus.ISSUED;
        this.usedAt = null;
        this.orderId = null;
    }

    // 쿠폰 만료
    public void expire() {
        if (this.status == UserCouponStatus.ISSUED
                && expiredAt != null
                && expiredAt.isBefore(LocalDateTime.now())) {

            this.status = UserCouponStatus.EXPIRED;
        }
    }

}
