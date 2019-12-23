package com.wujiabo.cloud.common.feign.context;

import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Slf4j
public class FeignRequestContext implements Serializable {
    private static final ThreadLocal<FeignRequestContext> contextHolder = new InheritableThreadLocal();

    private String loginUserId;
    private String token;

    private FeignRequestContext() {
    }

    public static synchronized FeignRequestContext getCurrentContext() {
        FeignRequestContext context = contextHolder.get();
        if (context == null) {
            log.info("new RequestContext() in Thread {}", Thread.currentThread().getName());
            contextHolder.set(new FeignRequestContext());
            context = contextHolder.get();
        }
        return context;
    }

    public void close() {
        log.info("RequestContext close {}", Thread.currentThread().getName());
        this.reset();
        contextHolder.remove();
    }


    public void reset() {
        log.info("RequestContext reset {}", Thread.currentThread().getName());
        this.loginUserId = null;
        this.token = null;
    }

    public String getLoginUserId() {
        return loginUserId;
    }

    public void setLoginUserId(String loginUserId) {
        this.loginUserId = loginUserId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
