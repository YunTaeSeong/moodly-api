package com.moodly.cart.controller;

import com.moodly.cart.dto.CartDto;
import com.moodly.cart.dto.CartItemDto;
import com.moodly.cart.response.CartItemResponse;
import com.moodly.cart.response.CartResponse;
import com.moodly.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/internal/cart")
@RequiredArgsConstructor
public class InternalCartController {

    private final CartService cartService;

    @GetMapping("/{cartId}")
    public CartResponse getCartById(@PathVariable("cartId") Long cartId) {
        CartDto dto = cartService.getCartById(cartId);
        return CartResponse.response(dto);
    }

    /**
     * 주문 생성용: cartIds에 해당하는 장바구니 항목을 상품 정보와 함께 조회
     */
    @GetMapping("/items")
    public List<CartItemResponse> getCartItemsWithProductInfo(@RequestParam("ids") List<Long> cartIds) {
        List<CartItemDto> items = cartService.getCartItemsWithProductInfo(cartIds);
        return items.stream()
                .map(CartItemResponse::response)
                .toList();
    }

    /**
     * 주문 완료 후 선택된 장바구니 항목 삭제 (order-service에서 호출)
     */
    @PostMapping("/items/delete")
    public void deleteSelectedItems(
            @RequestParam("userId") Long userId,
            @RequestBody List<Long> cartIds
    ) {
        cartService.deleteSelectedItems(userId, cartIds);
    }
}
