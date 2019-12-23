package com.wujiabo.cloud.common.feign.context;

import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Slf4j
public class CloudRequestContext implements Serializable {
    private static final ThreadLocal<CloudRequestContext> contextHolder = new InheritableThreadLocal();

    private String loginUserId;
    private String token;

    private CloudRequestContext() {
    }

    public static synchronized CloudRequestContext getCurrentContext() {
        CloudRequestContext context = contextHolder.get();
        if (context == null) {
            log.info("getCurrentContext():RequestContext is null, new RequestContext() in Thread " + Thread.currentThread().getName());
            contextHolder.set(new CloudRequestContext());
            context = contextHolder.get();
        }
        return context;
    }

    public void close() {
        this.reset();
        log.info("RequestContext close");
        contextHolder.remove();
    }


    public void reset() {
        log.info("RequestContext reset");
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
