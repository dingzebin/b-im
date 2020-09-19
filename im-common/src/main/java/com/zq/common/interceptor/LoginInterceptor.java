package com.zq.common.interceptor;

import com.zq.common.exception.AuthFailedException;
import com.zq.common.utils.TokenUtil;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author dzeb
 * @version 1.0
 * @Description login interceptor
 * @createTime 2020/9/19 9:04 下午
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (TokenUtil.getLoginUser() == null) {
            throw new AuthFailedException("登录失效");
        }
        return super.preHandle(request, response, handler);
    }
}
