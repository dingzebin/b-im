package com.zq.common.interceptor;

import com.zq.common.constant.GlobalConstant;
import com.zq.common.exception.AuthFailureException;
import com.zq.common.model.UserInfo;
import com.zq.common.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author dzeb
 * @version 1.0
 * @Description token varification
 * @createTime 2020/9/14 16:18
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            throw new AuthFailureException("登录失效，请重新登录");
        }
        UserInfo userInfo = (UserInfo)redisUtil.get(GlobalConstant.TOKEN_PREFIX + token);
        if (userInfo == null) {
            throw new AuthFailureException("登录失效，请重新登录");
        }
        return true;
    }
}
