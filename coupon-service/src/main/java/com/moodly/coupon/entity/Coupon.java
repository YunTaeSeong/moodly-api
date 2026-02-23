package com.moodly.coupon.entity;

import com.moodly.common.domain.BaseEntity;
import com.moodly.common.exception.BaseException;
import com.moodly.common.exception.GlobalErrorCode;
import com.moodly.coupon.enums.DiscountType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "coupons",
        indexes = {
                @Index(name = "idx_coupons_end_date", columnList = "valid_end_date"),
                @Index(name = "idx_coupons_active", columnList = "valid_start_date, valid_end_date")
        }
)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Coupon extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal discount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DiscountType discountType;

    @Column(nullable = false, precision = 12, scale = 2)
    @Builder.Default
    private BigDecimal minPurchase = BigDecimal.ZERO;

    private LocalDateTime validStartDate;

    private LocalDateTime validEndDate;

    private Integer totalQuantity;

    @Column(nullable = false)
    @Builder.Default
    private Integer issuedQuantity = 0;


    // == 비즈니스 검증 로직 == //

    // 발급 조건 검증
    public void validateIssuable() {
        if (totalQuantity != null && issuedQuantity >= totalQuantity) {
            throw new BaseException(GlobalErrorCode.COUPON_EXHAUSTED);
        }

        LocalDateTime now = LocalDateTime.now();

        if (validStartDate != null && now.isBefore(validStartDate)) {
            throw new BaseException(GlobalErrorCode.INVALID_DATE);
        }

        if (validEndDate != null && now.isAfter(validEndDate)) {
            throw new BaseException(GlobalErrorCode.INVALID_DATE);
        }
    }

    // 수량 증가
    public void incrementIssuedQuantity() {
        this.issuedQuantity++;
    }

    // 주문 적용 검증
    public void validateUsable(BigDecimal orderAmount) {

        validateIssuable();

        // 최소 주문이 0원 보다 작으면 에러 처리
        if (orderAmount.compareTo(minPurchase) < 0) {
            throw new BaseException(GlobalErrorCode.MIN_PURCHASE_NOT_MET);
        }
    }

    // 할인 금액 계산 : order-service(주문) 요청 시 사용
    public BigDecimal calculateDiscountAmount(BigDecimal orderAmount) {
        validateUsable(orderAmount);

        if (discountType == DiscountType.FIXED) {
            return this.discount.min(orderAmount);
        }
        return orderAmount.multiply(this.discount)
                .divide(new BigDecimal("100"), 0, RoundingMode.DOWN);
    }

}
