package com.wenge.tbase.nacos.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.wenge.tbase.nacos.result.RestResult;
import com.wenge.tbase.nacos.result.WengeStatusEnum;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice(basePackages = "com.wenge.tbase.nacos.controller")
public class GlobalExceptionControllerAdvice {
	 @ResponseBody
	    @ExceptionHandler(value= MethodArgumentNotValidException.class)
	    public RestResult<?> handleValidException(MethodArgumentNotValidException e) {
	        log.error("数据校验出现问题{}，异常类型：{}", e.getMessage(), e.getClass());
	        BindingResult bindingResult = e.getBindingResult();
	        Map<String, String> errorMap = new HashMap<>();
	        bindingResult.getFieldErrors().forEach((fieldError)->{
	            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
	        });
	        return RestResult.error(WengeStatusEnum.SYSYTM_CLIENT_ERROR.getMsg(),WengeStatusEnum.SYSYTM_CLIENT_ERROR.getCode());
	    }

	    @ExceptionHandler(value=Throwable.class)
	    public RestResult<?> handleException(Throwable throwable) {
	        log.error("未知异常{}，异常类型：{}", throwable.getMessage(), throwable.getClass());
	        return RestResult.error( WengeStatusEnum.SYSYTM_CLIENT_ERROR.getMsg(),WengeStatusEnum.SYSYTM_CLIENT_ERROR.getCode());
	    }
}
