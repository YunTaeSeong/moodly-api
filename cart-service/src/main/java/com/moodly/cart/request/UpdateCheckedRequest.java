package com.moodly.cart.request;

import jakarta.validation.constraints.NotNull;

public record UpdateCheckedRequest(
        @NotNull(message = "체크 상태는 필수입니다.")
        Boolean checked
) {
}
