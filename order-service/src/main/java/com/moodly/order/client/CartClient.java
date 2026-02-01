package com.moodly.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(
        name = "cart-service",
        url = "${cart-service.base_url}"
)
public interface CartClient {

    @GetMapping("/internal/cart/{cartId}")
    CartResponse getCartById(@PathVariable("cartId") Long cartId);

    /**
     * 주문 생성용 : cartIds에 해당하는 장바구니 항목을 상품 정보와 함께 조회
     */
    @GetMapping("/internal/cart/items")
    List<CartItemResponse> getCartItemsWithProductInfo(@RequestParam("ids") List<Long> cartIds);

    /**
     * 주문 완료 후 선택된 장바구니 항목 삭제
     */
    @PostMapping("/internal/cart/items/delete")
    void deleteSelectedItems(
            @RequestParam("userId") Long userId,
            @RequestBody List<Long> cartIds
    );
}
