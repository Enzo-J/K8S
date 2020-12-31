package com.wenge.tbase.gateway.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wenge.tbase.gateway.entity.base.ServiceIntercept;

@Mapper
@Repository
public interface ServiceInterceptMybatisDAO extends BaseMapper<ServiceIntercept>{  
	
    /**
     * 获取所有放行接口
     * @return
     */
    @Select("select * from gateway_service_intercept where url is not null and url != '' and matching_rules in (0, 1)")
    List<ServiceIntercept> getAllReleaseUrl();
       
}
