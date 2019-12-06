package com.wujiabo.cloud.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api/demo")
public class TestController {

    @GetMapping("/test")
    public String test(@RequestParam(value = "name", required = true) String name, HttpServletRequest servletRequest) {
        System.out.println("demo2@" + servletRequest.getHeader("x-token"));
        return "@@@@" + name;
    }

    @GetMapping("/test1")
    public String test1(HttpServletRequest servletRequest) {
        System.out.println("demo@" + servletRequest.getHeader("x-token"));
        return "test1";
    }

    @GetMapping("/test2")
    public String test2() {
        System.out.println(1/0);
        return "test1";
    }
}