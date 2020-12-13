package com.wenge.tbase.gateway.config;


import java.util.Date;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

@Component
public class MybatisMetaObjectHandler implements MetaObjectHandler {
	private final static String DEFAULT_USERNAME = "system";
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createdBy",DEFAULT_USERNAME,metaObject);
        this.setFieldValByName("createdTime",new Date(),metaObject);
        this.setFieldValByName("updatedTime",new Date(),metaObject);
        this.setFieldValByName("updatedBy",DEFAULT_USERNAME,metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
    	 this.setFieldValByName("updatedBy",DEFAULT_USERNAME,metaObject);
        this.setFieldValByName("updatedTime",new Date(),metaObject);
    }
}


