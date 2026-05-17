package com.moodly.gateway.config;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Gateway globalcors와 하위 MSA SecurityConfig CORS가 동시에 적용되면
 * Access-Control-Allow-Origin 이 중복되어 브라우저가 차단한다. 응답 직전에 하나만 남긴다.
 */
@Component
public class CorsResponseHeaderDedupeFilter implements GlobalFilter, Ordered {

    private static final List<String> DEDUPE_HEADERS = List.of(
            "Access-Control-Allow-Origin",
            "Access-Control-Allow-Credentials",
            "Access-Control-Expose-Headers"
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            HttpHeaders headers = exchange.getResponse().getHeaders();
            for (String name : DEDUPE_HEADERS) {
                List<String> values = headers.get(name);
                if (values != null && values.size() > 1) {
                    headers.set(name, values.getFirst());
                }
            }
        }));
    }

    @Override
    public int getOrder() {
        return NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER + 1;
    }
}
