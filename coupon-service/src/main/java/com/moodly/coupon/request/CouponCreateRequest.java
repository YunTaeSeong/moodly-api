package com.moodly.coupon.request;

import com.moodly.coupon.enums.DiscountType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CouponCreateRequest {
    private String name;
    private BigDecimal discount;
    private DiscountType discountType;
    private BigDecimal minPurchase;

    private LocalDateTime validStartDate;
    private LocalDateTime validEndDate;

    private Integer validDays;

    private Integer totalQuantity;
}
