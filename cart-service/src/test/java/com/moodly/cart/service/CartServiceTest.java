package com.moodly.cart.service;

import com.moodly.cart.client.ProductClient;
import com.moodly.cart.client.ProductResponse;
import com.moodly.cart.domain.Cart;
import com.moodly.cart.dto.CartDto;
import com.moodly.cart.dto.CartItemDto;
import com.moodly.cart.repository.CartRepository;
import com.moodly.common.exception.BaseException;
import com.moodly.common.exception.GlobalErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductClient productClient;

    @InjectMocks
    private CartService cartService;

    private Long userId;
    private Long productId;
    private Long cartId;
    private Cart cart;
    private ProductResponse productResponse;

    @BeforeEach
    void setUp() {
        userId = 1L;
        productId = 101L;
        cartId = 1L;

        cart = Cart.builder()
                .id(cartId)
                .userId(userId)
                .productId(productId)
                .quantity(2)
                .checked(true)
                .build();

        productResponse = ProductResponse.builder()
                .id(productId)
                .name("테스트 상품")
                .price(BigDecimal.valueOf(10000))
                .discount(10)
                .image("https://example.com/image.jpg")
                .description("테스트 상품 설명")
                .build();
    }

    @Test
    @DisplayName("새로운 상품을 장바구니에 추가")
    void 새로운_상품_장바구니_추가() {
        // given
        Integer quantity = 3;
        when(cartRepository.findByUserIdAndProductId(userId, productId))
                .thenReturn(Optional.empty());
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        // when
        CartDto result = cartService.addToCart(userId, productId, quantity);

        // then
        assertNotNull(result);
        assertEquals(productId, result.getProductId());
        assertEquals(userId, result.getUserId());
        verify(cartRepository).findByUserIdAndProductId(userId, productId);
        verify(cartRepository).save(any(Cart.class));
    }

    @Test
    @DisplayName("기존 장바구니에 있는 상품의 수량을 증가")
    void 기존_장바구니_상품수량_증가() {
        // given
        Integer quantity = 2;
        Cart existingCart = Cart.builder()
                .id(cartId)
                .userId(userId)
                .productId(productId)
                .quantity(3)
                .checked(true)
                .build();

        when(cartRepository.findByUserIdAndProductId(userId, productId))
                .thenReturn(Optional.of(existingCart));
        when(cartRepository.save(any(Cart.class))).thenReturn(existingCart);

        // when
        CartDto result = cartService.addToCart(userId, productId, quantity);

        // then
        assertNotNull(result);
        assertEquals(5, existingCart.getQuantity()); // 3 + 2
        verify(cartRepository).findByUserIdAndProductId(userId, productId);
        verify(cartRepository).save(existingCart);
    }

    @Test
    @DisplayName("수량이 null 또는 0 이하일 때 기본값 1로 설정")
    void 수량_기본값_설정() {
        // given
        when(cartRepository.findByUserIdAndProductId(userId, productId))
                .thenReturn(Optional.empty());
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        // when
        CartDto result1 = cartService.addToCart(userId, productId, null);
        CartDto result2 = cartService.addToCart(userId, productId, 0);

        // then
        assertNotNull(result1);
        assertNotNull(result2);
        verify(cartRepository, times(2)).save(any(Cart.class));
    }

    @Test
    @DisplayName("장바구니 목록을 상품 정보와 함께 조회")
    void 장바구니_목록_상품정보와_같이_조회() {
        // given
        List<Cart> carts = Arrays.asList(cart);
        when(cartRepository.findByUserId(userId)).thenReturn(carts);
        when(productClient.getProductById(productId)).thenReturn(productResponse);

        // when
        List<CartItemDto> result = cartService.getCartItemsWithProductInfo(userId);

        // then
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("테스트 상품", result.get(0).getProductName());
        assertEquals(BigDecimal.valueOf(10000), result.get(0).getProductPrice());
        verify(cartRepository).findByUserId(userId);
        verify(productClient).getProductById(productId);
    }

    @Test
    @DisplayName("상품 정보 조회 실패 시 Null")
    void 상품정보조회_실패시_Null() {
        // given
        List<Cart> carts = Arrays.asList(cart);
        when(cartRepository.findByUserId(userId)).thenReturn(carts);
        when(productClient.getProductById(productId))
                .thenThrow(new RuntimeException("상품 조회 실패"));

        // when
        List<CartItemDto> result = cartService.getCartItemsWithProductInfo(userId);

        // then
        assertFalse(result.isEmpty());
        assertNull(result.get(0).getProductName());
        assertNull(result.get(0).getProductPrice());
        verify(cartRepository).findByUserId(userId);
        verify(productClient).getProductById(productId);
    }

    @Test
    @DisplayName("장바구니 수량 변경")
    void 장바구니_수량_변경() {
        // given
        Integer newQuantity = 5;
        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        // when
        cartService.updateQuantity(userId, cartId, newQuantity);

        // then
        assertEquals(newQuantity, cart.getQuantity());
        verify(cartRepository).findById(cartId);
        verify(cartRepository).save(cart);
    }

    @Test
    @DisplayName("수량이 null이거나 1 미만일 때 예외")
    void 수량_유효하지_않은_경우_예외() {
        // given
        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));

        // when & then
        BaseException exception1 = assertThrows(BaseException.class, 
                () -> cartService.updateQuantity(userId, cartId, null));
        assertEquals(GlobalErrorCode.INVALID_QUANTITY, exception1.getErrorCode());

        BaseException exception2 = assertThrows(BaseException.class, 
                () -> cartService.updateQuantity(userId, cartId, 0));
        assertEquals(GlobalErrorCode.INVALID_QUANTITY, exception2.getErrorCode());
    }

    @Test
    @DisplayName("장바구니 아이템을 삭제")
    void 장바구니_아이템_삭제() {
        // given
        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        doNothing().when(cartRepository).deleteById(cartId);

        // when
        cartService.deleteCartItem(userId, cartId);

        // then
        verify(cartRepository).findById(cartId);
        verify(cartRepository).deleteById(cartId);
    }

    @Test
    @DisplayName("존재하지 않는 장바구니 아이템 삭제 시 예외가 발생")
    void 장바구니_아이템_존재하지_않을때_삭제시_예외() {
        // given
        when(cartRepository.findById(cartId)).thenReturn(Optional.empty());

        // when & then
        BaseException exception = assertThrows(BaseException.class, 
                () -> cartService.deleteCartItem(userId, cartId));
        assertEquals(GlobalErrorCode.CART_ITEM_NOT_FOUND, exception.getErrorCode());
        verify(cartRepository).findById(cartId);
        verify(cartRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("장바구니 아이템 체크박스 변경")
    void 장바구니_아이템_체크박스_변경() {
        // given
        boolean newChecked = false;
        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        // when
        cartService.updateChecked(userId, cartId, newChecked);

        // then
        assertEquals(newChecked, cart.isChecked());
        verify(cartRepository).findById(cartId);
        verify(cartRepository).save(cart);
    }

    @Test
    @DisplayName("전체 장바구니 아이템 체크박스 변경")
    void 전체_장바구니_아이템_체크박스_변경() {
        // given
        boolean checked = true;
        Cart cart1 = Cart.builder().id(1L).userId(userId).productId(101L).quantity(1).checked(false).build();
        Cart cart2 = Cart.builder().id(2L).userId(userId).productId(102L).quantity(2).checked(false).build();
        List<Cart> carts = Arrays.asList(cart1, cart2);

        when(cartRepository.findByUserId(userId)).thenReturn(carts);
        when(cartRepository.saveAll(anyList())).thenReturn(carts);

        // when
        cartService.updateAllChecked(userId, checked);

        // then
        assertTrue(cart1.isChecked());
        assertTrue(cart2.isChecked());
        verify(cartRepository).findByUserId(userId);
        verify(cartRepository).saveAll(carts);
    }

    @Test
    @DisplayName("모든 장바구니 아이템 삭제")
    void 모든_장바구니_아이템_삭제() {
        // given
        List<Long> cartIds = Arrays.asList(1L, 2L);
        Cart cart1 = Cart.builder().id(1L).userId(userId).productId(101L).quantity(1).checked(true).build();
        Cart cart2 = Cart.builder().id(2L).userId(userId).productId(102L).quantity(2).checked(true).build();
        List<Cart> carts = Arrays.asList(cart1, cart2);

        when(cartRepository.findAllById(cartIds)).thenReturn(carts);
        doNothing().when(cartRepository).deleteAll(carts);

        // when
        cartService.deleteSelectedItems(userId, cartIds);

        // then
        verify(cartRepository).findAllById(cartIds);
        verify(cartRepository).deleteAll(carts);
    }

    @Test
    @DisplayName("빈 장바구니 빈 리스트 반환")
    void 빈_장바구니_빈_리스트_반환() {
        // given
        when(cartRepository.findByUserId(userId)).thenReturn(List.of());

        // when
        List<CartItemDto> result = cartService.getCartItemsWithProductInfo(userId);

        // then
        assertTrue(result.isEmpty());
        verify(cartRepository).findByUserId(userId);
        verify(productClient, never()).getProductById(anyLong());
    }
}
