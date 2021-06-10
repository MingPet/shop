package com.fh.shop.admin.exception;

import com.fh.shop.common.ServerResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public class ControllerExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ServerResponse handleException(Exception e) {
        e.printStackTrace();
        return ServerResponse.error();
    }
}
