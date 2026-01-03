package com.moodly.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moodly.user.domain.Users;
import com.moodly.user.repository.UserRepository;
import com.moodly.user.request.CredentialRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class InternalCredentialVerifyApiControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Test
    @DisplayName("로그인 통합 테스트 : 성공")
    void 로그인_성공() throws Exception {
        // given
        Users saved = userRepository.save(
                Users.of(
                        "test@test.com",
                        encoder.encode("test1234!@#$"),
                        "테스트",
                        "010-1111-2222"
                )
        );

        CredentialRequest request = new CredentialRequest("test@test.com", "test1234!@#$");

        // when & then
        mockMvc.perform(
                        post("/internal/user/credential/verify")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(saved.getId()))
                .andExpect(jsonPath("$.roles[0]").value("USER"));
    }

    @Test
    @DisplayName("로그인 통합 테스트 : 실패(비밀번호 불일치)")
    void 로그인_실패_비밀번호불일치() throws Exception {
        // given
        userRepository.save(
                Users.of(
                        "test@test.com",
                        encoder.encode("test1234!@#$"),
                        "테스트",
                        "010-1111-2222"
                )
        );

        CredentialRequest request = new CredentialRequest("test@test.com", "wrong!@#$");

        // when & then
        mockMvc.perform(
                        post("/internal/user/credential/verify")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isBadRequest());
    }
}
