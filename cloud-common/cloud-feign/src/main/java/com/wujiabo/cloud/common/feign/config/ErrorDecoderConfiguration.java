package com.wujiabo.cloud.common.feign.config;

import com.netflix.hystrix.Hystrix;
import com.wujiabo.cloud.common.feign.codec.FeignErrorDecoder;
import com.wujiabo.cloud.common.feign.properties.ErrorDecoderProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass({Hystrix.class})
@ConditionalOnProperty(value = "feign.error.decoder.enabled", matchIfMissing = true)
@EnableConfigurationProperties(ErrorDecoderProperties.class)
public class ErrorDecoderConfiguration {

    @Bean
    public FeignErrorDecoder feignErrorDecoder() {
        return new FeignErrorDecoder();
    }
}
