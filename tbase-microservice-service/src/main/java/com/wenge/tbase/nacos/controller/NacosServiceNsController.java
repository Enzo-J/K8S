package com.wenge.tbase.nacos.controller;

import java.util.HashMap;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wenge.tbase.nacos.result.RestResult;
import com.wenge.tbase.nacos.service.NacosNsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api("nacos服务系统数据相关接口")
@RestController
@RequestMapping("/nacos/v1/ns")
public class NacosServiceNsController<V> {
	@Autowired
	private NacosNsService nacosNsService;
	@ApiOperation(value = "查看当前集群leader", notes = "查看当前集群leader")
	@GetMapping("/raft/leader")
	public RestResult<?> getLeader() {
		return nacosNsService.getLeader();
	}
	

	
	@ApiOperation(value = "更新实例的健康状态", notes = "删除服务更新实例的健康状态")
	@ApiImplicitParams({ 
	    @ApiImplicitParam(name = "ip", value = "服务实例IP", required = true, dataType = "string"),
		@ApiImplicitParam(name = "port", value = "服务实例port", required = true, dataType = "integer"),
		@ApiImplicitParam(name = "namespaceId", value = "命名空间ID", required = false, dataType = "string"),
		@ApiImplicitParam(name = "clusterName", value = "集群名", required = false, dataType = "string"),
		@ApiImplicitParam(name = "serviceName", value = "服务名", required = true, dataType = "string"),
		@ApiImplicitParam(name = "groupName", value = "分组名", required = false, dataType = "string"),
		@ApiImplicitParam(name = "healthy", value = "是否健康", required = true, dataType = "boolean") }) // @PostMapping(value
	@PutMapping("/health/instance")
	public RestResult<?> updateHealthInstance(
			@Valid @RequestParam(required = false) String namespaceId,
			@Valid @RequestParam String ip, 
			@Valid @RequestParam Boolean healthy, 
			@Valid @RequestParam int port,
			@Valid @RequestParam String serviceName,
			@Valid @RequestParam(required = false) String clusterName,
			@Valid @RequestParam(required = false) String groupName) {
		HashMap<String, String> instanceMap = new HashMap<String, String>();
		instanceMap.put("ip", ip);
		instanceMap.put("port",String.valueOf(port));
		instanceMap.put("serviceName", serviceName);
		if (namespaceId != null) {
			instanceMap.put("namespaceId", namespaceId);
		}
		if (clusterName != null) {
			instanceMap.put("clusterName", clusterName);
		}
		if (groupName != null) {
			instanceMap.put("groupName", groupName);
		}
		if (healthy != null) {
			instanceMap.put("healthy", String.valueOf(healthy));
		}
		return nacosNsService.updateHealthInstance(instanceMap);
	}
	
	
}
