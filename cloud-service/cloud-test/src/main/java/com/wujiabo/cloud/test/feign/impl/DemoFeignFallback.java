package com.wujiabo.cloud.test.feign.impl;

import com.wujiabo.cloud.test.feign.DemoFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DemoFeignFallback implements DemoFeign {
    @Override
    public String test1() {
        log.warn("Fallback");
        return null;
    }

    @Override
    public String test2() {
        log.warn("Fallback");
        return null;
    }
}
