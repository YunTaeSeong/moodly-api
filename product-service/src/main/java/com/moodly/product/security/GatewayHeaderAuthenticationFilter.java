package com.moodly.product.security;

import com.moodly.common.security.principal.AuthPrincipal;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class GatewayHeaderAuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        Authentication existing = SecurityContextHolder.getContext().getAuthentication();

        // 로그인 사용자가 있으면 다시 principal을 덮어씌울 필요 없음
        if (existing != null && existing.isAuthenticated() && !(existing instanceof AnonymousAuthenticationToken)) {
            filterChain.doFilter(request, response);
            return;
        }


        String userIdHeader = request.getHeader("X-User-Id");
        if (userIdHeader == null || userIdHeader.isBlank()) {
            filterChain.doFilter(request, response);
            return;
        }

        Long userId;
        try {
            userId = Long.parseLong(userIdHeader);
        } catch (NumberFormatException e) {
            filterChain.doFilter(request, response);
            return;
        }

        String rolesHeader = request.getHeader("X-Roles"); // 예: "USER,ADMIN"
        List<String> roles = (rolesHeader == null || rolesHeader.isBlank())
                ? List.of()
                : Arrays.stream(rolesHeader.split(",")).map(String::trim).filter(s -> !s.isBlank()).toList();

        // email은 Gateway에서 안 넘기면 null로 두거나, 필요하면 X-User-Email 같은 헤더 추가해서 채워도 됨
        String emailHeader = request.getHeader("X-User-Email");

        AuthPrincipal principal = AuthPrincipal.builder()
                .userId(userId)
                .email(emailHeader)
                .roles(roles)
                .build();

        // Spring Security 권한으로도 만들어두면 @PreAuthorize 같은 것도 가능
        var authorities = roles.stream()
                .map(r -> r.startsWith("ROLE_") ? r : "ROLE_" + r)
                .map(SimpleGrantedAuthority::new)
                .toList();

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(principal, null, authorities);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}
