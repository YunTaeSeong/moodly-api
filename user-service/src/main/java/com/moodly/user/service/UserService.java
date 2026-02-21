package com.moodly.user.service;

import com.moodly.common.exception.BaseException;
import com.moodly.common.exception.GlobalErrorCode;
import com.moodly.user.domain.Users;
import com.moodly.user.dto.UserDto;
import com.moodly.user.enums.Provider;
import com.moodly.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
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

    /**
     * 카카오 사용자 조회 또는 생성
     * @param providerId 카카오 사용자 ID
     * @param name 카카오 닉네임
     * @param email 카카오 이메일 (선택사항)
     * @return UserDto
     */
    @Transactional
    public UserDto findOrCreateKakaoUser(String providerId, String name, String email) {
        log.info("[UserService] 카카오 사용자 조회/생성: providerId={}, name={}, email={}", providerId, name, email);

        // 기존 카카오 사용자 조회
        return userRepository.findByProviderAndProviderId(Provider.KAKAO, providerId)
                .map(user -> {
                    log.info("[UserService] 기존 카카오 사용자 발견: userId={}", user.getId());
                    return UserDto.fromEntity(user);
                })
                .orElseGet(() -> {
                    log.info("[UserService] 신규 카카오 사용자 생성: providerId={}", providerId);
                    Users newUser = Users.ofKakao(providerId, name, email);
                    Users saved = userRepository.save(newUser);
                    return UserDto.fromEntity(saved);
                });
    }

    /**
     * 카카오 사용자 ID로 조회
     */
    @Transactional(readOnly = true)
    public UserDto findByKakaoProviderId(String providerId) {
        return userRepository.findByProviderAndProviderId(Provider.KAKAO, providerId)
                .map(UserDto::fromEntity)
                .orElseThrow(() -> new BaseException(GlobalErrorCode.USER_NOT_FOUND));
    }
}
