package com.moodly.coupon.dto;

import com.moodly.coupon.entity.UserCoupon;
import com.moodly.coupon.enums.UserCouponStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCouponDto {
    private Long id;

    private Long userId;

    private Long couponId;

    private UserCouponStatus status;

    private LocalDateTime receivedAt;

    private LocalDateTime expiredAt;

    private LocalDateTime usedAt;

    private String orderId;

    public static UserCouponDto fromEntity(UserCoupon userCoupon) {
        return UserCouponDto.builder()
                .id(userCoupon.getId())
                .userId(userCoupon.getUserId())
                .couponId(userCoupon.getCouponId())
                .orderId(userCoupon.getOrderId())
                .status(userCoupon.getStatus())
                .receivedAt(userCoupon.getReceivedAt())
                .expiredAt(userCoupon.getExpiredAt())
                .usedAt(userCoupon.getUsedAt())
                .build();
    }
}
