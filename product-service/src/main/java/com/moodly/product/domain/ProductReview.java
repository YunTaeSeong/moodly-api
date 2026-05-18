package com.moodly.product.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "reviews",
        indexes = {
                @Index(name = "idx_reviews_product_created", columnList = "product_id, created_at"),
                @Index(name = "idx_reviews_user_created", columnList = "user_id, created_at")
        },
        uniqueConstraints = @UniqueConstraint(name = "uk_reviews_order_item", columnNames = "order_item_id")
)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_item_id", nullable = false)
    private Long orderItemId;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "product_name", length = 200)
    private String productName;

    @Column(name = "product_image", length = 500)
    private String productImage;

    @Column(nullable = false)
    private Integer rating;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(columnDefinition = "TEXT")
    private String reply;

    @Column(name = "reply_date")
    private LocalDateTime replyDate;

    @Column(name = "reply_name", length = 50)
    private String replyName;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ReviewImage> images = new ArrayList<>();

    @PrePersist
    void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    public void addImage(String imageUrl, int displayOrder) {
        ReviewImage image = ReviewImage.builder()
                .review(this)
                .imageUrl(imageUrl)
                .displayOrder(displayOrder)
                .build();
        images.add(image);
    }

    public void setAdminReply(String replyName, String reply) {
        this.reply = reply;
        this.replyDate = LocalDateTime.now();
        this.replyName = replyName;
    }
}
