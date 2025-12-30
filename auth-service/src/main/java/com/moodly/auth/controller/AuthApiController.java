package com.moodly.auth.controller;

import com.moodly.auth.dto.LoginRequest;
import com.moodly.auth.dto.RefreshRequest;
import com.moodly.auth.dto.TokenPairResponse;
import com.moodly.auth.token.JwtTokenIssuer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthApiController {
    private final JwtTokenIssuer tokenIssuer;

    @PostMapping("/login")
    public TokenPairResponse login(@RequestBody LoginRequest req) {
        // user 검증 없이 userId=1, role=USER로 발급 : 수정예정
        return tokenIssuer.issue(1L, List.of("USER"));
    }

    @PostMapping("/refresh")
    public TokenPairResponse refresh(@RequestBody RefreshRequest req) {
        return tokenIssuer.refresh(req.refreshToken());
    }
}
