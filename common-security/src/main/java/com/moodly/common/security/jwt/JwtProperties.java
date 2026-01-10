package com.moodly.common.security.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "security.jwt.common")
public class JwtProperties {

    // HS256 secret
    private String secret;

    // 토큰 발급
    // ex: moodly-auth
    private String issuer;

    // 토큰 대상
    // ex: moodly-api
    private String audience;

    // ex : 'USER', 'ADMIN'
    private String roleClaim = "roles";

    private long accessTokenTtlSeconds;

    private long refreshTokenTtlSeconds;
}
