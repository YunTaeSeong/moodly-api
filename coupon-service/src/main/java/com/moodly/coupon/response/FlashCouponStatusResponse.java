package com.moodly.coupon.response;

import com.moodly.coupon.enums.DiscountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlashCouponStatusResponse {

    private Long couponId;
    private String name;
    private BigDecimal discount;
    private DiscountType discountType;
    private Integer totalQuantity;
    private Integer issuedQuantity;
    private Integer remainingQuantity;
    private boolean exhausted;
    private boolean alreadyReceived;
}
