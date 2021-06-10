package com.fh.shop.api.exception;

import com.fh.shop.common.ResponseEnum;
import com.fh.shop.common.ServerResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class WebExceptionHandler {

    @ExceptionHandler(ShopException.class)
    public ServerResponse handlerException(ShopException se){
        ResponseEnum responseEnum = se.getResponseEnum();
        return ServerResponse.error(responseEnum);
    }
}
