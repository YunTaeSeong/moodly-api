package com.moodly.auth.token;

import com.moodly.auth.client.user.UserServiceClient;
import com.moodly.auth.domain.RefreshToken;
import com.moodly.auth.response.TokenPairResponse;
import com.moodly.auth.repository.RefreshTokenRepository;
import com.moodly.common.exception.BaseException;
import com.moodly.common.exception.GlobalErrorCode;
import com.moodly.common.security.jwt.JwtProperties;
import com.moodly.common.security.jwt.JwtTokenProvider;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HexFormat;
import java.util.List;
import java.util.UUID;


@Component
@RequiredArgsConstructor
public class JwtTokenIssuer {

    private final JwtProperties jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserServiceClient userServiceClient;

    public TokenPairResponse issue(Long userId, List<String> roles) {
        String access = createAccessToken(userId, roles);
        String refresh = createAndStoreRefreshToken(userId);
        return new TokenPairResponse(access, refresh);
    }

    @Transactional
    public TokenPairResponse refresh(String refreshTokenRaw) {
        if (refreshTokenRaw == null || refreshTokenRaw.isBlank()) {
            throw new BaseException(GlobalErrorCode.INVALID_REFRESH_TOKEN);
        }

        LocalDateTime now = LocalDateTime.now();
        String hash = sha256Hex(refreshTokenRaw);

        RefreshToken stored = refreshTokenRepository.findByRefreshTokenHash(hash)
                .orElseThrow(() -> new BaseException(GlobalErrorCode.INVALID_REFRESH_TOKEN));

        if (stored.isExpired(now)) {
            refreshTokenRepository.deleteByUserId(stored.getUserId());
            throw new BaseException(GlobalErrorCode.EXPIRED_REFRESH_TOKEN);
        }

        // rotation: 해당 유저 refresh 전부 삭제 후 새로 발급
        refreshTokenRepository.deleteByUserId(stored.getUserId());

        List<String> roles = userServiceClient.getRoles(stored.getUserId());
        String newAccess = createAccessToken(stored.getUserId(), roles);
        String newRefresh = createAndStoreRefreshToken(stored.getUserId());

        return new TokenPairResponse(newAccess, newRefresh);
    }

    private String createAccessToken(Long userId, List<String> roles) {
        Instant now = Instant.now();
        Date exp = Date.from(now.plusSeconds(jwtTokenProvider.getAccessTokenTtlSeconds()));

        return Jwts.builder()
                .subject(String.valueOf(userId))
                .issuer(jwtTokenProvider.getIssuer())
                .audience().add(jwtTokenProvider.getAudience()).and()
                .claim(jwtTokenProvider.getRoleClaim(), roles)
                .issuedAt(Date.from(now))
                .expiration(exp)
                .signWith(getKey())
                .compact();
    }

    private String createAndStoreRefreshToken(Long userId) {
        Instant nowInstant = Instant.now();
        Instant expInstant = nowInstant.plusSeconds(jwtTokenProvider.getRefreshTokenTtlSeconds());

        String random = UUID.randomUUID() + "." + UUID.randomUUID();
        String hash = sha256Hex(random);

        LocalDateTime expiredAt = LocalDateTime.ofInstant(expInstant, ZoneId.systemDefault());

        refreshTokenRepository.save(
                RefreshToken.builder()
                        .userId(userId)
                        .refreshTokenHash(hash)
                        .expiredAt(expiredAt)
                        .build()
        );

        return random;
    }

    private SecretKey getKey() {
        String secret = jwtTokenProvider.getSecret();
        try {
            return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        } catch (Exception e) {
            return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        }
    }

    public String sha256Hex(String raw) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(raw.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(digest);
        } catch (Exception e) {
            throw new BaseException(GlobalErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}

