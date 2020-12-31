package com.wenge.tbase.gateway.exception;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.wenge.tbase.commons.entity.ErrorType;
import com.wenge.tbase.commons.entity.RestResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class DefaultGlobalExceptionHandlerAdvice {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public RestResult<?> serviceException(MethodArgumentNotValidException ex) {
        log.error("service exception:{}", ex.getMessage());
        return RestResult.error(ErrorType.ARGUMENT_NOT_VALID.getMsg(),ErrorType.ARGUMENT_NOT_VALID.getCode());
    }

    @ExceptionHandler(value = {DuplicateKeyException.class})
    public RestResult<?> duplicateKeyException(DuplicateKeyException ex) {
        log.error("primary key duplication exception:{}", ex.getMessage());
        return RestResult.error(ErrorType.DUPLICATE_PRIMARY_KEY.getMsg(),ErrorType.DUPLICATE_PRIMARY_KEY.getCode());
    }
    
    @ExceptionHandler(value = {WengeException.class})
    public RestResult<?> wengeException(WengeException ex) {
        log.error("system exception:{}", ex.getMessage());
        return RestResult.error(ex.getErrorType().getMsg(),ex.getErrorType().getCode());
    }
    
    @ExceptionHandler(value = {RpcException.class})
    public RestResult<?> rpcException(RpcException ex) {
        log.error("system exception:{}", ex.getMessage());
        return RestResult.error(ex.getMessage(),ex.getErrorCode());
    }
    
    

   
}