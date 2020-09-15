package com.zq.common.exception;

/**
 * @author dzeb
 * @version 1.0
 * @Description 认证失败异常
 * @createTime 2020/9/14 16:29
 */
public class AuthFailureException extends RuntimeException {
    public AuthFailureException() {
        super();
    }

    public AuthFailureException(String message) {
        super(message);
    }

    public AuthFailureException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthFailureException(Throwable cause) {
        super(cause);
    }

    protected AuthFailureException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
