package com.wenge.tbase.commons.entity;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "接口统一返回对象", description = "")
public class RestResult<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "返回状态码")
	public int status = ErrorType.SUCCESS.getCode();
	@ApiModelProperty(value = "返回信息")
    public T msg ;
    
    public static <T> RestResult<T> ok() {
        RestResult<T> restResult = new RestResult<>();
        return restResult;
    }

    public static <T> RestResult<T> ok(T data) {
        RestResult<T> restResult = new RestResult<>();
        restResult.msg = data;
        return restResult;
    }

    public static <T> RestResult<T> error(T data) {
        RestResult<T> restResult = new RestResult<>();
        restResult.status = ErrorType.FAILED.getCode();
        restResult.msg = data;
        return restResult;
    }
    
    public static <T> RestResult<T> error(T data,int code) {
        RestResult<T> restResult = new RestResult<>();
        restResult.status = code;
        restResult.msg = data;
        return restResult;
    }
    
}
