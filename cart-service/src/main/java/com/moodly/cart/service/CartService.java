package com.moodly.cart.service;

import com.moodly.cart.client.ProductClient;
import com.moodly.cart.client.ProductResponse;
import com.moodly.cart.domain.Cart;
import com.moodly.cart.dto.CartDto;
import com.moodly.cart.dto.CartItemDto;
import com.moodly.cart.repository.CartRepository;
import com.moodly.common.exception.BaseException;
import com.moodly.common.exception.GlobalErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductClient productClient;

    @Transactional
    public CartDto addToCart(Long userId, Long productId, Integer quantity) {
        // 기존 장바구니에 같은 상품이 있는지
        Cart existingCart = cartRepository.findByUserIdAndProductId(userId, productId)
                .orElse(null);

        if (existingCart != null) {
            // 기존 상품이 있으면 수량 증가
            Integer currentQuantity = existingCart.getQuantity() != null ? existingCart.getQuantity() : 1;
            existingCart.setQuantity(currentQuantity + quantity);
        } else {
            // 새로운 상품 추가
            existingCart = Cart.builder()
                    .userId(userId)
                    .productId(productId)
                    .quantity(quantity != null && quantity > 0 ? quantity : 1)
                    .checked(true)
                    .build();
        }

        Cart saved = cartRepository.save(existingCart);
        return CartDto.fromEntity(saved);
    }

    @Transactional(readOnly = true)
    public List<CartItemDto> getCartItemsWithProductInfo(Long userId) {
        List<Cart> carts = cartRepository.findByUserId(userId);
        return carts.stream()
                .map(cart -> {
                    CartDto cartDto = CartDto.fromEntity(cart);
                    try {
                        ProductResponse product = productClient.getProductById(cart.getProductId());
                        return CartItemDto.fromCartDtoAndProduct(cartDto, product);
                    } catch (Exception e) {
                        log.error("상품 정보 에러 : productId={}, error={}", cart.getProductId(), e.getMessage());
                        return CartItemDto.fromCartDtoAndProduct(cartDto, null);
                    }
                })
                .toList();
    }

    @Transactional
    public void updateQuantity(Long userId, Long cartId, Integer quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new BaseException(GlobalErrorCode.CART_ITEM_NOT_FOUND));

        getNotEqualUserId(userId, cart);

        if (quantity == null || quantity < 1) {
            throw new BaseException(GlobalErrorCode.INVALID_QUANTITY);
        }

        cart.setQuantity(quantity);
        cartRepository.save(cart);
    }

    @Transactional
    public void deleteCartItem(Long userId, Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new BaseException(GlobalErrorCode.CART_ITEM_NOT_FOUND));

        getNotEqualUserId(userId, cart);

        cartRepository.deleteById(cartId);
    }

    @Transactional
    public void updateChecked(Long userId, Long cartId, boolean checked) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new BaseException(GlobalErrorCode.CART_ITEM_NOT_FOUND));

        getNotEqualUserId(userId, cart);

        cart.setChecked(checked);
        cartRepository.save(cart);
    }

    @Transactional
    public void updateAllChecked(Long userId, boolean checked) {
        List<Cart> carts = cartRepository.findByUserId(userId);
        carts.forEach(cart -> cart.setChecked(checked));
        cartRepository.saveAll(carts);
    }

    @Transactional
    public void deleteSelectedItems(Long userId, List<Long> cartIds) {
        List<Cart> carts = cartRepository.findAllById(cartIds);

        carts.forEach(cart -> {
            getNotEqualUserId(userId, cart);
        });

        cartRepository.deleteAll(carts);
    }

    @Transactional(readOnly = true)
    public CartDto getCartById(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new BaseException(GlobalErrorCode.CART_ITEM_NOT_FOUND));
        return CartDto.fromEntity(cart);
    }

    /**
     * 주문 생성용 메소드 : cartIds에 해당하는 장바구니 항목을 상품 정보와 함께 조회
     */
    @Transactional(readOnly = true)
    public List<CartItemDto> getCartItemsWithProductInfo(List<Long> cartIds) {
        if (cartIds == null || cartIds.isEmpty()) {
            return List.of();
        }
        List<Cart> carts = cartRepository.findAllById(cartIds);
        return carts.stream()
                .map(cart -> {
                    CartDto cartDto = CartDto.fromEntity(cart);
                    try {
                        ProductResponse product = productClient.getProductById(cart.getProductId());
                        return CartItemDto.fromCartDtoAndProduct(cartDto, product);
                    } catch (Exception e) {
                        log.warn("상품 정보 조회 실패: productId = {}", cart.getProductId(), e);
                        return CartItemDto.fromCartDtoAndProduct(cartDto, null);
                    }
                })
                .toList();
    }

    private static void getNotEqualUserId(Long userId, Cart cart) {
        if (!cart.getUserId().equals(userId)) {
            throw new BaseException(GlobalErrorCode.MISSING_AUTHORIZATION);
        }
    }
}
