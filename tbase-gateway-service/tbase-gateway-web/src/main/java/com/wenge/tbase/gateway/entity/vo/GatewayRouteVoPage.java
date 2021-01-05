package com.wenge.tbase.gateway.entity.vo;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="接口返回路由分页集合对象")
public class GatewayRouteVoPage {	
	private List<GatewayRouteVo> gatewayRouteList; 
	@ApiModelProperty(value = "总条数")
	private long total;
	@ApiModelProperty(value = "总分数")
	private long totalPages;
	@ApiModelProperty(value = "当前分页条数")
	private long size;
	@ApiModelProperty(value = "当前分页书")
	private long current;
}
