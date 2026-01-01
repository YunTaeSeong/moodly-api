package com.moodly.user.service;

import com.moodly.common.exception.BaseException;
import com.moodly.common.exception.GlobalErrorCode;
import com.moodly.user.domain.Users;
import com.moodly.user.dto.UserDto;
import com.moodly.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder encoder;

    String email;
    String password;
    String rePassword;
    String encodedPassword;
    String name;
    String phoneNumber;

    @BeforeEach
    void init() {
        email = "test@test.com";
        password = "test1234!@#$";
        rePassword = "test1234!@#$";
        encodedPassword = "encodedPassword";
        name = "테스트";
        phoneNumber = "010-1111-2222";
    }

    @DisplayName("회원가입 성공")
    @Test
    void 회원가입_성공() {
        // given
        when(encoder.encode(password)).thenReturn(encodedPassword);
        when(userRepository.existsByEmail(email)).thenReturn(false);

        Users savedUser = Users.of(email, encodedPassword, name, phoneNumber);
        when(userRepository.save(any(Users.class))).thenReturn(savedUser);

        // when
        UserDto userDto = userService.register(email, password, rePassword, name, phoneNumber);

        // then
        assertNotNull(userDto);
        assertEquals(email, userDto.getEmail());
        assertEquals(name, userDto.getName());
        assertEquals(phoneNumber, userDto.getPhoneNumber());

        verify(encoder).encode(password);
        verify(userRepository).existsByEmail(email);
        verify(userRepository).save(any(Users.class));
    }

    @DisplayName("회원가입 실패 : 이메일 중복")
    @Test
    void 회원가입_실패_이메일_중복() {
        // given
        when(userRepository.existsByEmail(email)).thenReturn(true);

        // when
        BaseException baseException = assertThrows(
                BaseException.class,
                () -> userService.register(email, password, rePassword, name, phoneNumber)
        );

        // then
        assertEquals(GlobalErrorCode.DUPLICATED_EMAIL, baseException.getErrorCode());

        verify(userRepository).existsByEmail(email);
    }

    @DisplayName("회원가입 실패 : 패스워드 불일치")
    void 회원가입_실패_패스워드_불일치() {
        // given
        String wrongPassword = "wrongPassword";

        // when
        BaseException baseException = assertThrows(
                BaseException.class,
                () -> userService.register(email, password, wrongPassword, name, phoneNumber)
        );

        // then
        assertEquals(GlobalErrorCode.PASSWORD_MISMATCH, baseException.getErrorCode());

        verifyNoInteractions(userRepository);
        verifyNoInteractions(encoder);
    }
}