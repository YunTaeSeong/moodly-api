package com.moodly.gateway.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "security.jwt.gateway")
public class GatewayJwtProperties {

    /**
     * JWT 검증을 스킵할 경로 패턴 (Ant 스타일)
     * 예: /AUTH/**, /actuator/**
     */
    private List<String> permitAll = new ArrayList<>(List.of(
            "/AUTH/**",
            "/actuator/**"
    ));

    /** 다운스트림에 전달할 사용자 식별 헤더 */
    private String userIdHeader = "X-User-Id";

    /** 다운스트림에 전달할 roles 헤더 */
    private String rolesHeader = "X-Roles";

    public List<String> getPermitAll() { return permitAll; }
    public void setPermitAll(List<String> permitAll) { this.permitAll = permitAll; }

    public String getUserIdHeader() { return userIdHeader; }
    public void setUserIdHeader(String userIdHeader) { this.userIdHeader = userIdHeader; }

    public String getRolesHeader() { return rolesHeader; }
    public void setRolesHeader(String rolesHeader) { this.rolesHeader = rolesHeader; }
}
