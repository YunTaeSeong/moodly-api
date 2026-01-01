package com.moodly.user.service;

import com.moodly.common.exception.BaseException;
import com.moodly.common.exception.ErrorCode;
import com.moodly.common.exception.GlobalErrorCode;
import com.moodly.user.domain.Users;
import com.moodly.user.dto.UserDto;
import com.moodly.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Transactional
    public UserDto register(
            String email,
            String password,
            String rePassword,
            String name,
            String phoneNumber
    ) {
        if (!password.equals(rePassword)) {
            throw new BaseException(GlobalErrorCode.PASSWORD_MISMATCH);
        }

        if (userRepository.existsByEmail(email)) {
            throw new BaseException(GlobalErrorCode.DUPLICATED_EMAIL);
        }

        Users save = userRepository.save(Users.of(email, encoder.encode(password), name, phoneNumber));

        return UserDto.fromEntity(save);
    }
}
