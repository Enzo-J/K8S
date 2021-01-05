package com.wenge.tbase.gateway.entity.base;

import java.util.Date;

import javax.validation.constraints.Past;

import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wenge.tbase.gateway.entity.po.GatewayRoute;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@ApiModel
@Slf4j
@Data
public class BaseQueryForm<P extends Object> extends BaseForm {
	
	    @JsonFormat(timezone="GMT+8",pattern="yyyy-MM-dd HH:mm:ss")
	    @Past(message = "查询开始时间必须小于当前日期")
	    @ApiModelProperty(value = "查询开始时间 yyyy-MM-dd HH:mm:ss")
	    private Date createdTimeStart;

	    @JsonFormat(timezone="GMT+8",pattern="yyyy-MM-dd HH:mm:ss")
	    @Past(message = "查询结束时间必须小于当前日期")
	    @ApiModelProperty(value = "查询结束时间 yyyy-MM-dd HH:mm:ss")
	    private Date createdTimeEnd;

	    
    /**
     * 分页查询的参数，当前页数
     */
   @ApiModelProperty(value = "分页查询的参数，当前页数，默认1")
    private long current = 1;
    /**
     * 分页查询的参数，当前页面每页显示的数量
     */
   @ApiModelProperty(value = "分页查询的参数，当前页面每页显示的数量，当前页数，默认10")
    private long size = 10;

    /**
     * Form转化为Param
     *
     * @param clazz
     * @return
     */
    public P toParam(Class<P> clazz) {
        P p = BeanUtils.instantiateClass(clazz);
        BeanUtils.copyProperties(this, p);
        return p;
    }

    /**
     * 从form中获取page参数，用于分页查询参数
     *
     * @return
     */
    public Page getPage() {
        return new Page(this.getCurrent(), this.getSize());
    }
    
    public LambdaQueryWrapper<GatewayRoute> build() {
    	LambdaQueryWrapper<GatewayRoute> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ge(null != this.createdTimeStart, GatewayRoute::getCreatedTime, this.createdTimeStart)
                .le(null != this.createdTimeEnd, GatewayRoute::getCreatedTime, this.createdTimeEnd);
        return queryWrapper;
    }

}
