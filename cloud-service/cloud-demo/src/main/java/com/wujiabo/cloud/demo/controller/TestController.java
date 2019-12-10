package com.wujiabo.cloud.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping(value = "/api/demo")
public class TestController {

    @GetMapping("/test")
    public String test(HttpServletRequest servletRequest) {
        log.info("getHeader:{}",servletRequest.getHeader("x-token"));
        return "@@@@";
    }

    @GetMapping("/test1")
    public String test1(HttpServletRequest servletRequest) {
        log.info("getHeader:{}",servletRequest.getHeader("x-token"));
        log.info("===<call trace-2, TraceId={}, SpanId={}>===",
                servletRequest.getHeader("X-B3-TraceId"), servletRequest.getHeader("X-B3-SpanId"));
        return "test1";
    }

    @GetMapping("/test2")
    public String test2() {
        log.info("getHeader:{}",1/0);
        return "test1";
    }
}