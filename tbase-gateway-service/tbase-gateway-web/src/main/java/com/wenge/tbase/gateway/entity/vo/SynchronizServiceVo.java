package com.wenge.tbase.gateway.entity.vo;

import java.util.List;

import com.wenge.tbase.gateway.entity.po.GatewayService;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel(value="同步注册中心服务返回结果对象")
public class SynchronizServiceVo {
	
	@ApiModelProperty(value = "同步后新增的服务列表")
	private List<GatewayService> addServiceList;
	
	@ApiModelProperty(value = "同步后覆盖的服务列表")
	private List<GatewayService> overrideServiceList;
	
	@ApiModelProperty(value = "下线服务列表")
	private List<GatewayService> offlineServiceList;
	
	@ApiModelProperty(value = "同步后新增的服务数量")
    private Integer addServiceCount;
	
	@ApiModelProperty(value = "同步后覆盖的服务数量")
    private Integer overrideServiceCount;
	
	@ApiModelProperty(value = "下线服务数量")
    private Integer offlineServiceCount;
	
	public SynchronizServiceVo(List<GatewayService> addList,List<GatewayService> overrideList,List<GatewayService> offlineServiceList,Integer addCount,Integer overrideCount,Integer offline) {
//		overrideServiceList=new LinkedList<Map<String,String>>();
//		addServiceList=new LinkedList<Map<String,String>>();
//		if(!CollectionUtils.isEmpty(overrideList)) {
//			for (String serverName : overrideList) {
//				   Map<String,String> map=new Hashtable<String, String>();
//				   map.put("serverName", serverName);
//				   overrideServiceList.add(map);
//			   }
//		}
//		if(!CollectionUtils.isEmpty(addList)) {			 
//			   for (String serverName : addList) {
//				   Map<String,String> map=new Hashtable<String, String>();
//				   map.put("serverName", serverName);
//				   addServiceList.add(map);
//			   }
//		}
		this.addServiceList=addList;
		this.overrideServiceList=overrideList;
		this.offlineServiceList=offlineServiceList;
		this.addServiceCount=addCount;
		this.overrideServiceCount=overrideCount;
		this.offlineServiceCount=offline;
		
	}
	
}
