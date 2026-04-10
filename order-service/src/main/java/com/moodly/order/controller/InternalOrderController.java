package com.moodly.order.controller;

import com.moodly.order.response.OrderPaymentCompleteRequest;
import com.moodly.order.response.OrderPaymentSnapshotResponse;
import com.moodly.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal/order")
@RequiredArgsConstructor
public class InternalOrderController {

    private final OrderService orderService;

    @GetMapping("/payment/{orderId}")
    public OrderPaymentSnapshotResponse getOrderForPayment(@PathVariable String orderId) {
        return orderService.getOrderSnapshotForPayment(orderId);
    }

    @PostMapping("/payment/{orderId}/complete")
    public ResponseEntity<Void> completePayment(
            @PathVariable String orderId,
            @Valid @RequestBody OrderPaymentCompleteRequest request
    ) {
        orderService.completeOrderPayment(orderId, request.getPaymentKey());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/payment/{orderId}/cancel")
    public ResponseEntity<Void> cancelPayment(@PathVariable String orderId) {
        orderService.cancelOrderPayment(orderId);
        return ResponseEntity.ok().build();
    }
}
