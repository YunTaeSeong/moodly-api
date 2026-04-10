package com.moodly.common.security.jwt;

import com.moodly.common.exception.BaseException;
import com.moodly.common.exception.GlobalErrorCode;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

/**
 * auth-service와 각 리소스 서버가 키를 만들 도록 단일 구현
 */
public final class JwtSigningKeySupport {

    private JwtSigningKeySupport() {
    }

    public static SecretKey toHmacShaKey(String secret) {
        if (secret == null || secret.isBlank()) {
            throw new BaseException(GlobalErrorCode.INVALID_JWT_SECRET);
        }
        String s = secret.strip();
        try {
            byte[] decode = Decoders.BASE64.decode(s);
            return Keys.hmacShaKeyFor(decode);
        } catch (Exception e) {
            return Keys.hmacShaKeyFor(s.getBytes(StandardCharsets.UTF_8));
        }
    }
}
