package com.moodly.cart.response;

import com.moodly.cart.dto.CartDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartResponse {

    private Long id;
    private Long userId;
    private Long productId;
    private Integer quantity;
    private Boolean checked;

    public static CartResponse response(CartDto dto) {
        return CartResponse.builder()
                .id(dto.getId())
                .userId(dto.getUserId())
                .productId(dto.getProductId())
                .quantity(dto.getQuantity())
                .checked(dto.getChecked())
                .build();
    }
}
