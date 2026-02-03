package com.moodly.order.response;

import com.moodly.order.dto.OrderDto;
import com.moodly.order.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {

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
    private List<OrderItemResponse> items;

    public static OrderResponse from(OrderDto dto) {
        return OrderResponse.builder()
                .id(dto.getId())
                .orderId(dto.getOrderId())
                .userId(dto.getUserId())
                .totalAmount(dto.getTotalAmount())
                .discountAmount(dto.getDiscountAmount())
                .shippingFee(dto.getShippingFee())
                .finalAmount(dto.getFinalAmount())
                .couponId(dto.getCouponId())
                .deliveryAddress(dto.getDeliveryAddress())
                .customerName(dto.getCustomerName())
                .customerPhoneNumber(dto.getCustomerPhoneNumber())
                .status(dto.getStatus())
                .paymentId(dto.getPaymentId())
                .deliveredDate(dto.getDeliveredDate())
                .items(OrderItemResponse.fromList(dto.getItems()))
                .build();
    }

    public static List<OrderResponse> fromList(List<OrderDto> dto) {
        return dto.stream()
                .map(OrderResponse::from)
                .toList();
    }
}
