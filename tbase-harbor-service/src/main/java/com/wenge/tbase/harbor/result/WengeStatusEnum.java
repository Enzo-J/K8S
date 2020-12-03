package com.wenge.tbase.harbor.result;
import lombok.Getter;

@Getter
public enum WengeStatusEnum {
    SUCCESS(200, "操作成功"),
	SYSYTM_CLIENT_ERROR(400,"客户端请求中的语法错误"),
	NO_PERMISSION(403, "没有权限"),
	NOT_FIND_RESOURCE(404, "无法找到资源"),
	SYSTEM_ERROR(500, "服务器内部错误"),
	;
    private int code;
    private String msg;
    WengeStatusEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}