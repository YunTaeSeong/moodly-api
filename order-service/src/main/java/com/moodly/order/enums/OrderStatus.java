package com.moodly.order.enums;

public enum OrderStatus {
    PENDING_PAYMENT,    // 주문 생성됨, 결제 대기
    PAYMENT_COMPLETED,  // 결제완료
    PAYMENT_CANCELLED,  // 결제 취소(환불) 완료
    PREPARING_SHIPMENT, // 배송 준비
    SHIPPED,            // 배송 중
    DELIVERED           // 배송 완료
}
