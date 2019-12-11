package com.wujiabo.cloud.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.wujiabo.cloud.common.base.response.CloudResponse;
import com.wujiabo.cloud.gateway.constant.SecurityConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;
import java.nio.charset.StandardCharsets;

@Slf4j
@Configuration
public class TokenValidFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String requestUri = request.getPath().pathWithinApplication().value();
        log.info("requestUri:{}",requestUri);

        //是否需要校验token
        if (isStartWith(requestUri)) {
            ServerHttpRequest build = request.mutate().build();
            return chain.filter(exchange.mutate().request(build).build());
        }
        String token = request.getHeaders().getFirst(SecurityConstants.TOKEN_KEY);
        if(StringUtils.isBlank(token)){
            return forbiddenVoidMono(exchange, CloudResponse.failed("User Token Forbidden or Expired !"));
        }

        ServerHttpRequest build = request.mutate().build();
        return chain.filter(exchange.mutate().request(build).build());
    }

    private boolean isStartWith(String requestUri) {
        return false;
    }

    @NotNull
    private Mono<Void> forbiddenVoidMono(ServerWebExchange serverWebExchange, CloudResponse response) {
        serverWebExchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        byte[] bytes = JSONObject.toJSONString(response).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = serverWebExchange.getResponse().bufferFactory().wrap(bytes);
        return serverWebExchange.getResponse().writeWith(Flux.just(buffer));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
