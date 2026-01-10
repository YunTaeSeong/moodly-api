package com.moodly.gateway.security;

public interface JwtVerifier {
    /**
     * 토큰 유효성 검증(서명/만료 등) : 실패 throw
     */
    VerifiedJwt verify(String token);

    record VerifiedJwt(String userId, String rolesCsv) {}
}
