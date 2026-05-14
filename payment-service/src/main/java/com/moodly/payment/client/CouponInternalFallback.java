package com.moodly.payment.client;

import com.moodly.common.exception.BaseException;
import com.moodly.common.exception.GlobalErrorCode;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CouponInternalFallback implements CouponInternalClient{

    @Override
    public void validateForOrderPayment(Long userId, Long userCouponId, BigDecimal orderProductTotalAmount) {
        throw new BaseException(GlobalErrorCode.INTERNAL_SERVER_ERROR, "payment-service unavailable: validateForOrderPayment");
    }
}
