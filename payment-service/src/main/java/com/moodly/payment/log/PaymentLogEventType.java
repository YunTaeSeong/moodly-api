package com.moodly.payment.log;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 결제 로그 이벤트 구분(검색, 모니터링)
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PaymentLogEventType {

    public static final String CONFIRM_REQUEST = "CONFIRM_REQUEST";
    public static final String CONFIRM_SUCCESS = "CONFIRM_SUCCESS";
    public static final String CONFIRM_FAILED = "CONFIRM_FAILED";
    public static final String VALIDATION_FAILED = "VALIDATION_FAILED";
}
