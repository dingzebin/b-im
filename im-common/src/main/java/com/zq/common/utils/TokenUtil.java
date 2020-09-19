package com.zq.common.utils;

import com.zq.common.constant.GlobalConstant;
import com.zq.common.model.UserInfo;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author dzeb
 * @version 1.0
 * @Description token utils
 * @createTime 2020/9/19 21:06
 */
public class TokenUtil {
    public final static String TOKEN_PREFIX = "token_";

    public final static String REQUEST_TOKEN_HEADER = "account";

    public static UserInfo getLoginUser() {
        return getUserByAccount(getRequest().getHeader(REQUEST_TOKEN_HEADER));
    }

    public static UserInfo getUserByAccount(String account) {
        return (UserInfo) SpringContextHolder.getBean(RedisUtil.class).get(TOKEN_PREFIX + account);
    }

    public static void setUser(UserInfo user) {
        SpringContextHolder.getBean(RedisUtil.class).set(GlobalConstant.TOKEN_PREFIX + user.getAccount(), user, -1);
    }

    public static void removeUser(String account) {
        SpringContextHolder.getBean(RedisUtil.class).del(GlobalConstant.TOKEN_PREFIX + account);
    }

    public static HttpServletRequest getRequest() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        return request;
    }
}
