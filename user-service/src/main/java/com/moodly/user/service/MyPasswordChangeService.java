package com.moodly.user.service;

import com.moodly.common.exception.BaseException;
import com.moodly.common.exception.GlobalErrorCode;
import com.moodly.user.client.AuthClient;
import com.moodly.user.domain.Users;
import com.moodly.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MyPasswordChangeService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final AuthClient authClient;

    @Transactional
    public void changePassword(
            Long userId,
            String currentPassword,
            String newPassword,
            String newPasswordConfirm,
            String authorization
    ) {
        if(newPassword == null || !newPassword.equals(newPasswordConfirm)) {
            throw new BaseException(GlobalErrorCode.PASSWORD_MISMATCH);
        }

        Users users = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(GlobalErrorCode.USER_NOT_FOUND));

        if(currentPassword == null || !encoder.matches(currentPassword, users.getPassword())) {
            throw new BaseException(GlobalErrorCode.PASSWORD_MISMATCH);
        }

        users.changePassword(encoder.encode(newPassword));

        log.info("Header Authorization ={}", authorization);

        // 비밀번호 변경 시 refresh 모두 삭제 (모두 로그아웃)
        authClient.revokeAllByUserId(authorization, userId);
    }
}
