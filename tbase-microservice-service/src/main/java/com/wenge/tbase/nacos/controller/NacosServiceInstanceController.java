package com.wenge.tbase.nacos.controller;

import java.util.HashMap;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wenge.tbase.nacos.result.RestResult;
import com.wenge.tbase.nacos.service.NacosInstanceService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api("nacos服务发现服务接口")
@RestController
@RequestMapping("/nacos/v1/ns/instance")
public class NacosServiceInstanceController<V> {
	@Autowired
	private NacosInstanceService nacosInstanceService;
	@ApiOperation(value = "注册实例", notes = "注册实例")
	@ApiImplicitParams({ @ApiImplicitParam(name = "ip", value = "服务实例IP", required = true, dataType = "string"),
			@ApiImplicitParam(name = "port", value = "服务实例port", required = true, dataType = "integer"),
			@ApiImplicitParam(name = "namespaceId", value = "命名空间ID", required = false, dataType = "string"),
			@ApiImplicitParam(name = "weight", value = "权重", required = false, dataType = "double"),
			@ApiImplicitParam(name = "enabled", value = "是否上线", required = false, dataType = "boolean"),
			@ApiImplicitParam(name = "healthy", value = "是否健康", required = false, dataType = "boolean"),
			@ApiImplicitParam(name = "clusterName", value = "集群名", required = false, dataType = "string"),
			@ApiImplicitParam(name = "metadata", value = "扩展信息", required = false, dataType = "string"),
			@ApiImplicitParam(name = "serviceName", value = "服务名", required = true, dataType = "string"),
			@ApiImplicitParam(name = "groupName", value = "分组名", required = false, dataType = "string"),
			@ApiImplicitParam(name = "ephemeral", value = "是否临时实例", required = false, dataType = "boolean") }) // @PostMapping(value
	@PostMapping
	public RestResult<?> registeredInstance(@Valid @RequestParam(required = false) String namespaceId,
			@Valid @RequestParam String ip, @Valid @RequestParam int port, @Valid @RequestParam String serviceName,
			@Valid @RequestParam(required = false) Double weight,
			@Valid @RequestParam(required = false) Boolean enabled,
			@Valid @RequestParam(required = false) Boolean healthy,
			@Valid @RequestParam(required = false) String clusterName,
			@Valid @RequestParam(required = false) String metadata,
			@Valid @RequestParam(required = false) String groupName,
			@Valid @RequestParam(required = false) Boolean ephemeral) {
		HashMap<String, String> instanceMap = new HashMap<String, String>();
		instanceMap.put("ip", ip);
		instanceMap.put("port",String.valueOf(port));
		instanceMap.put("serviceName", serviceName);
		if (namespaceId != null) {
			instanceMap.put("namespaceId", namespaceId);
		}
		if (weight != null) {
			instanceMap.put("weight", String.valueOf(weight));
		}
		if (enabled != null) {
			instanceMap.put("enabled", String.valueOf(enabled));
		}
		if (healthy != null) {
			instanceMap.put("healthy", String.valueOf(healthy));
		}
		if (clusterName != null) {
			instanceMap.put("clusterName", clusterName);
		}
		if (groupName != null) {
			instanceMap.put("groupName", groupName);
		}
		if (ephemeral != null) {
			instanceMap.put("ephemeral", String.valueOf(ephemeral));
		}
		return nacosInstanceService.registeredInstance(instanceMap);
	}
	
	
	@ApiOperation(value = "注销实例", notes = "注销实例")
	@ApiImplicitParams({ @ApiImplicitParam(name = "ip", value = "服务实例IP", required = true, dataType = "string"),
			@ApiImplicitParam(name = "port", value = "服务实例port", required = true, dataType = "integer"),
			@ApiImplicitParam(name = "namespaceId", value = "命名空间ID", required = false, dataType = "string"),
			@ApiImplicitParam(name = "clusterName", value = "集群名", required = false, dataType = "string"),
			@ApiImplicitParam(name = "serviceName", value = "服务名", required = true, dataType = "string"),
			@ApiImplicitParam(name = "groupName", value = "分组名", required = false, dataType = "string"),
			@ApiImplicitParam(name = "ephemeral", value = "是否临时实例", required = false, dataType = "boolean") }) // @PostMapping(value
	@DeleteMapping
	public RestResult<?> deleteInstance(
			@Valid @RequestParam(required = false) String namespaceId,
			@Valid @RequestParam String ip,
			@Valid @RequestParam int port,
			@Valid @RequestParam String serviceName,
			@Valid @RequestParam(required = false) String clusterName,
			@Valid @RequestParam(required = false) String groupName,
			@Valid @RequestParam(required = false) Boolean ephemeral) {
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
		if (ephemeral != null) {
			instanceMap.put("ephemeral", String.valueOf(ephemeral));
		}
		return nacosInstanceService.deleteInstance(instanceMap);
	}
	
