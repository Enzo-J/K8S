package com.wenge.tbase.harbor.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * @Author: chf
 * @ClassName: ResultObject
 * @Description: todo
 * @Date: 2020/11/27
 */

@Setter
@Getter
public class ResultObject {

    private  Map<String, Object> data;
    private  Integer code;
    private  String msg;
    private Map<String, Object> map;



    public ResultObject(){


    }

    public ResultObject(Integer code,String msg,Map<String, Object> map){
        this.code = code;
        this.msg = msg;
        this.map = map;

    }


    public static ResultObject error(String string){
        ResultObject resultObject = new ResultObject();
        resultObject.setMsg(string);
        return resultObject;
    }
}
