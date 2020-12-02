package com.wenge.tbase.nacos.exception;

public class ResponseDto <T> {
    /**
     * 状态码
     */
    private Integer code;

    /**
     * 状态信息
     */
    private String message;

    /**
     * 错误信息
     */
    private String errorMessage;

    /**
     * 数据
     */
    private T data;

    public ResponseDto(Integer code, String message, T data) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public ResponseDto(WengeException wengeException) {
        this.code = wengeException.getCode();
        this.message = wengeException.getMsg();
        this.errorMessage = wengeException.getDevelopMsg();
    }
}
