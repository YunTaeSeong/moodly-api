package com.moodly.user.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moodly.user.domain.Users;
import com.moodly.user.repository.UserRepository;
import com.moodly.user.request.UserRegisterRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class UserApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("회원가입 통합 테스트 : 성공")
    void 회원가입_성공() throws Exception {
        // given
        UserRegisterRequest request = new UserRegisterRequest(
                "test@test.com",
                "test1234!@#$",
                "test1234!@#$",
                "테스트",
                "010-1111-2222"
        );

        // when & then
        mockMvc.perform(
                        post("/user/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@test.com"))
                .andExpect(jsonPath("$.name").value("테스트"))
                .andExpect(jsonPath("$.phoneNumber").value("010-1111-2222"));

        assertTrue(userRepository.existsByEmail("test@test.com"));
    }

    @Test
    @DisplayName("회원가입 실패 통합 테스트 : 비밀번호 불일치")
    void 회원가입_실패_비밀번호_불일치() throws Exception {
        // given
        UserRegisterRequest request = new UserRegisterRequest(
                "test@test.com",
                "test1234!@#$",
                "test1234!@#$%^",
                "테스트",
                "010-1111-2222"
        );

        // when & then
        mockMvc.perform(
                post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        assertFalse(userRepository.existsByEmail("test@test.com"));
    }

    @Test
    @DisplayName("회원가입 실패 통합 테스트 : 이메일 중복")
    void 회원가입_실패_이메일_중복() throws Exception {
        // given
        userRepository.save(Users.of(
                "test@test.com",
                "encodedPassword",
                "테스트",
                "010-1111-2222"
        ));

        UserRegisterRequest request = new UserRegisterRequest(
                "test@test.com",
                "test1234!@#$",
                "test1234!@#$",
                "테스트",
                "010-1111-2222"
        );

        // when & then
        mockMvc.perform(
                        post("/user/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());

        assertTrue(userRepository.existsByEmail("test@test.com"));
    }



}
