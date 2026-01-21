package com.moodly.cart.dto;

import com.moodly.cart.client.ProductResponse;
import com.moodly.cart.domain.Cart;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDto {

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

    public static CartItemDto fromCartDtoAndProduct(CartDto cartDto, ProductResponse product) {
        return CartItemDto.builder()
                .id(cartDto.getId())
                .userId(cartDto.getUserId())
                .productId(cartDto.getProductId())
                .quantity(cartDto.getQuantity())
                .checked(cartDto.getChecked())
                .productName(product != null ? product.getName() : null)
                .productPrice(product != null ? product.getPrice() : null)
                .productDiscount(product != null ? product.getDiscount() : null)
                .productImage(product != null ? product.getImage() : null)
                .productDescription(product != null ? product.getDescription() : null)
                .build();
    }

}
