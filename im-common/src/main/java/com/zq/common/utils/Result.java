package com.zq.common.utils;

import lombok.Data;

import java.util.Optional;

/**
 * @author dzeb
 * @version 1.0
 * @Description 返回结果封装
 * @createTime 2020/9/15 10:01 下午
 */
@Data
public class Result<T> {
    private int code;

    private String msg;

    private T data;

    public Result() {
    }

    public static <T> Result<T> ok(String msg) {
        return restResult(null, StatusCode.SUCCESS.code, msg);
    }
    public static <T> Result<T> ok(T data) {
        return restResult(data, StatusCode.SUCCESS);
    }

    public static <T> Result<T> ok() {
        return restResult(null, StatusCode.SUCCESS);
    }
    public static <T> Result<T> failed() {
        return restResult(null, StatusCode.FAILED);
    }

    public static <T> Result<T> failed(String msg) {
        return restResult(null, StatusCode.FAILED.code, msg);
    }

    private static <T> Result<T> restResult(T data, int code, String msg) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setData(data);
        result.setMsg(msg);
        return result;
    }
    private static <T> Result<T> restResult(T data, StatusCode statusCode) {
        Result<T> result = new Result<>();
        result.setCode(statusCode.code);
        result.setData(data);
        result.setMsg(statusCode.msg);
        return result;
    }

    public static Result loginFailed() {
        return restResult(null, StatusCode.LOGIN_FAILED);
    }


    private enum StatusCode {
        SUCCESS(1, "操作成功"), FAILED(0, "操作失败"), LOGIN_FAILED(-1, "登录失效");

        private final int code;
        private final String msg;
        StatusCode(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public long getCode() {
            return this.code;
        }

        public String getMsg() {
            return this.msg;
        }
    }
}
