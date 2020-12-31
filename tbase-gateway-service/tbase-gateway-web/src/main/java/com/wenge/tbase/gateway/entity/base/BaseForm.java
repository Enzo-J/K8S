package com.wenge.tbase.gateway.entity.base;

import org.springframework.beans.BeanUtils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@ApiModel
@Slf4j
@Data
public class BaseForm<T extends BasePo> {
    /**
     * 用户名
     */
	@ApiModelProperty(value = "租户ID")
    private String tenantId;
    
    

    /**
     * From转化为Po，进行后续业务处理
     *
     * @param clazz
     * @return
     */
    public T toPo(Class<T> clazz) {
        T t = BeanUtils.instantiateClass(clazz);
        BeanUtils.copyProperties(this, t);
        return t;
    }

    /**
     * From转化为Po，进行后续业务处理
     *
     * @param id
     * @param clazz
     * @return
     */
    public T toPo(String id, Class<T> clazz) {
        T t = BeanUtils.instantiateClass(clazz);
        t.setId(id);
        BeanUtils.copyProperties(this, t);
        return t;
    }
}
