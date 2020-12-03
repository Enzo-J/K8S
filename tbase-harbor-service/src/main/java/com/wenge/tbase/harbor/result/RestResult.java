package com.wenge.tbase.harbor.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "接口统一返回对象", description = "")
public class RestResult<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "返回状态码")
	public int status = WengeStatusEnum.SUCCESS.getCode();
	@ApiModelProperty(value = "返回信息")
    public T data ;
    
    public static <T> RestResult<T> ok() {
        RestResult<T> restResult = new RestResult<>();
        return restResult;
    }

    public static <T> RestResult<T> ok(T data) {
        RestResult<T> restResult = new RestResult<>();
        restResult.data = data;
        return restResult;
    }

    public static <T> RestResult<T> error(T data) {
        RestResult<T> restResult = new RestResult<>();
        restResult.status = WengeStatusEnum.NOT_FIND_RESOURCE.getCode();
        restResult.data = data;
        return restResult;
    }
    
    public static <T> RestResult<T> error(T data, int code) {
        RestResult<T> restResult = new RestResult<>();
        restResult.status = code;
        restResult.data = data;
        return restResult;
    }
    
}
