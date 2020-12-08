package com.wenge.tbase.nacos.Exception;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
public class WengeExceptionHandler {
    @ExceptionHandler({Exception.class})
    public ResponseEntity<ResponseDto> handleException(Exception exception, HttpServletRequest request) {
        final boolean isBizException = exception instanceof WengeException;
        log.error("触发请求:[{}]时系统出现异常，异常类型：{}", request.getRequestURI(), isBizException ? "业务异常" : "系统异常");
        WengeException bizException = (WengeException) exception;
       ResponseDto responseT = new ResponseDto(bizException);
        return new ResponseEntity<>(responseT, HttpStatus.OK);
    }
}
