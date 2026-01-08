package com.moodly.common.security.jwt;

import com.moodly.common.exception.BaseException;
import com.moodly.common.exception.GlobalErrorCode;
import com.moodly.common.security.principal.AuthPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtProperties properties;
    private SecretKey key;

    @PostConstruct
    private void init() {
        this.key = buildKey(properties.getSecret());
    }

    private static SecretKey buildKey(String secret) {
        if (secret == null || secret.isBlank()) {
            throw new BaseException(GlobalErrorCode.INVALID_JWT_SECRET);
        }
        try {
            byte[] decode = Decoders.BASE64.decode(secret);
            return Keys.hmacShaKeyFor(decode);
        } catch (Exception e) {
            return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        }
    }

    /**
     * 서명, 검증, claims
     */
    public Claims parseAndValidate(String token) {
        Jws<Claims> jws = Jwts.parser()
                .verifyWith(key)
                .requireIssuer(properties.getIssuer())
                .requireAudience(properties.getAudience())
                .build()
                .parseSignedClaims(token);

        return jws.getPayload();
    }

    /**
     * 토큰 유효성 검사
     */
    public boolean isValid(String token) {
        try {
            parseAndValidate(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 인증 사용자
     */
    public AuthPrincipal toPrincipal(Claims claims) {
        String subject = claims.getSubject();
        if (subject == null || subject.isBlank()) {
            throw new BaseException(GlobalErrorCode.USER_NOT_FOUND);
        }

        Long userId;
        try {
            userId = Long.valueOf(subject);
        } catch (NumberFormatException e) {
            throw new BaseException(GlobalErrorCode.INVALID_JWT_TOKEN);
        }

        String email = claims.get("email", String.class);
        List<String> roles = extractRoles(claims, properties.getRoleClaim());

        return AuthPrincipal.builder()
                .userId(userId)   // ✅ Long
                .email(email)
                .roles(roles)
                .build();
    }

    /**
     * Role 추출
     */
    public List<String> extractRoles(Claims claims, String rolesClaimKey) {
        Object raw = claims.get(rolesClaimKey);
        if(raw == null) return List.of();

        if(raw instanceof Collection<?> collection) {
            return collection.stream()
                    .filter(Objects::nonNull)
                    .map(String::valueOf)
                    .toList();
        }

        return List.of(String.valueOf(raw));
    }

    /**
     * 인증 정보
     */
    public Authentication toAuthentication(AuthPrincipal principal) {
        List<SimpleGrantedAuthority> authorities = Optional.ofNullable(principal.getRoles())
                .orElseGet(List::of)
                .stream()
                .filter(r -> r != null && !r.isBlank())
                .map(r -> r.startsWith("ROLE_") ? r : "ROLE_" + r)
                .map(SimpleGrantedAuthority::new)
                .toList();

        return new UsernamePasswordAuthenticationToken(principal, null, authorities);
    }

    /**
     * Authorization 헤더에서 Bearer 토큰 추출
     */
    public String resolveBearerToken(String authorizationHeader) {
        if (authorizationHeader == null) return null;
        if (!authorizationHeader.startsWith("Bearer ")) return null;
        String token = authorizationHeader.substring(7).trim();
        return token.isEmpty() ? null : token;
    }


}
