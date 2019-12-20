package com.wujiabo.cloud.gateway.filter;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.wujiabo.cloud.common.base.response.CloudResponse;
import com.wujiabo.cloud.gateway.constant.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
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

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.validation.constraints.NotNull;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class TokenValidFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String requestUri = request.getPath().pathWithinApplication().value();
        log.info("requestUri:{}", requestUri);

        //是否需要校验token
        if (isStartWith(requestUri)) {
            ServerHttpRequest build = request.mutate().build();
            return chain.filter(exchange.mutate().request(build).build());
        }
        String token = request.getHeaders().getFirst(SecurityConstants.TOKEN_KEY);
        if (StringUtils.isBlank(token)) {
            return forbiddenVoidMono(exchange, CloudResponse.failed("User Token Empty !"));
        }
        try{
            Claims claims = parseJWT(token);
            log.info("getId:{}",claims.getId());
            log.info("getIssuedAt:{}",claims.getIssuedAt());
            log.info("getSubject:{}",claims.getSubject());
            log.info("getIssuer:{}",claims.getIssuer());
        }catch (Exception e){
            log.error(e.getMessage());
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

    /**
     * 创建jwt
     *
     * @return
     * @throws Exception
     */
    private String createJWT() throws Exception {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        Date now = DateUtil.date();
        Map<String, Object> claims = new HashMap<>();
        claims.put("uid", "DSSFAWDWADAS...");
        claims.put("user_name", "admin");
        claims.put("nick_name", "DASDA121");
        SecretKey key = generalKey();
        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                .setId(IdUtil.simpleUUID())
                .setIssuedAt(now)
                .setExpiration(DateUtil.offset(now, DateField.SECOND, 3600))
                .setSubject(SecurityConstants.JWT_SUBJECT)
                .signWith(key, signatureAlgorithm);
        return builder.compact();
    }

    /**
     * 解密jwt
     *
     * @param jwt
     * @return
     * @throws Exception
     */
    private Claims parseJWT(String jwt) {
        SecretKey key = generalKey();
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwt).getBody();
        return claims;
    }

    /**
     * 由字符串生成加密key
     *
     * @return
     */
    private SecretKey generalKey() {
        String stringKey = SecurityConstants.JWT_SECRET;
        byte[] encodedKey = Base64.decodeBase64(stringKey);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }
}
