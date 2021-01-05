package com.wenge.tbase.gateway.entity.vo;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.BeanUtils;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wenge.tbase.gateway.entity.po.GatewayService;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="返回服务对象")
public class GatewayServiceVo {

	@ApiModelProperty(value = "服务id")
	@TableId(value = "id", type = IdType.ID_WORKER)
	private String id;

	@ApiModelProperty(value = "服务名称")
	private String serviceName;

	@ApiModelProperty(value = "服务标识名称")
	private String tagName;

	@ApiModelProperty(value = "服务状态：Y-有效，N-无效")
	private String status;

	@ApiModelProperty(value = "服务描述")
	private String description;

	@ApiModelProperty(value = "服务swagger-ui页面地址")
	private String serviceInstanceApiUrl;
	
	@ApiModelProperty(value = "server uri")
	private String serviceUri;
	
    @JsonFormat
	@ApiModelProperty(value = "创建时间")
	private String createdTime;

	@ApiModelProperty(value = "更新时间")
	private String updatedTime;

	@ApiModelProperty(value = "服务类型 HTTP、HTTPS")
	private String serverType;	

	@ApiModelProperty(value = "同步状态：Y-同步，N-未同步")
	private String synchronize;
	
	public GatewayServiceVo(GatewayService gatewayService) {
		
		BeanUtils.copyProperties(gatewayService, this);		
		this.createdTime = DateFormatUtils.format(gatewayService.getCreatedTime(),"yyyy-MM-dd HH:mm:ss");
		
		this.updatedTime = DateFormatUtils.format(gatewayService.getUpdatedTime(),"yyyy-MM-dd HH:mm:ss");
	}

}
