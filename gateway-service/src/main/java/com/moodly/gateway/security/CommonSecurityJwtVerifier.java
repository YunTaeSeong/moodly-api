package com.moodly.gateway.security;

import com.moodly.common.security.jwt.JwtTokenProvider;
import com.moodly.common.security.principal.AuthPrincipal;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
/**
 * auth-service에서 쓰는 JWT 규칙과 완전히 동일
 * issuer, audience, roles-claim 등 통일
 * Gateway, Auth JWT 규칙 싱크 맞추기용
 */
public class CommonSecurityJwtVerifier implements JwtVerifier {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public VerifiedJwt verify(String token) {
        // 1) 서명/issuer/audience/exp 등 검증 + claims 파싱
        Claims claims = jwtTokenProvider.parseAndValidate(token);

        // 2) claims -> AuthPrincipal (userId Long, roles List<String>)
        AuthPrincipal principal = jwtTokenProvider.toPrincipal(claims);

        // 3) Gateway가 downstream으로 전달할 값 정리
        String userId = String.valueOf(principal.getUserId()); // Long -> String
        String rolesCsv = principal.getRoles() == null || principal.getRoles().isEmpty()
                ? ""
                : String.join(",", principal.getRoles());

        return new VerifiedJwt(userId, rolesCsv);
    }
}
