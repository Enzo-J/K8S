package com.wenge.tbase.gateway.exception;

import lombok.Getter;

@Getter
public class RpcException extends RuntimeException{
	private static final long serialVersionUID = 1L;	
	
	int errorCode;
	

    public RpcException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public RpcException(String message,int errorCode) {
        super(message);
        this.errorCode=errorCode;
    }
}
