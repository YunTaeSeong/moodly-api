package com.moodly.auth.client.user.dto;

import jakarta.validation.constraints.NotBlank;

public record KakaoUserCreateRequest(
        @NotBlank(message = "카카오 사용자 ID는 필수입니다")
        String providerId,
        @NotBlank(message = "이름은 필수입니다")
        String name,
        String email
) {
}
