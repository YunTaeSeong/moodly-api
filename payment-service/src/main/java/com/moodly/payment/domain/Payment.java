package com.moodly.payment.domain;

import com.moodly.common.domain.BaseEntity;
import com.moodly.payment.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments", indexes = {
        @Index(name = "idx_payments_order_id", columnList = "order_id"),
        @Index(name = "idx_payments_status", columnList = "status"),
        @Index(name = "idx_payments_created_date", columnList = "created_date"),
        @Index(name = "idx_payments_user_created", columnList = "user_id, created_date")
})
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id", length = 100, nullable = false)
    private String orderId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "amount", precision = 12, scale = 2, nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Builder.Default
    private PaymentStatus status = PaymentStatus.PENDING;

    @Column(name = "payment_key", length = 200, unique = true)
    private String paymentKey;

    @Column(name = "payment_method", length = 50)
    private String paymentMethod;

    @Column(name = "order_name", length = 200)
    private String orderName;

    @Column(name = "customer_name", length = 50)
    private String customerName;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Column(name = "fail_reason", length = 500)
    private String failReason;

    @Column(name = "fail_code", length = 50)
    private String failCode;

    /**
     * 비즈니스 로직
     */
    // 승인
    public void markAsApproved(String paymentKey, String paymentMethod, LocalDateTime approvedAt) {
        this.status = PaymentStatus.APPROVED;
        this.paymentKey = paymentKey;
        this.paymentMethod = paymentMethod;
        this.approvedAt = approvedAt;
    }

    // 실패
    public void markAsFailed(String failCode, String failReason) {
        this.status = PaymentStatus.FAILED;
        this.failCode = failCode;
        this.failReason = failReason;
    }

    public boolean isApproved() {
        return this.status == PaymentStatus.APPROVED;
    }

    public void markAsCancelled() {
        this.status = PaymentStatus.CANCELLED;
    }

    /** Toss 승인 성공 직후 영속화용 */
    public static Payment createApproved(
            String orderId,
            Long userId,
            BigDecimal amount,
            String paymentKey,
            String paymentMethod,
            LocalDateTime approvedAt,
            String orderName,
            String customerName
    ) {
        Payment p = new Payment();
        p.orderId = orderId;
        p.userId = userId;
        p.amount = amount;
        p.paymentKey = paymentKey;
        p.paymentMethod = paymentMethod;
        p.approvedAt = approvedAt;
        p.orderName = orderName;
        p.customerName = customerName;
        p.status = PaymentStatus.APPROVED;
        return p;
    }

    /** PG 승인 거절 등 */
    public static Payment createFailed(
            String orderId,
            Long userId,
            BigDecimal amount,
            String paymentKey,
            String orderName,
            String customerName,
            String failCode,
            String failReason
    ) {
        Payment p = new Payment();
        p.orderId = orderId;
        p.userId = userId;
        p.amount = amount;
        p.paymentKey = paymentKey;
        p.orderName = orderName;
        p.customerName = customerName;
        p.status = PaymentStatus.FAILED;
        p.failCode = failCode;
        p.failReason = failReason;
        return p;
    }

}
