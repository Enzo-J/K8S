package com.wenge.tbase.commons.result;

import lombok.Getter;

/**
 * @ClassName: ResultCode
 * @Description: 返回code定义
 * @Author: Wang XingPeng
 * @Date: 2020/5/7 16:15
 */
@Getter
public enum ResultCode {

    SUCCESS(200, "操作成功"),

    FAILED(400, "响应失败"),

    SYSYTM_CLIENT_ERROR(401,"客户端请求中的语法错误"),

    NO_PERMISSION(403, "没有权限"),

    NOT_FIND_RESOURCE(404, "无法找到资源"),

    SYSTEM_ERROR(500, "服务器内部错误"),

    VALIDATE_FAILED(1002, "参数校验失败"),

    PARAM_IS_EMPTY(1003, "参数为空"),

    ERROR(5000, "未知错误"),

    FILE_IS_EMPTY(5001, "文件为空");

    private int code;
    private String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
