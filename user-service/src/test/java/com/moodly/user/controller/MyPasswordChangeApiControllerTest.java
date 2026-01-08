package com.moodly.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moodly.common.security.principal.AuthPrincipal;
import com.moodly.user.client.AuthClient;
import com.moodly.user.domain.Users;
import com.moodly.user.repository.UserRepository;
import com.moodly.user.request.MyChangePasswordRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@Transactional
class MyPasswordChangeApiControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @MockitoBean
    AuthClient authClient;

    @DisplayName("패스워드 변경 통합테스트 : 성공")
    @Test
    void 패스워드_변경_성공_통합테스트() throws Exception {
        // given
        Long userId = 1L;
        String email = "test@test.com";
        String currentPassword = "test1234!@#$";
        String newPassword = "test1234!@#$%";
        String newPasswordConfirm = "test1234!@#$%";

        Users user = Users.of(
                email,
                encoder.encode(currentPassword),
                "테스트",
                "010-1111-2222"
        );

        Users saved = userRepository.save(user);
        Long savedId = saved.getId();

        MyChangePasswordRequest request = new MyChangePasswordRequest(
                userId,
                currentPassword,
                newPassword,
                newPasswordConfirm
        );

        AuthPrincipal principal = AuthPrincipal.builder()
                .userId(savedId)
                .email(email)
                .roles(List.of("USER"))
                .build();

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(principal, null, List.of())
        );

        String authorization = "Bearer Authorization";

        // when
        mockMvc.perform(
                patch("/user/mypage/security/changePassword")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("Authorization", authorization)
        )
        .andExpect(status().isOk());

        // then
        Users updated = userRepository.findById(savedId).orElseThrow();

        assertFalse(encoder.matches(currentPassword, updated.getPassword()));
        assertTrue(encoder.matches(newPassword, updated.getPassword()));

        verify(authClient).revokeAllByUserId(eq(authorization), eq(savedId));
    }

    @DisplayName("패스워드 변경 실패 통합테스트 : 새 비밀번호 확인 불일치")
    @Test
    void 패스워드_변경_실패_새비밀번호_확인_불일치() throws Exception {
        // given
        String email = "test@test.com";
        String currentPassword = "test1234!@#$";
        String newPassword = "test1234!@#$%";
        String newPasswordConfirm = "differentPassword";

        Users user = Users.of(
                email,
                encoder.encode(currentPassword),
                "테스트",
                "010-1111-2222"
        );

        Users saved = userRepository.save(user);
        Long savedId = saved.getId();

        MyChangePasswordRequest request = new MyChangePasswordRequest(
                savedId,
                currentPassword,
                newPassword,
                newPasswordConfirm
        );

        AuthPrincipal principal = AuthPrincipal.builder()
                .userId(savedId)
                .email(email)
                .roles(List.of("USER"))
                .build();

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(principal, null, List.of())
        );

        String authorization = "Bearer Authorization";

        // when
        mockMvc.perform(
                        patch("/user/mypage/security/changePassword")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                                .header("Authorization", authorization)
                )
                .andExpect(status().isBadRequest());

        // then
        Users notChanged = userRepository.findById(savedId).orElseThrow();

        assertTrue(encoder.matches(currentPassword, notChanged.getPassword()));
    }
}