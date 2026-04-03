package com.moodly.coupon.response;

import com.moodly.coupon.entity.Coupon;
import com.moodly.coupon.entity.UserCoupon;
import com.moodly.coupon.enums.DiscountType;
import com.moodly.coupon.enums.UserCouponStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCouponDetailResponse {

    private Long id;
    private Long userId;
    private Long couponId;
    private String orderId;
    private UserCouponStatus status;
    private LocalDateTime receivedAt;
    private LocalDateTime expiredAt;
    private LocalDateTime usedAt;

    private String couponName;
    private BigDecimal discount;
    private DiscountType discountType;
    private BigDecimal minPurchase;

    public static UserCouponDetailResponse from(UserCoupon userCoupon, Coupon coupon) {
        return UserCouponDetailResponse.builder()
                .id(userCoupon.getId())
                .userId(userCoupon.getUserId())
                .couponId(userCoupon.getCouponId())
                .orderId(userCoupon.getOrderId())
                .status(userCoupon.getStatus())
                .receivedAt(userCoupon.getReceivedAt())
                .expiredAt(userCoupon.getExpiredAt())
                .usedAt(userCoupon.getUsedAt())
                .couponName(coupon.getName())
                .discount(coupon.getDiscount())
                .discountType(coupon.getDiscountType())
                .minPurchase(coupon.getMinPurchase())
                .build();
    }
}
