package com.moodly.user.controller;

import com.moodly.user.dto.UserDto;
import com.moodly.user.request.UserRegisterRequest;
import com.moodly.user.response.UserResponse;
import com.moodly.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Validated
public class UserApiController {

    private final UserService userService;

    @PostMapping("/register")
    public UserResponse register(
            @Valid
            @RequestBody UserRegisterRequest request
    ) {
        UserDto dto = userService.register(request.getEmail(), request.getPassword(), request.getRePassword(), request.getName(), request.getPhoneNumber());
        return UserResponse.response(dto);
    }

    /**
     * 회원가입 전 이메일 사용 여부
     */
    @GetMapping("/email-available")
    public Map<String, Boolean> emailAvailable(@RequestParam("email") String email) {
        boolean exists = userService.isEmailRegistered(email);
        return Map.of("exists", exists, "available", !exists);
    }
}
