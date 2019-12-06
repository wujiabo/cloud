package com.wujiabo.cloud.test.feign;

import com.wujiabo.cloud.test.feign.impl.DemoFeignFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "cloud-demo", fallback = DemoFeignFallback.class)
public interface DemoFeign {

    @RequestMapping(value = "/api/demo/test1", method = RequestMethod.GET)
    String test1();
    @RequestMapping(value = "/api/demo/test2", method = RequestMethod.GET)
    String test2();
}
