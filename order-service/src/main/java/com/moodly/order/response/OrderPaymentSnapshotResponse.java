package com.moodly.order.response;

import com.moodly.order.domain.Order;
import com.moodly.order.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderPaymentSnapshotResponse {

    private String orderId;
    private Long userId;
    private BigDecimal totalAmount;
    private BigDecimal discountAmount;
    private BigDecimal shippingFee;
    private BigDecimal finalAmount;
    private Long couponId;
    private String deliveryAddress;
    private String customerName;
    private String customerPhoneNumber;
    private OrderStatus status;
    /** Toss paymentKey (결제 완료 후 저장) */
    private String paymentKey;

    public static OrderPaymentSnapshotResponse from(Order order) {
        return OrderPaymentSnapshotResponse.builder()
                .orderId(order.getOrderId())
                .userId(order.getUserId())
                .totalAmount(order.getTotalAmount())
                .discountAmount(order.getDiscountAmount())
                .shippingFee(order.getShippingFee())
                .finalAmount(order.getFinalAmount())
                .couponId(order.getCouponId())
                .deliveryAddress(order.getDeliveryAddress())
                .customerName(order.getCustomerName())
                .customerPhoneNumber(order.getCustomerPhoneNumber())
                .status(order.getStatus())
                .paymentKey(order.getPaymentId())
                .build();
    }
}
