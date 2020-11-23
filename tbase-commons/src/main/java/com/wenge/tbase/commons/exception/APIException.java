package com.wenge.tbase.commons.exception;

import lombok.Getter;

/**
 * @ClassName: APIException
 * @Description: 异常处理API
 * @Author: Wang XingPeng
 * @Date: 2020/5/7 16:10
 * 只要getter方法，无需setter
 */
@Getter
public class APIException extends RuntimeException {
    private int code;
    private String msg;

    public APIException() {
        this(400, "接口错误");
    }

    public APIException(String msg) {
        this(1001, msg);
    }

    public APIException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
}
