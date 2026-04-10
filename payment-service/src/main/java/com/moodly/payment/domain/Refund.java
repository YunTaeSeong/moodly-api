package com.moodly.payment.domain;

import com.moodly.payment.enums.RefundStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "refunds", indexes = {
        @Index(name = "idx_refunds_payment_id", columnList = "payment_id"),
        @Index(name = "idx_refunds_status", columnList = "status")
})
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Refund {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // MSA 구조: 객체 참조 대신 ID 참조 사용
    @Column(name = "payment_id", nullable = false)
    private Long paymentId;

    @Column(name = "amount", precision = 12, scale = 2, nullable = false)
    private BigDecimal amount;

    @Column(name = "reason", length = 500)
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Builder.Default
    private RefundStatus status = RefundStatus.PENDING;

    @Column(name = "refund_key", length = 200)
    private String refundKey;

    @CreatedDate
    @Column(name = "created_date", updatable = false, nullable = false)
    private LocalDateTime createdDate;

    @CreatedBy
    @Column(name = "created_by", updatable = false, nullable = false)
    private String createdBy;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    /**
     * 비즈니스 로직
     */
    // 환불 완료
    public void complete(String refundKey) {
        this.status = RefundStatus.COMPLETED;
        this.refundKey = refundKey;
        this.completedAt = LocalDateTime.now();
    }

    // 환불 실패
    public void fail() {
        this.status = RefundStatus.FAILED;
    }
}
