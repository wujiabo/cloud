package com.wujiabo.cloud.common.feign.interceptor;

import com.wujiabo.cloud.common.feign.context.FeignRequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class HeaderInheritedInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        FeignRequestContext context = FeignRequestContext.getCurrentContext();
        context.reset();
        String loginUserId = request.getHeader("loginUserId");
        String token = request.getHeader("x-token");
        log.info("HeaderInheritedInterceptor thread name {}, loginUserId {}, token {}",Thread.currentThread().getName() ,loginUserId,token);
        context.setToken(token);
        context.setLoginUserId(loginUserId);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        FeignRequestContext.getCurrentContext().close();
    }
}
