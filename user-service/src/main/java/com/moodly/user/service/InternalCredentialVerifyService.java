package com.moodly.user.service;

import com.moodly.common.exception.BaseException;
import com.moodly.common.exception.GlobalErrorCode;
import com.moodly.user.domain.Users;
import com.moodly.user.dto.CredentialVerifyDto;
import com.moodly.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class InternalCredentialVerifyService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Transactional
    public CredentialVerifyDto verify(
            String email,
            String password
    ) {
        Users users = userRepository.findByEmail(email)
                .orElseThrow(() -> new BaseException(GlobalErrorCode.EMAIL_NOT_FOUND));

        if(!encoder.matches(password, users.getPassword())) {
            throw new BaseException(GlobalErrorCode.PASSWORD_MISMATCH);
        }

        List<String> roles = List.of(users.getRole().name());

        return new CredentialVerifyDto(users.getId(), roles);
    }
}
