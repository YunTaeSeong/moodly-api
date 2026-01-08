package com.moodly.auth.controller;

import com.moodly.auth.client.user.UserServiceClient;
import com.moodly.auth.client.user.dto.CredentialRequest;
import com.moodly.auth.client.user.dto.CredentialVerifyResponse;
import com.moodly.auth.repository.RefreshTokenRepository;
import com.moodly.auth.request.LoginRequest;
import com.moodly.auth.request.RefreshRequest;
import com.moodly.auth.response.TokenPairResponse;
import com.moodly.auth.service.AuthService;
import com.moodly.auth.token.JwtTokenIssuer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthApiController {
    private final UserServiceClient userServiceClient;
    private final JwtTokenIssuer tokenIssuer;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenIssuer jwtTokenIssuer;
    private final AuthService authService;

    private static final String REFRESH_HEADER_NAME = "X-Refresh-Token";

    @PostMapping("/login")
    public TokenPairResponse login(@RequestBody LoginRequest req) {
        CredentialVerifyResponse verify = userServiceClient.verify(new CredentialRequest(req.email(), req.password()));
        return tokenIssuer.issue(verify.getUserId(), verify.getRoles());
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @RequestHeader(value = REFRESH_HEADER_NAME, required = false) String refreshToken
    ) {
        // 토큰 없으면 멱등 로그아웃: 그냥 204
        if (refreshToken == null || refreshToken.isBlank()) {
            return ResponseEntity.noContent().build();
        }

        String hash = jwtTokenIssuer.sha256Hex(refreshToken.trim());
        int deleted = refreshTokenRepository.deleteByRefreshTokenHash(hash);
        System.out.println("deleted = " + deleted); // 삭제될 때 : 1, 삭제 후 : 0

        return ResponseEntity.noContent().build(); // 204로 통일
    }

    @PostMapping("/refresh")
    public TokenPairResponse refresh(@RequestBody RefreshRequest req) {
        return tokenIssuer.refresh(req.refreshToken());
    }

    @DeleteMapping("/refresh-token/{userId}")
    public ResponseEntity<Void> revokeAllByUserId(
            @PathVariable Long userId
    ) {
        authService.revokeAllByUserId(userId);
        return ResponseEntity.noContent().build();
    }

}
