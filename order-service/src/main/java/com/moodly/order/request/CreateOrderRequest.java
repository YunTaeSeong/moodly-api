package com.moodly.order.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderRequest {

    @NotEmpty(message = "주문할 상품을 선택해주세요.")
    private List<Long> cartIds;

    @NotNull(message = "수령인 이름을 입력해주세요.")
    private String customerName;

    @NotNull(message = "연락처를 입력해주세요.")
    private String customerPhoneNumber;

    // 배송지 (JSON 문자열)
    private String deliveryAddress;

    private Long couponId;
}
