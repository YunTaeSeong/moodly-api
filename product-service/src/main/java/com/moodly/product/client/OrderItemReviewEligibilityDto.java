package com.moodly.product.client;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderItemReviewEligibilityDto {
    private Long orderItemId;
    private Long productId;
    private String productName;
    private String productImage;
    private String orderId;
    private String orderStatus;
    private boolean eligible;
}
