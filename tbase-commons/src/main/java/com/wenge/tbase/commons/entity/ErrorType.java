package com.wenge.tbase.commons.entity;
import lombok.Getter;

@Getter
public enum ErrorType {

    SUCCESS(1001, "操作成功"),
    
    FAILED(1002, "响应失败"),
	
	SYSTEM_BUSY(2001, "系统繁忙！"),
	SYSTEM_ERROR(2002, "系统内部错误！"),
	/** gateway start*/
	GATEWAY_NOT_FOUND_SERVICE(010404, "服务未找到"),
    GATEWAY_ERROR(010500, "网关异常"),
    GATEWAY_CONNECT_TIME_OUT(010002, "网关超时"),
    ARGUMENT_NOT_VALID(020000, "请求参数校验不通过"),   
    DUPLICATE_PRIMARY_KEY(030000,"唯一键冲突"),
    PARAM_NOT_NULL(030001,"参数不能为空"),
    DB_FAILED(1003,"入库失败"),
    ROUTE_DUPLICATE(1004,"路由冲突，相同服务不能拥有相同的过滤规则")
	 /** gateway end*/
	;
	
    private int code;
    private String msg;

    ErrorType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}