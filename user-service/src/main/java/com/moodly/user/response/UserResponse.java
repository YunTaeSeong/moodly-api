package com.moodly.user.response;

import com.moodly.user.dto.UserDto;
import com.moodly.user.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private Long id;

    private String email;

    private String password;

    private String name;

    private String phoneNumber;

    private UserRole role;

    public static UserResponse response(UserDto dto) {
        return UserResponse.builder()
                .id(dto.getId())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .name(dto.getName())
                .phoneNumber(dto.getPhoneNumber())
                .role(dto.getRole())
                .build();
    }
}
