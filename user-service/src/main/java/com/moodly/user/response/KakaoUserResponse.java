package com.moodly.user.response;

import com.moodly.user.dto.UserDto;
import com.moodly.user.enums.UserRole;

import java.util.List;
import java.util.stream.Collectors;

public record KakaoUserResponse(
        Long userId,
        List<String> roles
) {
    public static KakaoUserResponse from(UserDto userDto) {
        List<String> roles = List.of(userDto.getRole().name());
        return new KakaoUserResponse(userDto.getId(), roles);
    }
}
