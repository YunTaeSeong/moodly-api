package com.moodly.order.response;

import com.moodly.order.dto.OrderItemDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemResponse {

    private Long id;
    private Long productId;
    private String productName;
    private String productImage;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal subTotal;

    public static OrderItemResponse from(OrderItemDto dto) {
        return OrderItemResponse.builder()
                .id(dto.getId())
                .productId(dto.getProductId())
                .productName(dto.getProductName())
                .productImage(dto.getProductImage())
                .price(dto.getPrice())
                .quantity(dto.getQuantity())
                .subTotal(dto.getSubTotal())
                .build();
    }

    public static List<OrderItemResponse> fromList(List<OrderItemDto> dto) {
        return dto.stream()
                .map(OrderItemResponse::from)
                .toList();
    }
}
