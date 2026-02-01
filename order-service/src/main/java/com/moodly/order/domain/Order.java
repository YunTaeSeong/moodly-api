package com.moodly.order.domain;

import com.moodly.common.domain.BaseEntity;
import com.moodly.order.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
    private OrderStatus status = OrderStatus.PAYMENT_COMPLETED;

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
}
