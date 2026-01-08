package com.moodly.auth.service;

import com.moodly.auth.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void revokeAllByUserId(Long userId) {
        refreshTokenRepository.deleteByUserId(userId);
    }
}
