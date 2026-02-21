package com.moodly.auth.controller;

import com.moodly.auth.client.user.UserServiceClient;
import com.moodly.auth.client.user.dto.CredentialRequest;
import com.moodly.auth.client.user.dto.CredentialVerifyResponse;
import com.moodly.auth.client.user.dto.KakaoUserCreateRequest;
import com.moodly.auth.client.user.dto.KakaoUserResponse;
import com.moodly.auth.dto.kakao.KakaoTokenResponse;
import com.moodly.auth.dto.kakao.KakaoUserInfoResponse;
import com.moodly.auth.repository.RefreshTokenRepository;
import com.moodly.auth.request.KakaoLoginRequest;
import com.moodly.auth.request.LoginRequest;
import com.moodly.auth.request.RefreshRequest;
import com.moodly.auth.response.TokenPairResponse;
import com.moodly.auth.service.AuthService;
import com.moodly.auth.service.KakaoAuthService;
import com.moodly.auth.token.JwtTokenIssuer;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthApiController {
    private final UserServiceClient userServiceClient;
    private final JwtTokenIssuer tokenIssuer;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenIssuer jwtTokenIssuer;
    private final AuthService authService;
    private final KakaoAuthService kakaoAuthService;

    private static final String REFRESH_HEADER_NAME = "X-Refresh-Token";

    @PostMapping("/login")
    public TokenPairResponse login(@RequestBody LoginRequest req) {
        CredentialVerifyResponse verify = userServiceClient.verify(new CredentialRequest(req.email(), req.password()));
        return tokenIssuer.issue(verify.getUserId(), verify.getRoles());
    }

    @PostMapping("/kakao/login")
    public TokenPairResponse kakaoLogin(@Valid @RequestBody KakaoLoginRequest request) {
        log.info("[AuthApiController] 카카오 로그인 요청: code={}", request.code());

        // 1. 카카오 인가 코드로 액세스 토큰 발급
        KakaoTokenResponse tokenResponse = kakaoAuthService.getAccessToken(request.code());
        log.info("[AuthApiController] 카카오 액세스 토큰 발급 완료");

        // 2. 액세스 토큰으로 사용자 정보 조회
        KakaoUserInfoResponse userInfo = kakaoAuthService.getUserInfo(tokenResponse.getAccessToken());
        log.info("[AuthApiController] 카카오 사용자 정보 조회 완료: id={}", userInfo.getId());

        // 3. 카카오 사용자 ID로 우리 시스템 사용자 조회 또는 생성
        String providerId = String.valueOf(userInfo.getId());
        String name = userInfo.getKakaoAccount() != null && userInfo.getKakaoAccount().getProfile() != null
                ? userInfo.getKakaoAccount().getProfile().getNickname()
                : "카카오사용자";
        String email = userInfo.getKakaoAccount() != null ? userInfo.getKakaoAccount().getEmail() : null;

        KakaoUserResponse kakaoUser = userServiceClient.findOrCreateKakaoUser(
                new KakaoUserCreateRequest(providerId, name, email)
        );
        log.info("[AuthApiController] 카카오 사용자 조회/생성 완료: userId={}", kakaoUser.userId());

        // 4. JWT 토큰 발급
        return tokenIssuer.issue(kakaoUser.userId(), kakaoUser.roles());
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
