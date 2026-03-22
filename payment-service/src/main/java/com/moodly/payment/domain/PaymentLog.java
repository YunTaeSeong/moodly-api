package com.moodly.payment.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "payment_logs", indexes = {
        @Index(name = "idx_payment_logs_order_id", columnList = "order_id"),
        @Index(name = "idx_payment_logs_payment_key", columnList = "payment_key"),
        @Index(name = "idx_payment_logs_event_created", columnList = "event_type, created_at")
})
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "payment_id")
    private Long paymentId; // 승인 전에는 null일 수 있음

    @Column(name = "order_id", length = 100, nullable = false)
    private String orderId;

    @Column(name = "payment_key", length = 200)
    private String paymentKey;

    @Column(name = "event_type", length = 50, nullable = false)
    private String eventType;

    /**
     * Hibernate 6 이상에서는 @JdbcTypeCode(SqlTypes.JSON)를 사용하면
     * DB의 JSON 타입과 Java 객체(String, Map 등)를 쉽게 매핑 가능
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "raw_payload", columnDefinition = "json")
    private String rawPayload;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * 비즈니스 로직
     */
    // 로그 생성
    public static PaymentLog createLog(String orderId, String eventType, String payload) {
        return PaymentLog.builder()
                .orderId(orderId)
                .eventType(eventType)
                .paymentKey(payload)
                .createdAt(LocalDateTime.now())
                .build();
    }

}
