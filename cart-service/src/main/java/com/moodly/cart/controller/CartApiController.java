package com.moodly.cart.controller;

import com.moodly.cart.dto.CartDto;
import com.moodly.cart.dto.CartItemDto;
import com.moodly.cart.request.AddCartRequest;
import com.moodly.cart.request.DeleteSelectedRequest;
import com.moodly.cart.request.UpdateCheckedRequest;
import com.moodly.cart.response.CartItemResponse;
import com.moodly.cart.response.CartResponse;
import com.moodly.cart.service.CartService;
import com.moodly.common.security.principal.AuthPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartApiController {

    private final CartService cartService;

    /**
     * 장바구니 추가
     */
    @PostMapping
    public CartResponse addToCart(
            @Valid @RequestBody AddCartRequest request,
            @AuthenticationPrincipal AuthPrincipal principal
    ) {
        CartDto cartDto = cartService.addToCart(
                principal.getUserId(),
                request.productId(),
                request.quantity()
        );
        return CartResponse.response(cartDto);
    }

    /**
     * 장바구니 조회 (상품 정보 포함)
     */
    @GetMapping
    public List<CartItemResponse> getCartItemsWithProductInfo(
            @AuthenticationPrincipal AuthPrincipal principal
    ) {
        List<CartItemDto> cartItemDtos = cartService.getCartItemsWithProductInfo(principal.getUserId());
        return cartItemDtos.stream()
                .map(CartItemResponse::response)
                .toList();
    }

    /**
     * 수량 변경
     */
    @PatchMapping("/{cartId}/quantity")
    public void updateQuantity(
            @PathVariable Long cartId,
            @RequestParam Integer quantity,
            @AuthenticationPrincipal AuthPrincipal principal
    ) {
        cartService.updateQuantity(principal.getUserId(), cartId, quantity);
    }

    /**
     * 장바구니 삭제
     */
    @DeleteMapping("/{cartId}")
    public void deleteCartItem(
            @PathVariable Long cartId,
            @AuthenticationPrincipal AuthPrincipal principal
    ) {
        cartService.deleteCartItem(principal.getUserId(), cartId);
    }

    /**
     * 체크박스 선택/해제
     */
    @PatchMapping("/{cartId}/checked")
    public void updateChecked(
            @PathVariable Long cartId,
            @Valid @RequestBody UpdateCheckedRequest request,
            @AuthenticationPrincipal AuthPrincipal principal
    ) {
        cartService.updateChecked(principal.getUserId(), cartId, request.checked());
    }

    /**
     * 전체 선택/해제
     */
    @PatchMapping("/checked/all")
    public void updateAllChecked(
            @RequestParam Boolean checked,
            @AuthenticationPrincipal AuthPrincipal principal
    ) {
        cartService.updateAllChecked(principal.getUserId(), checked);
    }

    /**
     * 선택된 상품 전체 삭제
     */
    @DeleteMapping("/selected")
    public void deleteSelectedItems(
            @Valid @RequestBody DeleteSelectedRequest request,
            @AuthenticationPrincipal AuthPrincipal principal
    ) {
        cartService.deleteSelectedItems(principal.getUserId(), request.cartIds());
    }
}
