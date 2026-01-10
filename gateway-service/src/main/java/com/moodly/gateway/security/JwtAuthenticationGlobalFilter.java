package com.moodly.gateway.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moodly.common.exception.BaseException;
import com.moodly.common.exception.GlobalErrorCode;
import com.moodly.common.security.jwt.JwtTokenProvider;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;
import java.util.Map;

/**
 * 모든 요청을 가장 먼저 가로챔
 * Authorization: Bearer 추출
 */
public class JwtAuthenticationGlobalFilter implements GlobalFilter, Ordered {

    private final GatewayJwtProperties props;
    private final JwtVerifier jwtVerifier;
    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;

    private final AntPathMatcher matcher = new AntPathMatcher();

    public JwtAuthenticationGlobalFilter(
            GatewayJwtProperties props,
            JwtVerifier jwtVerifier,
            JwtTokenProvider jwtTokenProvider,
            ObjectMapper objectMapper
    ) {
        this.props = props;
        this.jwtVerifier = jwtVerifier;
        this.jwtTokenProvider = jwtTokenProvider;
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (exchange.getRequest().getMethod() == HttpMethod.OPTIONS) {
            return chain.filter(exchange);
        }

        String path = exchange.getRequest().getURI().getPath();
        if (isPermitAll(path)) {
            return chain.filter(exchange);
        }

        // Authorization 헤더에서 Bearer 토큰 추출 (common-security 로직 사용)
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String token = jwtTokenProvider.resolveBearerToken(authHeader);

        if (token == null) {
            throw new BaseException(GlobalErrorCode.MISSING_AUTHORIZATION);
        }

        // 4) JWT 검증 + 다운스트림 헤더 주입
        try {
            JwtVerifier.VerifiedJwt verified = jwtVerifier.verify(token);

            ServerWebExchange mutated = exchange.mutate()
                    .request(req -> req.headers(headers -> {
                        headers.set(props.getUserIdHeader(), verified.userId());
                        headers.set(props.getRolesHeader(), verified.rolesCsv());
                        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);
                    }))
                    .build();

            return chain.filter(mutated);

        } catch (Exception e) {
            throw new BaseException(GlobalErrorCode.INVALID_JWT_TOKEN);
        }
    }

    private boolean isPermitAll(String path) {
        for (String pattern : props.getPermitAll()) {
            if (matcher.match(pattern, path)) return true;
        }
        return false;
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange, String code, String message) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = Map.of(
                "timestamp", OffsetDateTime.now().toString(),
                "status", 401,
                "code", code,
                "message", message,
                "path", exchange.getRequest().getURI().getPath()
        );

        try {
            byte[] bytes = objectMapper.writeValueAsBytes(body);
            return exchange.getResponse()
                    .writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(bytes)));
        } catch (Exception ex) {
            byte[] fallback = ("{\"status\":401,\"code\":\"" + code + "\",\"message\":\"" + message + "\"}")
                    .getBytes();
            return exchange.getResponse()
                    .writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(fallback)));
        }
    }

    /**
     * 필터 순서: 라우팅 직전 정도
     * 다른 글로벌 필터 존재 시 수정 필요
     */
    @Override
    public int getOrder() {
        return -1;
    }
}
