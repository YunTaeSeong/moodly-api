package com.moodly.cart.request;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record DeleteSelectedRequest(
        @NotEmpty(message = "삭제할 장바구니 ID 목록은 필수입니다.")
        List<Long> cartIds
) {
}
