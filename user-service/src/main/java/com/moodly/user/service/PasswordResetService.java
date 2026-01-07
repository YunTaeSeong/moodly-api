package com.moodly.user.service;

import com.moodly.common.exception.BaseException;
import com.moodly.common.exception.GlobalErrorCode;
import com.moodly.user.domain.Users;
import com.moodly.user.mail.EmailSender;
import com.moodly.user.repository.UserRepository;
import com.moodly.user.verification.PasswordResetValue;
import com.moodly.user.verification.RedisPasswordResetStore;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.Base64;
import java.util.HexFormat;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private static final String REDIS_KEY_PREFIX = "password:reset:";
    private static final Duration TTL =  Duration.ofMinutes(15);

    private final UserRepository userRepository;
    private final RedisPasswordResetStore passwordResetStore;
    private final EmailSender emailSender;
    private final PasswordEncoder encoder;

    @Value("${app.frontend.base-url}")
    private String frontBaseUrl;

    @Transactional
    public void requestReset(String email) {

        userRepository.findByEmail(email).ifPresent(user -> {
            String token = generateToken();
            String tokenHash = sha256Hex(token);

            String redisKey = REDIS_KEY_PREFIX + tokenHash;
            passwordResetStore.save(redisKey, new PasswordResetValue(user.getId(), user.getEmail()), TTL);

            String link = frontBaseUrl + "/reset-password?token=" + token;
            emailSender.sendPasswordResetLink(user.getEmail(), link);
        });
    }

    @Transactional
    public void confirmReset(String token, String newPassword, String rePassword) {
        if(!newPassword.equals(rePassword)) {
            throw new BaseException(GlobalErrorCode.PASSWORD_MISMATCH);
        }

        String tokenHash = sha256Hex(token);
        String key = REDIS_KEY_PREFIX + tokenHash;

        PasswordResetValue value = passwordResetStore.get(key)
                .orElseThrow(() -> new BaseException(GlobalErrorCode.VERIFICATION_CODE_EXPIRED));

        Users users = userRepository.findByEmail(value.email())
                .orElseThrow(() -> new BaseException(GlobalErrorCode.USER_NOT_FOUND));

        users.changePassword(encoder.encode(newPassword));
        passwordResetStore.delete(key);
    }

    private String generateToken() {
        byte[] bytes = new byte[32];
        new SecureRandom().nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private String sha256Hex(String raw) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(raw.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(digest);
        } catch (Exception e) {
            throw new BaseException(GlobalErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