	@ApiOperation(value = "修改实例", notes = "修改实例")
	@ApiImplicitParams({ 
		    @ApiImplicitParam(name = "ip", value = "服务实例IP", required = true, dataType = "string"),
			@ApiImplicitParam(name = "port", value = "服务实例port", required = true, dataType = "integer"),
			@ApiImplicitParam(name = "namespaceId", value = "命名空间ID", required = false, dataType = "string"),
			@ApiImplicitParam(name = "weight", value = "权重", required = false, dataType = "double"),
			@ApiImplicitParam(name = "enabled", value = "是否上线", required = false, dataType = "boolean"),
			@ApiImplicitParam(name = "clusterName", value = "集群名", required = false, dataType = "string"),
			@ApiImplicitParam(name = "metadata", value = "扩展信息", required = false, dataType = "string"),
			@ApiImplicitParam(name = "serviceName", value = "服务名", required = true, dataType = "string"),
			@ApiImplicitParam(name = "groupName", value = "分组名", required = false, dataType = "string"),
			@ApiImplicitParam(name = "ephemeral", value = "是否临时实例", required = false, dataType = "boolean") }) // @PostMapping(value
	@PutMapping
	public RestResult<?> updateInstance(@Valid @RequestParam(required = false) String namespaceId,
			@Valid @RequestParam String ip, @Valid @RequestParam int port, @Valid @RequestParam String serviceName,
			@Valid @RequestParam(required = false) Double weight,
			@Valid @RequestParam(required = false) Boolean enabled,
			@Valid @RequestParam(required = false) String clusterName,
			@Valid @RequestParam(required = false) String metadata,
			@Valid @RequestParam(required = false) String groupName,
			@Valid @RequestParam(required = false) Boolean ephemeral) {
		HashMap<String, String> instanceMap = new HashMap<String, String>();
		instanceMap.put("ip", ip);
		instanceMap.put("port",String.valueOf(port));
		instanceMap.put("serviceName", serviceName);
		if (namespaceId != null) {
			instanceMap.put("namespaceId", namespaceId);
		}
		if (weight != null) {
			instanceMap.put("weight", String.valueOf(weight));
		}
		if (enabled != null) {
			instanceMap.put("enabled", String.valueOf(enabled));
		}
		if (clusterName != null) {
			instanceMap.put("clusterName", clusterName);
		}
		if (groupName != null) {
			instanceMap.put("groupName", groupName);
		}
		if (ephemeral != null) {
			instanceMap.put("ephemeral", String.valueOf(ephemeral));
		}
		return nacosInstanceService.getInstanceList(instanceMap);
	}
	@ApiOperation(value = "获取实例列表", notes = "获取实例列表")
	@ApiImplicitParams({ 
			@ApiImplicitParam(name = "clusters", value = "服务实例port", required = false, dataType = "integer"),
			@ApiImplicitParam(name = "namespaceId", value = "命名空间ID", required = false, dataType = "string"),
			@ApiImplicitParam(name = "serviceName", value = "服务名", required = true, dataType = "string"),
			@ApiImplicitParam(name = "groupName", value = "分组名", required = false, dataType = "string"),
			@ApiImplicitParam(name = "healthyOnly", value = "是否只返回健康实例", required = false, dataType = "boolean") }) // @PostMapping(value
	@GetMapping("/list")
	public RestResult<?> getInstanceList(
			@Valid @RequestParam String serviceName,
			@Valid @RequestParam(required = false) String namespaceId,
			@Valid @RequestParam(required = false) String clusterName,
			@Valid @RequestParam(required = false) String groupName,
			@Valid @RequestParam(required = false) Boolean healthyOnly) {
		HashMap<String, String> instanceMap = new HashMap<String, String>();
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
		if (healthyOnly != null) {
			instanceMap.put("healthyOnly", String.valueOf(healthyOnly));
		}
		return nacosInstanceService.getInstanceList(instanceMap);
	}
	
	@ApiOperation(value = "查询实例详情", notes = "查询实例列详情")
	@ApiImplicitParams({ 
		 @ApiImplicitParam(name = "ip", value = "服务实例IP", required = true, dataType = "string"),
			@ApiImplicitParam(name = "port", value = "服务实例port", required = true, dataType = "integer"),
			@ApiImplicitParam(name = "namespaceId", value = "命名空间ID", required = false, dataType = "string"),
			@ApiImplicitParam(name = "healthyOnly", value = "是否只返回健康实例", required = false, dataType = "boolean"),
			@ApiImplicitParam(name = "cluster", value = "集群名称", required = false, dataType = "string"),
			@ApiImplicitParam(name = "serviceName", value = "服务名", required = true, dataType = "string"),
			@ApiImplicitParam(name = "groupName", value = "分组名", required = false, dataType = "string"),
			@ApiImplicitParam(name = "ephemeral", value = "是否临时实例", required = false, dataType = "boolean") }) // @PostMapping(value
	@GetMapping
	public RestResult<?> updateInstance(
			@Valid @RequestParam(required = false) String namespaceId,
			@Valid @RequestParam String ip, 
			@Valid @RequestParam int port,
			@Valid @RequestParam String serviceName,
			@Valid @RequestParam(required = false) String cluster,
			@Valid @RequestParam(required = false) String groupName,
			@Valid @RequestParam(required = false) Boolean ephemeral) {
		HashMap<String, String> instanceMap = new HashMap<String, String>();
		instanceMap.put("serviceName", serviceName);
		if (namespaceId != null) {
			instanceMap.put("namespaceId", namespaceId);
		}
		if (cluster != null) {
			instanceMap.put("cluster", cluster);
		}
		if (groupName != null) {
			instanceMap.put("groupName", groupName);
		}
		return nacosInstanceService.getInstance(instanceMap);
	}
	
