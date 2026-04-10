package com.moodly.common.security.filter;

import com.moodly.common.security.jwt.JwtTokenProvider;
import com.moodly.common.security.principal.AuthPrincipal;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;

    /**
     * 서비스 간 Feign 등으로 호출되는 /internal/** 는 permitAll 이더라도,
     * 잘못된 Bearer 헤더가 붙으면 필터가 먼저 401을 내는 문제가 생길 수 있어 JWT 검증 자체를 생략
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        if (path == null) {
            return false;
        }
        String contextPath = request.getContextPath();
        if (contextPath != null && !contextPath.isEmpty() && path.startsWith(contextPath)) {
            path = path.substring(contextPath.length());
        }
        return path.startsWith("/internal/");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("[USER] Authorization={}", request.getHeader(HttpHeaders.AUTHORIZATION));
        String token = tokenProvider.resolveBearerToken(request.getHeader(HttpHeaders.AUTHORIZATION));

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            Claims claims = tokenProvider.parseAndValidate(token);
            AuthPrincipal principal = tokenProvider.toPrincipal(claims);
            Authentication authentication = tokenProvider.toAuthentication(principal);

            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            log.warn("JWT 검증 실패: {} — {}", e.getClass().getSimpleName(), e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("""
                    {"message":"Unauthorized","code":"INVALID_TOKEN"}
                    """);
        }
    }
}
