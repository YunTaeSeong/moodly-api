package com.moodly.auth.request;

import jakarta.validation.constraints.NotBlank;

public record KakaoLoginRequest(
        @NotBlank(message = "카카오 인가 코드는 필수입니다")
        String code
) {
}
