package com.moodly.user.controller;

import com.moodly.user.dto.UserDto;
import com.moodly.user.request.KakaoUserCreateRequest;
import com.moodly.user.response.KakaoUserResponse;
import com.moodly.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal/kakao")
@RequiredArgsConstructor
public class InternalKakaoUserApiController {

    private final UserService userService;

    /**
     * 카카오 사용자 조회 또는 생성 (내부 API)
     */
    @PostMapping("/user")
    public ResponseEntity<KakaoUserResponse> findOrCreateKakaoUser(
            @Valid @RequestBody KakaoUserCreateRequest request
    ) {
        UserDto userDto = userService.findOrCreateKakaoUser(
                request.providerId(),
                request.name(),
                request.email()
        );
        return ResponseEntity.ok(KakaoUserResponse.from(userDto));
    }
}
