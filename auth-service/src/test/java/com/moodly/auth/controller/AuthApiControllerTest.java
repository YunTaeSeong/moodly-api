package com.moodly.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moodly.auth.client.user.UserServiceClient;
import com.moodly.auth.client.user.dto.CredentialRequest;
import com.moodly.auth.client.user.dto.CredentialVerifyResponse;
import com.moodly.auth.request.LoginRequest;
import com.moodly.auth.request.RefreshRequest;
import com.moodly.auth.response.TokenPairResponse;
import com.moodly.auth.token.JwtTokenIssuer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class AuthApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserServiceClient userServiceClient;

    @MockitoBean
    private JwtTokenIssuer jwtTokenIssuer;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("로그인 성공 : user-service 검증 후 토큰 발행")
    void 로그인_성공() throws Exception {
        // given
        when(userServiceClient.verify(any(CredentialRequest.class)))
                .thenReturn(new CredentialVerifyResponse(1L, List.of("USER")));

        when(jwtTokenIssuer.issue(1L, List.of("USER")))
                .thenReturn(new TokenPairResponse("accessToken", "refreshToken"));

        LoginRequest req = new LoginRequest("test@test.com", "test1234!@#$");

        // when & then
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.accessToken").value("accessToken"))
                .andExpect(jsonPath("$.refreshToken").value("refreshToken"));
    }

    @Test
    @DisplayName("로그인 실패 : 이메일 비밀번호 불일치")
    void 로그인_실패() throws Exception {
        // given
        when(userServiceClient.verify(any(CredentialRequest.class)))
                .thenThrow(new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Invalid credentials"
                ));

        LoginRequest req = new LoginRequest("test@test.com", "wrong-password");

        // when & then
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("리프레시 성공 : 토큰 재발급")
    void refresh_성공() throws Exception {
        // given
        when(jwtTokenIssuer.refresh("valid-refresh-token"))
                .thenReturn(new TokenPairResponse("newAccessToken", "newRefreshToken"));

        RefreshRequest req = new RefreshRequest("valid-refresh-token");

        // when & then
        mockMvc.perform(post("/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("newAccessToken"))
                .andExpect(jsonPath("$.refreshToken").value("newRefreshToken"));
    }

    @Test
    @DisplayName("리프레시 실패 : 401 반환")
    void refresh_실패() throws Exception {
        // given
        when(jwtTokenIssuer.refresh("invalid-refresh-token"))
                .thenThrow(new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Invalid refresh token"
                ));

        RefreshRequest req = new RefreshRequest("invalid-refresh-token");

        // when & then
        mockMvc.perform(post("/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isUnauthorized());
    }

}