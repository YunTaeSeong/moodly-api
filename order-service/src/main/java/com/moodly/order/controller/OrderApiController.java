package com.moodly.order.controller;

import com.moodly.common.security.principal.AuthPrincipal;
import com.moodly.order.dto.OrderDto;
import com.moodly.order.request.CreateOrderRequest;
import com.moodly.order.response.OrderResponse;
import com.moodly.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderService orderService;

    @PostMapping
    public OrderResponse createOrder(
            @Valid @RequestBody CreateOrderRequest request,
            @AuthenticationPrincipal AuthPrincipal principal
    ) {
        Long userId = principal.getUserId();
        OrderDto dto = orderService.createOrder(
                userId,
                request.getCartIds(),
                request.getCustomerName(),
                request.getCustomerPhoneNumber(),
                request.getDeliveryAddress(),
                request.getCouponId()
        );
        return OrderResponse.from(dto);
    }

    @GetMapping("/{id}")
    public OrderResponse getOrder(
            @PathVariable("id") Long orderId,
            @AuthenticationPrincipal AuthPrincipal principal
    ) {
        Long userId = principal.getUserId();
        OrderDto dto = orderService.getOrder(userId, orderId);
        return OrderResponse.from(dto);
    }

    @GetMapping("/all")
    public List<OrderResponse> getOrderAll(@AuthenticationPrincipal AuthPrincipal principal) {
        Long userId = principal.getUserId();
        List<OrderDto> dtos = orderService.getOrderAll(userId);
        return OrderResponse.fromList(dtos);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(
            @PathVariable("id") Long orderId,
            @AuthenticationPrincipal AuthPrincipal principal
    ) {
        Long userId = principal.getUserId();
        orderService.getOrderSelectedDelete(userId, orderId);
    }

    @DeleteMapping("/all")
    public void deleteAllOrders(@AuthenticationPrincipal AuthPrincipal principal) {
        Long userId = principal.getUserId();
        orderService.deleteAllOrders(userId);
    }

}
