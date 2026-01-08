package com.moodly.user.service;

import com.moodly.common.exception.BaseException;
import com.moodly.common.exception.GlobalErrorCode;
import com.moodly.user.client.AuthClient;
import com.moodly.user.domain.Users;
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
class MyPasswordChangeServiceTest {

    @InjectMocks
    private MyPasswordChangeService myPasswordChangeService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private AuthClient authClient;

    Long userId;
    String currentPassword;
    String newPassword;
    String newPasswordConfirm;
    String authorization;

    @BeforeEach
    @Test
    void init() {
        userId = 1L;
        currentPassword = "test1234!@#$";
        newPassword = "test1234!@#$%";
        newPasswordConfirm = "test1234!@#$%";
        authorization = "Bearer Authorization";
    }

    @DisplayName("패스워드 변경 : 성공")
    void 패시워드_변경_성공() throws Exception {
        // given
        Users users = mock(Users.class);
        when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(users));
        when(users.getPassword()).thenReturn("encodedPassword");
        when(encoder.matches(currentPassword, "encodedPassword")).thenReturn(true);
        when(encoder.encode(newPassword)).thenReturn("encodedNewPassword");

        // when
        myPasswordChangeService.changePassword(userId, currentPassword, newPassword, newPasswordConfirm, authorization);

        // then
        verify(userRepository).findById(userId);
        verify(encoder).matches(currentPassword, "encodedNewPassword");
        verify(encoder).encode(newPassword);
        verify(users).changePassword("encodedNewPassword");

        verify(authClient).revokeAllByUserId(authorization, userId);
        verifyNoMoreInteractions(authClient);
    }

    @Test
    @DisplayName("패스워드 변경 실패: 새 비밀번호 확인 불일치")
    void 패스워드_변경_실패() throws Exception {
        // given
        String newPasswordConfirm = "differentPassword";

        // when
        BaseException ex = assertThrows(BaseException.class, () ->
                myPasswordChangeService.changePassword(userId, currentPassword, newPassword, newPasswordConfirm, authorization)
        );

        // then
        assertEquals(GlobalErrorCode.PASSWORD_MISMATCH, ex.getErrorCode());

        verifyNoInteractions(userRepository);
        verifyNoInteractions(encoder);
        verifyNoInteractions(authClient);

    }
}