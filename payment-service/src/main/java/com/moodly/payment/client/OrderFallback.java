package com.moodly.payment.client;

import com.moodly.common.exception.BaseException;
import com.moodly.common.exception.GlobalErrorCode;
import org.springframework.stereotype.Component;

@Component
public class OrderFallback implements OrderServiceClient{

    @Override
    public OrderPaymentSnapshotDto getOrderForPayment(String orderId) {
        throw new BaseException(GlobalErrorCode.INTERNAL_SERVER_ERROR, "payment-service unavailable: getOrderForPayment");
    }

    @Override
    public void completePayment(String orderId, OrderPaymentCompleteFeignRequest body) {
        throw new BaseException(GlobalErrorCode.INTERNAL_SERVER_ERROR, "payment-service unavailable: completePayment");
    }

    @Override
    public void cancelOrderPayment(String orderId) {
        throw new BaseException(GlobalErrorCode.INTERNAL_SERVER_ERROR, "payment-service unavailable: cancelOrderPayment");
    }
}
