package com.moodly.payment.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RefundStatus {
    PENDING,    // 결제 대기
    COMPLETED,  // 결제 완료
    FAILED      // 결제 실패
}
