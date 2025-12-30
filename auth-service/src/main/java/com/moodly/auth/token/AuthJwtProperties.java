package com.moodly.auth.token;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "security.jwt")
public class AuthJwtProperties {
    private String secret;

    private String issuer;

    private String audience;

    private String rolesClaim = "roles";

    private long accessTokenTtlSeconds;

    private long refreshTokenTtlSeconds;
}
