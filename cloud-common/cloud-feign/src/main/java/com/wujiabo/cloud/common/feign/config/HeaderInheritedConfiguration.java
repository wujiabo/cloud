package com.wujiabo.cloud.common.feign.config;

import com.netflix.hystrix.Hystrix;
import com.wujiabo.cloud.common.feign.context.CloudRequestContext;
import com.wujiabo.cloud.common.feign.properties.HeaderInheritedProperties;
import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Slf4j
@Configuration
@ConditionalOnClass({Hystrix.class})
@ConditionalOnProperty(value = "feign.header.inherited.enabled", matchIfMissing = true)
@EnableConfigurationProperties(HeaderInheritedProperties.class)
public class HeaderInheritedConfiguration {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                Enumeration<String> headerNames = request.getHeaderNames();
                log.info("ServletRequestAttributes headerNames {}", Thread.currentThread().getName(), headerNames);
                if (headerNames != null) {
                    while (headerNames.hasMoreElements()) {
                        String name = headerNames.nextElement();
                        String header = request.getHeader(name);
                        requestTemplate.header(name, header);
                    }
                }
            }

            CloudRequestContext context = CloudRequestContext.getCurrentContext();

            String loginUserId = context.getLoginUserId();
            if (!StringUtils.isEmpty(loginUserId)) {
                requestTemplate.header("loginUserId", new String[]{loginUserId});
            }
            String token = context.getToken();
            if (!StringUtils.isEmpty(token)) {
                requestTemplate.header("x-token", new String[]{token});
            }
            log.info("RequestInterceptor thread name {}, loginUserId {}, token {}", Thread.currentThread().getName(), loginUserId, token);
        };
    }
}
