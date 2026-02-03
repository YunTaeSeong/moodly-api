package com.moodly.order.service;

import com.moodly.common.exception.BaseException;
import com.moodly.common.exception.GlobalErrorCode;
import com.moodly.order.client.CartClient;
import com.moodly.order.client.CartItemResponse;
import com.moodly.order.domain.Order;
import com.moodly.order.domain.OrderItem;
import com.moodly.order.dto.OrderDto;
import com.moodly.order.repository.OrderItemRepository;
import com.moodly.order.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private CartClient cartClient;

    @Test
    @DisplayName("주문 생성 성공: 장바구니 항목으로 주문이 정상적으로 생성되고 장바구니가 비워진다")
    void createOrder_Success() {
        // given
        Long userId = 1L;
        List<Long> cartIds = List.of(10L, 11L);
        String customerName = "테스트";
        String customerPhoneNumber = "010-1111-2222";
        String deliveryAddress = "서울시 강남구";
        Long couponId = null;

        // 상품1: 10,000원, 10% 할인, 2개 / 상품2: 5,000원, 0% 할인, 1개
        CartItemResponse item1 = createCartItem(10L, userId, 101L, "상품1", new BigDecimal("10000"), 10, 2);
        CartItemResponse item2 = createCartItem(11L, userId, 102L, "상품2", new BigDecimal("5000"), 0, 1);

        when(cartClient.getCartItemsWithProductInfo(cartIds)).thenReturn(List.of(item1, item2));

        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(orderItemRepository.saveAll(anyList())).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        OrderDto result = orderService.createOrder(userId, cartIds, customerName, customerPhoneNumber, deliveryAddress, couponId);

        // then
        // 상품1: 10,000원 * 0.9 = 9,000원 -> 2개 = 18,000원
        // 상품2: 5,000원 * 1.0 = 5,000원 -> 1개 = 5,000원
        // totalAmount = 23,000원 (15,000원 이상이므로 배송비 0원 예상)
        assertEquals(new BigDecimal("23000.00"), result.getTotalAmount());
        assertEquals(BigDecimal.ZERO, result.getShippingFee());
        assertEquals(new BigDecimal("23000.00"), result.getFinalAmount());

        assertEquals(customerName, result.getCustomerName());
        assertEquals(2, result.getItems().size());

        verify(cartClient, times(1)).getCartItemsWithProductInfo(cartIds);
        verify(cartClient, times(1)).deleteSelectedItems(userId, cartIds);
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(orderItemRepository, times(1)).saveAll(anyList());
    }

    // 테스트 데이터 생성
    private CartItemResponse createCartItem(Long id, Long userId, Long productId, String name, BigDecimal price, int discount, int quantity) {
        return CartItemResponse.builder()
                .id(id)
                .userId(userId)
                .productId(productId)
                .productName(name)
                .productPrice(price)
                .productDiscount(discount)
                .quantity(quantity)
                .build();
    }

    @Test
    @DisplayName("주문 생성 : 실패 (존재하지 않을 때)")
    void 주문_생성_실패_존재하지_않을_때() {
        // given
        Long userId = 1L;
        List<Long> cartIds = List.of(10L, 11L);

        when(cartClient.getCartItemsWithProductInfo(cartIds)).thenReturn(Collections.emptyList());

        // when
        BaseException baseException = assertThrows(
                BaseException.class,
                () -> orderService.createOrder(
                        userId, cartIds, "테스트", "010-1111-2222", "서울시 강남구", null)
        );

        // then
        assertEquals(GlobalErrorCode.CART_NOT_FOUND, baseException.getErrorCode());
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    @DisplayName("주문 생성 : 실패 (사용자 일치하지 않을 때)")
    void 주문_생성_실패_사용자_일치하지_않을_때() {
        // given
        Long userId = 1L;
        Long anotherUserId = 2L;
        List<Long> cartIds = List.of(10L, 11L);

        CartItemResponse cartItem = CartItemResponse.builder()
                .userId(anotherUserId)
                .build();

        when(cartClient.getCartItemsWithProductInfo(cartIds)).thenReturn(List.of(cartItem));

        // when
        BaseException baseException = assertThrows(
                BaseException.class,
                () -> orderService.createOrder(
                        userId, cartIds, "테스트", "010-1111-2222", "서울시 강남구", null)
        );

        // then
        assertEquals(GlobalErrorCode.MISSING_AUTHORIZATION, baseException.getErrorCode());
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    @DisplayName("주문 생성 : 실패 (장바구니 삭제 중 예외가 발생해도 주문은 저장)")
    void 주문_생성_실패_예외_발생해도_주문은_저장() {
        // given
        Long userId = 1L;
        List<Long> cartIds = List.of(10L, 11L);
        CartItemResponse cartItem = CartItemResponse.builder()
                .userId(userId)
                .productPrice(new BigDecimal("10000"))
                .quantity(1)
                .build();

        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(cartClient.getCartItemsWithProductInfo(cartIds)).thenReturn(List.of(cartItem));

        doThrow(new RuntimeException("error")).when(cartClient).deleteSelectedItems(anyLong(), anyList());

        // when
        // 삭제에서 에러지만, try-catch 때문에 정상 종료
        assertDoesNotThrow(() -> {
                    orderService.createOrder(userId, cartIds, "테스트", "010-1111-2222", "서울시 강남구", null);
        });

        // then
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(cartClient, times(1)).deleteSelectedItems(userId, cartIds);
    }

    @Test
    @DisplayName("단건 조회 : 성공")
    void 단건_조회_성공() {
        // given
        Order order = mock(Order.class);
        Long userId = 1L;
        Long orderId = 10L;

        when(order.getUserId()).thenReturn(userId);
        when(order.getId()).thenReturn(orderId);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderItemRepository.findByOrderId(orderId)).thenReturn(List.of());

        // when
        OrderDto result = orderService.getOrder(userId, orderId);

        // then
        assertNotNull(result);

        verify(orderRepository, times(1)).findById(orderId);
        verify(orderItemRepository, times(1)).findByOrderId(orderId);
    }

    @Test
    @DisplayName("단건 조회 : 실패 (주문 내역 없음)")
    void 단건_조회_실패_주문_내역_없음() {
        //given
        Long userId = 1L;
        Long orderId = 10L;

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        // when
        BaseException baseException = assertThrows(
                BaseException.class,
                () -> orderService.getOrder(userId, orderId)
        );

        // then
        assertEquals(GlobalErrorCode.ORDER_NOT_FOUND, baseException.getErrorCode());

        verify(orderRepository, times(1)).findById(orderId);
        verify(orderItemRepository, never()).findByOrderId(orderId);
    }

    @Test
    @DisplayName("전체 조회 : 성공")
    void 전체_조회_성공() {
        // given
        Order order = mock(Order.class);
        Long userId = 1L;

        when(orderRepository.findAllByUserIdOrderByCreatedDateDesc(userId)).thenReturn(List.of(mock(Order.class)));
        when(orderItemRepository.findByOrderId(order.getId())).thenReturn(List.of(mock(OrderItem.class)));

        // when
        List<OrderDto> result = orderService.getOrderAll(userId);

        // then
        assertNotNull(result);

        verify(orderRepository, times(1)).findAllByUserIdOrderByCreatedDateDesc(userId);
        verify(orderItemRepository, times(1)).findByOrderId(order.getId());
    }

    @Test
    @DisplayName("전체 조회 : 실패(주문 내역 없음)")
    void 전체_조회_실패_주문_내역_없음() {
        // given
        Long userId = 1L;
        when(orderRepository.findAllByUserIdOrderByCreatedDateDesc(userId))
                .thenReturn(Collections.emptyList());

        // when
        BaseException ex = assertThrows(
                BaseException.class,
                () -> orderService.getOrderAll(userId)
        );

        // then
        assertEquals(GlobalErrorCode.ORDER_NOT_FOUND, ex.getErrorCode());

        verify(orderRepository, times(1)).findAllByUserIdOrderByCreatedDateDesc(userId); // ✅ 호출됨
        verify(orderItemRepository, never()).findByOrderId(anyLong());
    }

    @Test
    @DisplayName("주문 단건 삭제 : 성공")
    void 주문_단건_삭제() {
        // given
        Order order = mock(Order.class);
        List<OrderItem> items = List.of(mock(OrderItem.class));
        Long userId = 1L;
        Long orderId = 10L;

        when(order.getUserId()).thenReturn(userId);
        when(order.getId()).thenReturn(orderId);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderItemRepository.findByOrderId(orderId)).thenReturn(items);

        // when
        assertDoesNotThrow(
                () -> orderService.getOrderSelectedDelete(userId, orderId)
        );

        // then
        verify(orderItemRepository, times(1)).deleteAll(items);
        verify(orderRepository, times(1)).delete(order);
    }

    @Test
    @DisplayName("주문 전체 삭제 : 성공")
    void 주문_전체_삭제() {
        // given
        Long userId = 1L;

        Order order1 = mock(Order.class);
        when(order1.getId()).thenReturn(10L);

        Order order2 = mock(Order.class);
        when(order2.getId()).thenReturn(20L);

        List<Order> orders = List.of(order1, order2);

        List<OrderItem> items1 = List.of(mock(OrderItem.class));
        List<OrderItem> items2 = List.of(mock(OrderItem.class), mock(OrderItem.class));

        when(orderRepository.findAllByUserIdOrderByCreatedDateDesc(userId)).thenReturn(orders);
        when(orderItemRepository.findByOrderId(order1.getId())).thenReturn(items1);
        when(orderItemRepository.findByOrderId(order2.getId())).thenReturn(items2);

        // when
        assertDoesNotThrow(() -> orderService.deleteAllOrders(userId));

        // then
        verify(orderRepository, times(1)).findAllByUserIdOrderByCreatedDateDesc(userId);

        verify(orderItemRepository, times(1)).findByOrderId(10L);
        verify(orderItemRepository, times(1)).deleteAll(items1);

        verify(orderItemRepository, times(1)).findByOrderId(20L);
        verify(orderItemRepository, times(1)).deleteAll(items2);

        verify(orderRepository, times(1)).deleteAll(orders);

    }

}