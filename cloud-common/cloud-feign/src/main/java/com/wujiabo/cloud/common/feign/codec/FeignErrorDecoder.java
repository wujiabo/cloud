package com.wujiabo.cloud.common.feign.codec;

import com.netflix.hystrix.exception.HystrixBadRequestException;
import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

import static feign.FeignException.errorStatus;

@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        log.error("response status : {}", response.status());
        FeignException exception = errorStatus(methodKey, response);
        if (exception instanceof FeignException.InternalServerError) {
            return new HystrixBadRequestException("FeignErrorDecoder", exception);
        }
        return exception;
    }
}
