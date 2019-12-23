package com.wujiabo.cloud.common.feign.config;

import com.netflix.hystrix.Hystrix;
import com.wujiabo.cloud.common.feign.interceptor.HeaderInheritedInterceptor;
import com.wujiabo.cloud.common.feign.properties.HeaderInheritedProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
@ConditionalOnClass({Hystrix.class})
@ConditionalOnProperty(value = "feign.header.inherited.enabled", matchIfMissing = true)
@EnableConfigurationProperties(HeaderInheritedProperties.class)
public class FeignWebMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HeaderInheritedInterceptor());
    }
}
