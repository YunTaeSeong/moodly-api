package com.moodly.product.domain;

import com.moodly.common.domain.BaseEntity;
import com.moodly.product.enums.ProductInquiriesStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "product_inquiries",
        indexes = {
                @Index(name = "idx_inquiries_product_id", columnList = "product_id"),
                @Index(name = "idx_inquiries_user_id", columnList = "user_id"),
                @Index(name = "idx_inquiries_status", columnList = "status")
        }
)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ProductInquiries extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "product_id")
    private Long productId;

    @Column(nullable = false, name = "user_id")
    private Long userId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    private ProductInquiriesStatus status = ProductInquiriesStatus.PENDING; // 답변대기 Default

    @Column(columnDefinition = "TEXT")
    private String reply;

    @Column(name = "reply_date")
    private LocalDateTime replyDate;

    @Column(name = "reply_user_id")
    private Long replyUserId;

}
