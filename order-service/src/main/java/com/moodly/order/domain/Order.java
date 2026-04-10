package com.moodly.order.domain;

import com.moodly.common.domain.BaseEntity;
import com.moodly.common.exception.BaseException;
import com.moodly.common.exception.GlobalErrorCode;
import com.moodly.order.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(
        name = "orders",
        indexes = {
                @Index(
                        name = "idx_orders_user_date", columnList = "user_id, created_date"
                ),
                @Index(
                        name = "idx_orders_status", columnList = "status"
                )
        }
)

@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id", nullable = false, unique = true, length = 100)
    private String orderId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "total_amount", precision = 12, scale = 2, nullable = false)
    @Builder.Default
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @Column(name = "discount_amount", precision = 12, scale = 2)
    @Builder.Default
    private BigDecimal discountAmount = BigDecimal.ZERO;

    @Column(name = "shipping_fee", precision = 12, scale = 2)
    @Builder.Default
    private BigDecimal shippingFee = BigDecimal.valueOf(3000);

    @Column(name = "final_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal finalAmount;

    @Column(name = "coupon_id")
    private Long couponId;

    @Column(name = "delivery_address", columnDefinition = "json")
    private String deliveryAddress;

    @Column(name = "customer_name", nullable = false, length = 50)
    private String customerName;

    @Column(name = "customer_phone_number", length = 20)
    private String customerPhoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Builder.Default
    private OrderStatus status = OrderStatus.PENDING_PAYMENT;

    @Column(name = "payment_id", length = 100)
    private String paymentId;

    @Column(name = "delivered_date")
    private LocalDateTime deliveredDate;

    public static Order of(
            String orderId,
            Long userId,
            BigDecimal totalAmount,
            BigDecimal discountAmount,
            BigDecimal shippingFee,
            BigDecimal finalAmount,
            Long couponId,
            String deliveryAddress,
            String customerName,
            String customerPhoneNumber,
            OrderStatus status
    ) {
        return Order.builder()
                .orderId(orderId)
                .userId(userId)
                .totalAmount(totalAmount)
                .discountAmount(discountAmount)
                .shippingFee(shippingFee)
                .finalAmount(finalAmount)
                .couponId(couponId)
                .deliveryAddress(deliveryAddress)
                .customerName(customerName)
                .customerPhoneNumber(customerPhoneNumber)
                .status(status)
                .build();
    }

    /** 결제 승인 전: 본인 주문인지 */
    public void assertOwnedBy(Long userId) {
        if (!Objects.equals(this.userId, userId)) {
            throw new BaseException(GlobalErrorCode.MISSING_AUTHORIZATION);
        }
    }

    /** 결제 금액(Toss 요청 금액)과 주문 최종 금액 일치 여부 */
    public void assertAmountMatches(BigDecimal paidAmount) {
        if (paidAmount == null || this.finalAmount.compareTo(paidAmount) != 0) {
            throw new BaseException(GlobalErrorCode.PAYMENT_AMOUNT_MISMATCH);
        }
    }

    /** 배송지 JSON 존재 여부 */
    public void assertDeliveryAddressPresent() {
        if (this.deliveryAddress == null || this.deliveryAddress.isBlank()) {
            throw new BaseException(GlobalErrorCode.DELIVERY_ADDRESS_REQUIRED);
        }
    }

    /** 결제 대기 상태에서만 승인 가능 */
    public void assertPayable() {
        if (this.status != OrderStatus.PENDING_PAYMENT) {
            throw new BaseException(GlobalErrorCode.ORDER_NOT_PAYABLE);
        }
    }

    /** Toss 결제 완료 후 주문 상태 반영 */
    public void markPaymentCompleted(String externalPaymentKey) {
        assertPayable();
        this.status = OrderStatus.PAYMENT_COMPLETED;
        this.paymentId = externalPaymentKey;
    }
}
