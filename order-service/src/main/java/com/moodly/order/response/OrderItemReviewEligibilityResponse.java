package com.moodly.order.response;

import com.moodly.order.domain.Order;
import com.moodly.order.domain.OrderItem;
import com.moodly.order.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.EnumSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemReviewEligibilityResponse {

    private static final Set<OrderStatus> REVIEWABLE = EnumSet.of(
            OrderStatus.PAYMENT_COMPLETED,
            OrderStatus.PREPARING_SHIPMENT,
            OrderStatus.SHIPPED,
            OrderStatus.DELIVERED
    );

    private Long orderItemId;
    private Long productId;
    private String productName;
    private String productImage;
    private String orderId;
    private OrderStatus orderStatus;
    private boolean eligible;

    public static OrderItemReviewEligibilityResponse from(OrderItem item) {
        Order order = item.getOrder();
        OrderStatus status = order.getStatus();
        return OrderItemReviewEligibilityResponse.builder()
                .orderItemId(item.getId())
                .productId(item.getProductId())
                .productName(item.getProductName())
                .productImage(item.getProductImage())
                .orderId(order.getOrderId())
                .orderStatus(status)
                .eligible(REVIEWABLE.contains(status))
                .build();
    }
}
