package com.moodly.cart.client;

import com.moodly.common.exception.BaseException;
import com.moodly.common.exception.GlobalErrorCode;
import org.springframework.stereotype.Component;

@Component
public class ProductFallback implements ProductClient{

    @Override
    public ProductResponse getProductById(Long productId) {
        throw new BaseException(GlobalErrorCode.INTERNAL_SERVER_ERROR, "cart-service unavailable: getProductById");
    }
}
