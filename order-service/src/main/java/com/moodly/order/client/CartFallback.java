package com.moodly.order.client;

import com.moodly.common.exception.BaseException;
import com.moodly.common.exception.GlobalErrorCode;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartFallback implements CartClient{

    @Override
    public CartResponse getCartById(Long cartId) {
        throw new BaseException(GlobalErrorCode.INTERNAL_SERVER_ERROR, "order-service unavailable: getCartById");
    }

    @Override
    public List<CartItemResponse> getCartItemsWithProductInfo(List<Long> cartIds) {
        throw new BaseException(GlobalErrorCode.INTERNAL_SERVER_ERROR, "order-service unavailable: getCartItemsWithProductInfo");
    }

    @Override
    public void deleteSelectedItems(Long userId, List<Long> cartIds) {
        throw new BaseException(GlobalErrorCode.INTERNAL_SERVER_ERROR, "order-service unavailable: deleteSelectedItems");
    }
}
