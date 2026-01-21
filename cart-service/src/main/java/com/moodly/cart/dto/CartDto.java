package com.moodly.cart.dto;

import com.moodly.cart.domain.Cart;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDto {

    private Long id;
    private Long userId;
    private Long productId;
    private Integer quantity;
    private Boolean checked;

    public static CartDto fromEntity(Cart cart) {
        return CartDto.builder()
                .id(cart.getId())
                .userId(cart.getUserId())
                .productId(cart.getProductId())
                .quantity(cart.getQuantity() != null ? cart.getQuantity() : 1)
                .checked(cart.isChecked())
                .build();
    }
}
