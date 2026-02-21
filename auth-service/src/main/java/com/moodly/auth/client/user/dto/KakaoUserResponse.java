package com.moodly.auth.client.user.dto;

import java.util.List;

public record KakaoUserResponse(
        Long userId,
        List<String> roles
) {
}
