package com.moodly.coupon.response;

import com.moodly.coupon.dto.UserCouponDto;
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
public class UserCouponResponse {

    private Long id;

    private Long userId;

    private Long couponId;

    private String orderId;

    private UserCouponStatus status;

    private LocalDateTime receivedAt;

    private LocalDateTime expiredAt;

    private LocalDateTime usedAt;

    public static UserCouponResponse response(UserCouponDto dto) {
        return UserCouponResponse.builder()
                .id(dto.getId())
                .userId(dto.getUserId())
                .couponId(dto.getCouponId())
                .orderId(dto.getOrderId())
                .status(dto.getStatus())
                .receivedAt(dto.getReceivedAt())
                .expiredAt(dto.getExpiredAt())
                .usedAt(dto.getUsedAt())
                .build();
    }
}
