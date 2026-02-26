package com.moodly.coupon.dto;

import com.moodly.coupon.entity.Coupon;
import com.moodly.coupon.enums.DiscountType;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponDto {

    private Long id;

    private String name;

    private BigDecimal discount;

    private DiscountType discountType;

    private BigDecimal minPurchase = BigDecimal.ZERO;

    private LocalDateTime validStartDate;

    private LocalDateTime validEndDate;

    private Integer validDays;

    private Integer totalQuantity;

    private Integer issuedQuantity;

    public static CouponDto fromEntity(Coupon coupon) {
        return CouponDto.builder()
                .id(coupon.getId())
                .name(coupon.getName())
                .discount(coupon.getDiscount())
                .discountType(coupon.getDiscountType())
                .minPurchase(coupon.getMinPurchase())
                .validStartDate(coupon.getValidStartDate())
                .validEndDate(coupon.getValidEndDate())
                .validDays(coupon.getValidDays())
                .totalQuantity(coupon.getTotalQuantity())
                .issuedQuantity(coupon.getIssuedQuantity())
                .build();
    }
}
