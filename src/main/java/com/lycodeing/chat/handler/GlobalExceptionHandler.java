package com.lycodeing.chat.handler;

import com.lycodeing.chat.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author xiaotianyu
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception ex) {
        log.error("Exception:", ex);
        return Result.error("系统异常");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Result handleCustomException(IllegalArgumentException ex) {
        return Result.failure(ex.getMessage());
    }
}
