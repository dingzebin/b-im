package com.zq.common.exception;

/**
 * @author dzeb
 * @version 1.0
 * @Description login verification failed exception
 * @createTime 2020/9/19 21:20
 */
public class AuthFailedException extends RuntimeException {
    public AuthFailedException() {
        super();
    }

    public AuthFailedException(String message) {
        super(message);
    }

    public AuthFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthFailedException(Throwable cause) {
        super(cause);
    }

    protected AuthFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
