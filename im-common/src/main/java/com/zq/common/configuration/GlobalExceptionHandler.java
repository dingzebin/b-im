package com.zq.common.configuration;

import com.zq.common.exception.AuthFailureException;
import com.zq.common.exception.BizException;
import com.zq.common.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author dzeb
 * @version 1.0
 * @Description 全局异常处理
 * @createTime 2020/9/14 17:37
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthFailureException.class)
    @ResponseBody
    public Result authFailureException(Exception e) {
        return Result.failed(e.getMessage());
    }

    @ExceptionHandler(BizException.class)
    @ResponseBody
    public Result bizException(Exception e) {
        log.error("业务异常", e);
        return Result.failed(e.getMessage());
    }


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result exceptionHandler(Exception e) {
        log.error("未知异常！", e);
        return Result.failed("未知错误，请稍后重试");
    }
}
