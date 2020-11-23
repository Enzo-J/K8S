package com.wenge.tbase.commons.entity;
import lombok.Getter;

@Getter
public enum ErrorType {

    SUCCESS(1001, "操作成功"),
    
    FAILED(1002, "响应失败"),
	
	SYSTEM_BUSY(2001, "系统繁忙！"),
	SYSTEM_ERROR(2002, "系统内部错误！"),
	;
	
    private int code;
    private String msg;

    ErrorType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}