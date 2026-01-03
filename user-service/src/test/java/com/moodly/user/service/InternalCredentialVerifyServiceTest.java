package com.moodly.user.service;

import com.moodly.common.exception.BaseException;
import com.moodly.common.exception.GlobalErrorCode;
import com.moodly.user.domain.Users;
import com.moodly.user.dto.CredentialVerifyDto;
import com.moodly.user.enums.UserRole;
import com.moodly.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InternalCredentialVerifyServiceTest {

    @InjectMocks
    private InternalCredentialVerifyService credentialVerifyService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder encoder;

    String email;
    String password;
    String encodedPassword;

    @BeforeEach
    void init() {
        email = "test@test.com";
        password = "test1234!@#$";
        encodedPassword = "encodedPassword";
    }

    @Test
    @DisplayName("로그인 검증 성공: 이메일, 비밀번호 일치 시 userId와 roles 반환")
    void verify_success() {
        // given
        Users user = mock(Users.class);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(encoder.matches(password, encodedPassword)).thenReturn(true);
        when(user.getId()).thenReturn(1L);
        when(user.getPassword()).thenReturn(encodedPassword);
        when(user.getRole()).thenReturn(UserRole.USER);

        // when
        CredentialVerifyDto result = credentialVerifyService.verify(email, password);

        // then
        assertNotNull(result);
        assertEquals(1L, result.getUserId());
        assertEquals(1,  result.getRoles().size());
        assertEquals("USER", result.getRoles().get(0));

        verify(userRepository).findByEmail(email);
        verify(encoder).matches(password, encodedPassword);
    }

    @Test
    @DisplayName("로그인 검증 실패 : 이메일 없으면 에러")
    void 이메일_중복() throws Exception {
        // given
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // when
        BaseException baseException = assertThrows(
                BaseException.class,
                () -> credentialVerifyService.verify(email, password)
        );

        // then
        assertEquals(GlobalErrorCode.EMAIL_NOT_FOUND, baseException.getErrorCode());

        verify(userRepository).findByEmail(email);
    }

    @Test
    @DisplayName("로그인 검증 실패 : 비밀번호 불일치")
    void 비밀번호_불일치() throws Exception {
        // given
        Users users = mock(Users.class);

        // when
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(users));
        when(users.getPassword()).thenReturn(encodedPassword);
        when(encoder.matches(password, encodedPassword)).thenReturn(false);

        // then
        BaseException baseException = assertThrows(
                BaseException.class,
                () -> credentialVerifyService.verify(email, password)
        );

        assertEquals(GlobalErrorCode.PASSWORD_MISMATCH, baseException.getErrorCode());

        verify(userRepository).findByEmail(email);
        verify(encoder).matches(password, encodedPassword);
    }

}