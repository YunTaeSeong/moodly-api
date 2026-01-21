package com.moodly.cart.response;

import com.moodly.cart.dto.CartItemDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemResponse {

    private Long id;
    private Long userId;
    private Long productId;
    private Integer quantity;
    private Boolean checked;
    
    // 상품 정보
    private String productName;
    private BigDecimal productPrice;
    private Integer productDiscount;
    private String productImage;
    private String productDescription;

    public static CartItemResponse response(CartItemDto dto) {
        return CartItemResponse.builder()
                .id(dto.getId())
                .userId(dto.getUserId())
                .productId(dto.getProductId())
                .quantity(dto.getQuantity())
                .checked(dto.getChecked())
                .productName(dto.getProductName())
                .productPrice(dto.getProductPrice())
                .productDiscount(dto.getProductDiscount())
                .productImage(dto.getProductImage())
                .productDescription(dto.getProductDescription())
                .build();
    }
}
