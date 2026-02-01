package com.moodly.order.controller;

import com.moodly.common.security.principal.AuthPrincipal;
import com.moodly.order.dto.OrderDto;
import com.moodly.order.request.CreateOrderRequest;
import com.moodly.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderService orderService;

    @PostMapping
    public OrderDto createOrder(
            @Valid @RequestBody CreateOrderRequest request,
            @AuthenticationPrincipal AuthPrincipal principal
    ) {
        Long userId = principal.getUserId();
        return orderService.createOrder(
                userId,
                request.getCartIds(),
                request.getCustomerName(),
                request.getCustomerPhoneNumber(),
                request.getDeliveryAddress(),
                request.getCouponId()
        );
    }
}