	@ApiOperation(value = "发送实例心跳", notes = "发送实例心跳")
	@ApiImplicitParams({ 
			@ApiImplicitParam(name = "beat", value = "实例心跳内容(JSON格式字符串)", required = false, dataType = "string"),
			@ApiImplicitParam(name = "serviceName", value = "服务名", required = true, dataType = "string"),
			@ApiImplicitParam(name = "groupName", value = "分组名", required = false, dataType = "string"),
			@ApiImplicitParam(name = "ephemeral", value = "是否临时实例", required = false, dataType = "boolean") }) // @PostMapping(value
	@PutMapping("/beat")
	public RestResult<?> putBeatInstance(
			@Valid @RequestParam(required = false) String beat,
			@Valid @RequestParam String serviceName,
			@Valid @RequestParam(required = false) String groupName,
			@Valid @RequestParam(required = false) Boolean ephemeral) {
		HashMap<String, String> instanceMap = new HashMap<String, String>();
		instanceMap.put("serviceName", serviceName);
		if (beat != null) {
			instanceMap.put("beat", beat);
		}
		if (groupName != null) {
			instanceMap.put("groupName", groupName);
		}
		if (ephemeral != null) {
			instanceMap.put("ephemeral", String.valueOf(ephemeral));
		}
		return nacosInstanceService.putInstanceBeat(instanceMap);
	}
	
	@ApiOperation(value = "批量更新实例元数据(Beta)", notes = "批量更新实例元数据(Beta)")
	@ApiImplicitParams({ 
			@ApiImplicitParam(name = "namespaceId", value = "命名空间ID", required = true, dataType = "string"),
			@ApiImplicitParam(name = "serviceName", value = "服务名", required = true, dataType = "string"),
			@ApiImplicitParam(name = "consistencyType", value = "实例的类型(ephemeral/persist)", required = false, dataType = "string"),
			@ApiImplicitParam(name = "metadata", value = "元数据信息(JSON格式字符串)", required = true, dataType = "string"),
			@ApiImplicitParam(name = "instances", value = "需要更新的实例(JSON格式字符串)", required = false, dataType = "string") }) // @PostMapping(value
	@PutMapping("/metadata/batch")
	public RestResult<?> putBatchMetadata(
			@Valid @RequestParam String metadata,
			@Valid @RequestParam String serviceName,
			@Valid @RequestParam String namespaceId,
			@Valid @RequestParam(required = false) String instances,
			@Valid @RequestParam(required = false) String consistencyType) {
		HashMap<String, String> instanceMap = new HashMap<String, String>();
		instanceMap.put("serviceName", serviceName);
		instanceMap.put("metadata", metadata);
		instanceMap.put("namespaceId", namespaceId);
		if (instances != null) {
			instanceMap.put("instances", instances);
		}
		if (consistencyType != null) {
			instanceMap.put("consistencyType", consistencyType);
		}
		return nacosInstanceService.putBatchMetadata(instanceMap);
	}
	
	@ApiOperation(value = "批量删除实例元数据(Beta)", notes = "批量删除实例元数据(Beta)")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "namespaceId", value = "命名空间ID", required = true, dataType = "string"),
		@ApiImplicitParam(name = "consistencyType", value = "实例的类型(ephemeral/persist)", required = false, dataType = "string"),
		@ApiImplicitParam(name = "serviceName", value = "服务名", required = true, dataType = "string"),
		@ApiImplicitParam(name = "instances", value = "需要更新的实例(JSON格式字符串)", required = false, dataType = "string"),
		@ApiImplicitParam(name = "metadata", value = "元数据信息", required = true, dataType = "string") }) // @PostMapping(value
	@DeleteMapping("/metadata/batch")
	public RestResult<?> deleteBatchMetadata(
			@Valid @RequestParam String namespaceId,
			@Valid @RequestParam String metadata, 
			@Valid @RequestParam String serviceName,
			@Valid @RequestParam(required = false) String consistencyType,
			@Valid @RequestParam(required = false) String instances) {
		HashMap<String, String> instanceMap = new HashMap<String, String>();
		instanceMap.put("namespaceId", namespaceId);
		instanceMap.put("metadata",String.valueOf(metadata));
		instanceMap.put("serviceName", serviceName);
		if (consistencyType != null) {
			instanceMap.put("consistencyType", consistencyType);
		}
		if (instances != null) {
			instanceMap.put("instances", instances);
		}
		return nacosInstanceService.deleteBatchMetadata(instanceMap);
	}

}
