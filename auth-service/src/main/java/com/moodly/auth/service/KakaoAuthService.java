package com.moodly.auth.service;

import com.moodly.auth.dto.kakao.KakaoTokenResponse;
import com.moodly.auth.dto.kakao.KakaoUserInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoAuthService {

    private final RestTemplate restTemplate;

    @Value("${kakao.client-id}")
    private String clientId;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    @Value("${kakao.token-uri:https://kauth.kakao.com/oauth/token}")
    private String tokenUri;

    @Value("${kakao.user-info-uri:https://kapi.kakao.com/v2/user/me}")
    private String userInfoUri;

    @Value("${kakao.client-secret:}")
    private String clientSecret;

    /**
     * 카카오 인가 코드로 액세스 토큰 발급*/
    public KakaoTokenResponse getAccessToken(String code) {
        log.info("[KakaoAuthService] 카카오 액세스 토큰 요청: code={}", code);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/x-www-form-urlencoded;charset=utf-8"));

        StringBuilder body = new StringBuilder();
        body.append("grant_type=authorization_code");
        body.append("&client_id=").append(URLEncoder.encode(clientId, StandardCharsets.UTF_8));
        body.append("&redirect_uri=").append(URLEncoder.encode(redirectUri, StandardCharsets.UTF_8));
        body.append("&code=").append(URLEncoder.encode(code, StandardCharsets.UTF_8));
        if (clientSecret != null && !clientSecret.isBlank()) {
            body.append("&client_secret=").append(URLEncoder.encode(clientSecret, StandardCharsets.UTF_8));
        }

        HttpEntity<String> request = new HttpEntity<>(body.toString(), headers);

        try {
            ResponseEntity<KakaoTokenResponse> response = restTemplate.postForEntity(
                    tokenUri,
                    request,
                    KakaoTokenResponse.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                log.info("[KakaoAuthService] 카카오 액세스 토큰 발급 성공");
                return response.getBody();
            } else {
                log.error("[KakaoAuthService] 카카오 액세스 토큰 발급 실패: status={}", response.getStatusCode());
                throw new RuntimeException("카카오 액세스 토큰 발급 실패");
            }
        } catch (Exception e) {
            log.error("[KakaoAuthService] 카카오 액세스 토큰 발급 중 오류 발생", e);
            throw new RuntimeException("카카오 액세스 토큰 발급 중 오류 발생: " + e.getMessage(), e);
        }
    }

    /**
     * 카카오 액세스 토큰으로 사용자 정보 조회
     */
    public KakaoUserInfoResponse getUserInfo(String accessToken) {
        log.info("[KakaoAuthService] 카카오 사용자 정보 조회 요청");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        HttpEntity<String> request = new HttpEntity<>(headers);

        try {
            ResponseEntity<KakaoUserInfoResponse> response = restTemplate.exchange(
                    userInfoUri,
                    HttpMethod.GET,
                    request,
                    KakaoUserInfoResponse.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                KakaoUserInfoResponse userInfo = response.getBody();
                log.info("[KakaoAuthService] 카카오 사용자 정보 조회 성공: id={}, nickname={}", 
                        userInfo.getId(),
                        userInfo.getKakaoAccount() != null && userInfo.getKakaoAccount().getProfile() != null 
                                ? userInfo.getKakaoAccount().getProfile().getNickname() : "N/A");
                return userInfo;
            } else {
                log.error("[KakaoAuthService] 카카오 사용자 정보 조회 실패: status={}", response.getStatusCode());
                throw new RuntimeException("카카오 사용자 정보 조회 실패");
            }
        } catch (Exception e) {
            log.error("[KakaoAuthService] 카카오 사용자 정보 조회 중 오류 발생", e);
            throw new RuntimeException("카카오 사용자 정보 조회 중 오류 발생: " + e.getMessage(), e);
        }
    }
}
