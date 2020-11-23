package com.wenge.tbase.commons.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName: ResultVO
 * @Description: 返回VO定义
 * @Author: Wang XingPeng
 * @Date: 2020/5/7 16:12
 */

@Getter
@AllArgsConstructor
public class ResultVO<T> {
    /**
     * 状态码，比如1000代表响应成功
     */
    private int code;
    /**
     * 响应信息，用来说明响应情况
     */
    private String msg;
    /**
     * 响应的具体数据
     */
    private T data;

    public ResultVO(T data) {
        this(ResultCode.SUCCESS, data);
    }

    public ResultVO(ResultCode resultCode, T data) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
        this.data = data;
    }
}
