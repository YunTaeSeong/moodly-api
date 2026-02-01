package com.moodly.order.client;

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
}
