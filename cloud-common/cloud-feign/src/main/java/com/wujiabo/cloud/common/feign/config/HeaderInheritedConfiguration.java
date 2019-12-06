package com.wujiabo.cloud.common.feign.config;

import com.netflix.hystrix.Hystrix;
import com.wujiabo.cloud.common.feign.properties.HeaderInheritedProperties;
import com.wujiabo.cloud.common.feign.strategy.HeaderInheritedStrategy;
import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Slf4j
@Configuration
@ConditionalOnClass({Hystrix.class})
@ConditionalOnProperty(value = "feign.header.inherited.enabled", matchIfMissing = true)
@EnableConfigurationProperties(HeaderInheritedProperties.class)
public class HeaderInheritedConfiguration {

    @Bean
    public HeaderInheritedStrategy headerInheritedStrategy() {
        return new HeaderInheritedStrategy();
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes();
            if(attributes != null){
                HttpServletRequest request = attributes.getRequest();
                Enumeration<String> headerNames = request.getHeaderNames();
                if (headerNames != null) {
                    while (headerNames.hasMoreElements()) {
                        String name = headerNames.nextElement();
                        String header = request.getHeader(name);
                        requestTemplate.header(name, header);
                    }
                }
            }
        };
    }
}
