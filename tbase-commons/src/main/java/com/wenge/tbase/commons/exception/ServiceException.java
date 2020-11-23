package com.wenge.tbase.commons.exception;

import com.wenge.tbase.commons.entity.ErrorType;
import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException {
  
	private static final long serialVersionUID = 1L;
	/**
     * 异常对应的错误类型
     */
    private final ErrorType errorType;

    /**
     * 默认是系统异常
     */
    public ServiceException() {
        this.errorType = ErrorType.SYSTEM_ERROR;
    }

    public ServiceException(ErrorType errorType) {
        this.errorType = errorType;
    }

    public ServiceException(ErrorType errorType, String message) {
        super(message);
        this.errorType = errorType;
    }

    public ServiceException(ErrorType errorType, String message, Throwable cause) {
        super(message, cause);
        this.errorType = errorType;
    }
}
