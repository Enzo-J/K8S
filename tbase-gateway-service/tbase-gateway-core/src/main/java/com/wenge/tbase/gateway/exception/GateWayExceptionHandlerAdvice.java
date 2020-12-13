package com.wenge.tbase.gateway.exception;


import io.netty.channel.ConnectTimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import com.wenge.tbase.commons.entity.ErrorType;
import com.wenge.tbase.commons.entity.RestResult;

@Slf4j
@Component
public class GateWayExceptionHandlerAdvice {

    @ExceptionHandler(value = {ResponseStatusException.class})
    public RestResult<?> handle(ResponseStatusException ex) {
        log.error("response status exception:{}", ex.getMessage());
        return RestResult.error(ErrorType.GATEWAY_ERROR.getMsg(),ErrorType.GATEWAY_ERROR.getCode());
    }

    @ExceptionHandler(value = {ConnectTimeoutException.class})
    public RestResult<?> handle(ConnectTimeoutException ex) {
        log.error("connect timeout exception:{}", ex.getMessage());
        return RestResult.error(ErrorType.GATEWAY_CONNECT_TIME_OUT.getMsg(),ErrorType.GATEWAY_ERROR.getCode());
    }

    @ExceptionHandler(value = {NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public RestResult<?> handle(NotFoundException ex) {
        log.error("not found exception:{}", ex.getMessage());
        return RestResult.error(ErrorType.GATEWAY_NOT_FOUND_SERVICE.getMsg(),ErrorType.GATEWAY_NOT_FOUND_SERVICE.getCode());
    }  

    @ExceptionHandler(value = {WengeException.class})
    public RestResult<?> wengeException(WengeException ex) {
        log.error("system exception:{}", ex.getMessage());
        return RestResult.error(ex.getErrorType().getMsg(),ex.getErrorType().getCode());
    }	 

    @ExceptionHandler(value = {Throwable.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestResult<?> handle(Throwable throwable) {
    	RestResult<?> result = RestResult.error(throwable);
        if (throwable instanceof ResponseStatusException) {
            result = handle((ResponseStatusException) throwable);
        } else if (throwable instanceof ConnectTimeoutException) {
            result = handle((ConnectTimeoutException) throwable);
        } else if (throwable instanceof NotFoundException) {
            result = handle((NotFoundException) throwable);
        } else if (throwable instanceof WengeException) {
            result = handle((WengeException) throwable);
        } 
        return result;
    }
}
