package com.moodly.payment.client;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class OrderPaymentSnapshotDto {

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
    private String status;
}
