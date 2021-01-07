package com.wenge.tbase.commons.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: ResultVO
 * @Description: 返回VO定义
 * @Author: Wang XingPeng
 * @Date: 2020/5/7 16:12
 */

@Getter
@Setter
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

    public ResultVO() {
    }
    public ResultVO(T data) {
        this(ResultCode.SUCCESS, data);
    }

    public ResultVO(ResultCode resultCode, T data) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
        this.data = data;
    }

    public static ResultVO success(Object data) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(ResultCode.SUCCESS.getCode());
        resultVO.setData(data);
        return resultVO;
    }

    public static ResultVO fail(String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(ResultCode.FAILED.getCode());
        resultVO.setMsg(msg);
        return resultVO;
    }
}
