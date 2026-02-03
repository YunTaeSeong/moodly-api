package com.moodly.order.service;

import com.moodly.common.exception.BaseException;
import com.moodly.common.exception.GlobalErrorCode;
import com.moodly.order.client.CartClient;
import com.moodly.order.client.CartItemResponse;
import com.moodly.order.domain.Order;
import com.moodly.order.domain.OrderItem;
import com.moodly.order.dto.OrderDto;
import com.moodly.order.dto.OrderItemDto;
import com.moodly.order.enums.OrderStatus;
import com.moodly.order.repository.OrderItemRepository;
import com.moodly.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private static final BigDecimal SHIPPING_FEE = new BigDecimal("3000");
    private static final String ORDER_ID_PREFIX = "ORDER-";

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartClient cartClient;

    /**
     * 주문 생성: 장바구니 선택 항목으로 주문을 생성하고, 해당 장바구니 항목을 삭제
     */
    @Transactional
    public OrderDto createOrder(
            Long userId,
            List<Long> cartIds,
            String customerName,
            String customerPhoneNumber,
            String deliveryAddress,
            Long couponId
    ) {
        // 1. 장바구니 항목 조회 (상품 정보 포함)
        List<CartItemResponse> cartItems = cartClient.getCartItemsWithProductInfo(cartIds);
        if (cartItems == null || cartItems.isEmpty()) {
            throw new BaseException(GlobalErrorCode.CART_NOT_FOUND);
        }

        // 2. 장바구니를 담은 해당 사용자 인지 확인
        boolean allMatchUserId = cartItems.stream()
                .allMatch(item -> userId.equals(item.getUserId()));
        if (!allMatchUserId) {
            throw new BaseException(GlobalErrorCode.MISSING_AUTHORIZATION);
        }

        // 3. 금액 계산
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (CartItemResponse item : cartItems) {
            BigDecimal price = applyDiscount(
                    item.getProductPrice() != null ? item.getProductPrice() : BigDecimal.ZERO,
                    item.getProductDiscount() != null ? item.getProductDiscount() : 0
            );
            BigDecimal subTotal = price.multiply(BigDecimal.valueOf(item.getQuantity() != null ? item.getQuantity() : 1));
            totalAmount = totalAmount.add(subTotal);
        }

        // 할인율
        BigDecimal discountAmount = couponId != null ? BigDecimal.ZERO : BigDecimal.ZERO; // 쿠폰 적용 시 계산
        // 배송비
        BigDecimal shippingFee = totalAmount.compareTo(new BigDecimal("15000")) >= 0 ? BigDecimal.ZERO : SHIPPING_FEE;
        // 총 결제 금액
        BigDecimal finalAmount = totalAmount.subtract(discountAmount).add(shippingFee);

        // 4. 주문 ID 생성
        String orderId = ORDER_ID_PREFIX + System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(0, 8);

        // 5. Order 저장
        Order savedOrder = orderRepository.save(Order.of(
                orderId, userId, totalAmount, discountAmount, shippingFee, finalAmount, couponId,
                deliveryAddress, customerName, customerPhoneNumber, OrderStatus.PAYMENT_COMPLETED
        ));

        // 6. OrderItem 생성 및 저장
        List<OrderItem> orderItems = cartItems.stream()
                .map(cartItem -> {
                    BigDecimal price = applyDiscount(
                            cartItem.getProductPrice() != null ? cartItem.getProductPrice() : BigDecimal.ZERO,
                            cartItem.getProductDiscount() != null ? cartItem.getProductDiscount() : 0
                    );
                    int quantities = cartItem.getQuantity() != null ? cartItem.getQuantity() : 1;
                    BigDecimal subTotal = price.multiply(BigDecimal.valueOf(quantities));

                    return OrderItem.builder()
                            .order(savedOrder)
                            .productId(cartItem.getProductId())
                            .productName(cartItem.getProductName() != null ? cartItem.getProductName() : "")
                            .productImage(cartItem.getProductImage())
                            .price(price)
                            .quantity(quantities)
                            .subTotal(subTotal)
                            .build();
                })
                .collect(Collectors.toList());

        orderItemRepository.saveAll(orderItems);

        // 7. 주문 완료 후 해당 장바구니 항목 삭제
        try {
            cartClient.deleteSelectedItems(userId, cartIds);
        } catch (Exception e) {
            log.warn("주문 후 장바구니 삭제 실패 (주문은 완료): userId = {}, cartIds = {}", userId, cartIds, e);
        }

        List<OrderItemDto> itemDto = orderItems.stream()
                .map(OrderItemDto::fromEntity)
                .collect(Collectors.toList());

        return OrderDto.fromEntity(savedOrder, itemDto);
    }

    private static BigDecimal applyDiscount(BigDecimal price, int discountPercent) {
        if (price == null) throw new IllegalArgumentException("가격은 null 일 수 없습니다.");
        if (discountPercent <= 0) return price;
        if (discountPercent > 100) throw new IllegalArgumentException("할인율은 100을 넘을 수 없습니다.");

        BigDecimal rate = BigDecimal.valueOf(100 - discountPercent).divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);
        return price.multiply(rate).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * 주문 단건 조회 (주문 내역 상세)
     */
    @Transactional(readOnly = true)
    public OrderDto getOrder(Long userId, Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BaseException(GlobalErrorCode.ORDER_NOT_FOUND));

        if (!order.getUserId().equals(userId)) {
            throw new BaseException(GlobalErrorCode.MISSING_AUTHORIZATION);
        }

        List<OrderItem> orderItems = orderItemRepository.findByOrderId(order.getId());
        List<OrderItemDto> itemDto = orderItems.stream()
                .map(OrderItemDto::fromEntity)
                .toList();

        return OrderDto.fromEntity(order, itemDto);
    }

    /**
     * 주문 전체 조회 (최신순)
     */
    @Transactional(readOnly = true)
    public List<OrderDto> getOrderAll(Long userId) {
        List<Order> orders = orderRepository.findAllByUserIdOrderByCreatedDateDesc(userId);

        if(orders == null || orders.isEmpty()) {
            throw new BaseException(GlobalErrorCode.ORDER_NOT_FOUND);
        }

        return orders.stream()
                .map(order -> {
                    List<OrderItem> orderItems = orderItemRepository.findByOrderId(order.getId());
                    List<OrderItemDto> itemDto = orderItems.stream()
                            .map(OrderItemDto::fromEntity)
                            .toList();
                    return OrderDto.fromEntity(order, itemDto);
                })
                .toList();
    }

    /**
     * 주문 단건 삭제 -> OrderItem 먼저 삭제 후 Order 삭제
     */
    @Transactional
    public void getOrderSelectedDelete(Long userId, Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BaseException(GlobalErrorCode.ORDER_NOT_FOUND));

        if (!order.getUserId().equals(userId)) {
            throw new BaseException(GlobalErrorCode.MISSING_AUTHORIZATION);
        }

        List<OrderItem> orderItems = orderItemRepository.findByOrderId(order.getId());
        orderItemRepository.deleteAll(orderItems);

        orderRepository.delete(order);
    }

    /**
     * 주문 전체 삭제
     */
    @Transactional
    public void deleteAllOrders(Long userId) {
        List<Order> orders = orderRepository.findAllByUserIdOrderByCreatedDateDesc(userId);

        orders.stream().forEach(order -> {
            List<OrderItem> orderItems = orderItemRepository.findByOrderId(order.getId());
            orderItemRepository.deleteAll(orderItems);
        });

        orderRepository.deleteAll(orders);
    }

}
