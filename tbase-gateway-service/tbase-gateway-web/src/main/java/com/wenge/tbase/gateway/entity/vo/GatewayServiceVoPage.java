package com.wenge.tbase.gateway.entity.vo;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wenge.tbase.gateway.entity.po.GatewayService;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@Data
@ApiModel(value="接口返回服务分页集合对象")
public class GatewayServiceVoPage {
	@ApiModelProperty(value = "返回服务集合")
	private List<GatewayServiceVo> gatewayServiceList=new LinkedList<GatewayServiceVo>(); 
	@ApiModelProperty(value = "总条数")
	private long total;
	@ApiModelProperty(value = "总分数")
	private long totalPages;
	@ApiModelProperty(value = "当前分页条数")
	private long size;
	@ApiModelProperty(value = "当前分页序号")
	private long current;	
	public GatewayServiceVoPage(IPage<GatewayService> page) {
		this.total=page.getTotal();
		this.size=page.getSize();
		this.totalPages=page.getPages();
		this.current=page.getCurrent();
//		page.getRecords().forEach(item ->{
//			this.gatewayServiceList.add(new GatewayServiceVo(item));
//		});
		this.gatewayServiceList=page.getRecords().stream().map(GatewayServiceVo::new).collect(Collectors.toList());
	}

}
