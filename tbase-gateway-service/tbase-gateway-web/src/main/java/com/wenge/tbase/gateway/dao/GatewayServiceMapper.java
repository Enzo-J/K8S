package com.wenge.tbase.gateway.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wenge.tbase.gateway.entity.po.GatewayService;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dangwei
 * @since 2020-12-17
 */
@Mapper
@Repository
public interface GatewayServiceMapper extends BaseMapper<GatewayService> {
	
	

}
