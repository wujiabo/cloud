package com.wujiabo.cloud.test.controller;

import com.wujiabo.cloud.test.feign.DemoFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping(value = "/api/test")
public class TestController {

    @Autowired
    private DemoFeign demoFeign;

    @GetMapping("/test")
    public String test(@RequestParam(value = "name", required = true) String name, HttpServletRequest servletRequest) {
        log.info("getHeader:{}",servletRequest.getHeader("x-token"));
        return "@@@@";
    }

    @GetMapping("/test1")
    public String test1(HttpServletRequest servletRequest) {
        log.info("getHeader:{}",servletRequest.getHeader("x-token"));
        return demoFeign.test1();
    }

    @GetMapping("/test2")
    public String test2() {
        return demoFeign.test2();
    }
}