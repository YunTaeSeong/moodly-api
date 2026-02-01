package com.moodly.order.dto;

import com.moodly.order.domain.Order;
import com.moodly.order.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {

    private Long id;
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
    private String paymentId;
    private LocalDateTime deliveredDate;
    private List<OrderItemDto> items;

    public static OrderDto fromEntity(Order order, List<OrderItemDto> items) {
        return OrderDto.builder()
                .id(order.getId())
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
                .paymentId(order.getPaymentId())
                .deliveredDate(order.getDeliveredDate())
                .items(items != null ? items : List.of())
                .build();
    }
}
