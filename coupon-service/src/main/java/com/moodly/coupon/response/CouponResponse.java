package com.moodly.coupon.response;

import com.moodly.coupon.dto.CouponDto;
import com.moodly.coupon.entity.Coupon;
import com.moodly.coupon.enums.DiscountType;
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
public class CouponResponse {
    private Long id;
    private String name;
    private BigDecimal discount;
    private DiscountType discountType;
    private BigDecimal minPurchase;

    private LocalDateTime validStartDate;
    private LocalDateTime validEndDate;
    private Integer validDays;

    private Integer totalQuantity;
    private Integer issuedQuantity;

    public static CouponResponse response(CouponDto dto) {
        return CouponResponse.builder()
                .id(dto.getId())
                .name(dto.getName())
                .discount(dto.getDiscount())
                .discountType(dto.getDiscountType())
                .minPurchase(dto.getMinPurchase())
                .validStartDate(dto.getValidStartDate())
                .validEndDate(dto.getValidEndDate())
                .validDays(dto.getValidDays())
                .totalQuantity(dto.getTotalQuantity())
                .issuedQuantity(dto.getIssuedQuantity())
                .build();
    }
}
