package com.moodly.cart.domain;

import com.moodly.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(
        name = "carts",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_cart_user_product",
                        columnNames = {"user_id", "product_id"}
                ),
        },
        indexes = {
                @Index(
                        name = "idx_cart_user_id",
                        columnList = "user_id"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Cart extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "user_id")
    private Long userId;

    @Column(nullable = false, name = "product_id")
    private Long productId;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private boolean checked;

    @PrePersist
    protected void onPrePersist() {
        if (this.quantity == null) {
            this.quantity = 1;
        }
        // checked의 기본값 false이지만, service에서 명시적으로 true로 설정
    }
}
