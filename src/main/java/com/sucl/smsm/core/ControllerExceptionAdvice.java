package com.sucl.smsm.core;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 处理请求内部异常
 * @author sucl
 * @date 2019/4/29
 */
@ControllerAdvice
public class ControllerExceptionAdvice {

    @ExceptionHandler(Exception.class)
    public R handle(Exception e){

        return R.of(e.getMessage());
    }
}
