package com.shuma.web.controller;

import com.king.kong.tzj.util.ApiDataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局捕获异常
 * @author KingKong
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)
    public Object resultError(Exception e){
        StackTraceElement stackTraceElement= e.getStackTrace()[0];
        log.error("出现异常:{},{},{},{}",stackTraceElement.getClassName(),stackTraceElement.getFileName(),stackTraceElement.getMethodName(),stackTraceElement.getLineNumber());
        return ApiDataUtil.errResult(e.getMessage(),null);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Object resultParamError(IllegalArgumentException e){
        log.error("参数异常：{}",e.getMessage());
       return ApiDataUtil.errResult(e.getMessage(),null);
    }

}
