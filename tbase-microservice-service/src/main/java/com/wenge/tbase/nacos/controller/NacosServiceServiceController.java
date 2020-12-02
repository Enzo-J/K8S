package com.wenge.tbase.nacos.controller;

import java.util.HashMap;

import javax.validation.Valid;

import com.alibaba.fastjson.JSON;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wenge.tbase.nacos.result.RestResult;
import com.wenge.tbase.nacos.service.NacosServiceService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = "nacos服务注册发现相关接口")
@RestController
@RequestMapping("/nacos/v1/ns/service")
public class NacosServiceServiceController<V> {
	@Autowired
	private NacosServiceService nacosServiceService;
	@ApiOperation(value = "创建服务", notes = "创建服务")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "namespaceId", value = "命名空间ID", required = false, dataType = "String"),
			@ApiImplicitParam(name = "protectThreshold", value = "保护阈值,取值0到1,默认0", required = false, dataType = "Float"),
			@ApiImplicitParam(name = "metadata", value = "元数据", required = false, dataType = "String"),
			@ApiImplicitParam(name = "groupName", value = "分组名", required = false, dataType = "String"),
			@ApiImplicitParam(name = "serviceName", value = "服务名", required = true, dataType = "String"),
			@ApiImplicitParam(name = "selector", value = "访问策略(json格式的字符串)", required = false, dataType = "String")
			})
	@PostMapping
	public RestResult<?> createService(
			@Valid @RequestParam(required = false) String namespaceId,
			@Valid @RequestParam(required = false) Float protectThreshold,
			@Valid @RequestParam(required = false) String metadata,
			@Valid @RequestParam(required = false) String groupName,
			@Valid @RequestParam String serviceName,
			@Valid @RequestParam (required = false) String selector
	) {
		HashMap<String, String> serviceMap = new HashMap<String, String>();
		serviceMap.put("serviceName", serviceName);
		if (namespaceId != null) {
			serviceMap.put("namespaceId", namespaceId);
		}
		if (protectThreshold != null) {
			serviceMap.put("protectThreshold", String.valueOf(protectThreshold));
		}
		if (protectThreshold == null) {
			serviceMap.put("protectThreshold", "0");
		}
		if (groupName != null) {
			serviceMap.put("groupName", groupName);
		}
		if (metadata != null) {
			serviceMap.put("metadata", metadata);
		}
		if (selector != null) {
			serviceMap.put("selector",selector);
		}
		return nacosServiceService.createService(serviceMap);
	}
	
	
	@ApiOperation(value = "删除服务", notes = "删除服务")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "namespaceId", value = "命名空间ID", required = false, dataType = "String"),
		@ApiImplicitParam(name = "groupName", value = "分组名", required = false, dataType = "String"),
		@ApiImplicitParam(name = "serviceName", value = "服务名", required = true, dataType = "String"),
		})
	@DeleteMapping
	public RestResult<?> deleteService(
			@Valid @RequestParam(required = false) String namespaceId,
			@Valid @RequestParam String serviceName,
			@Valid @RequestParam(required = false) String groupName) {
		HashMap<String, String> serviceMap = new HashMap<String, String>();
		serviceMap.put("serviceName", serviceName);
		if (namespaceId != null) {
			serviceMap.put("namespaceId", namespaceId);
		}
		if (groupName != null) {
			serviceMap.put("groupName", groupName);
		}
		return nacosServiceService.deleteService(serviceMap);
	}
	@ApiOperation(value = "修改服务", notes = "修改服务")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "namespaceId", value = "命名空间ID", required = false, dataType = "String"),
		@ApiImplicitParam(name = "protectThreshold", value = "保护阈值,取值0到1,默认0", required = false, dataType = "Float"),
		@ApiImplicitParam(name = "metadata", value = "元数据", required = false, dataType = "String"),
		@ApiImplicitParam(name = "groupName", value = "分组名", required = false, dataType = "String"),
		@ApiImplicitParam(name = "serviceName", value = "服务名", required = true, dataType = "String"),
		@ApiImplicitParam(name = "selector", value = "访问策略(json格式的字符串)", required = false, dataType = "String")
		})
@PutMapping
public RestResult<?> updateService(
			@Valid @RequestParam(required = false) String namespaceId,
			@Valid @RequestParam(required = false) Float protectThreshold,
			@Valid @RequestParam(required = false) String metadata,
			@Valid @RequestParam(required = false) String groupName,
			@Valid @RequestParam String serviceName,
		@Valid @RequestParam(required = false) String selector)  {
		HashMap<String, String> serviceMap = new HashMap<String, String>();
		serviceMap.put("serviceName", serviceName);
		if (namespaceId != null) {
			serviceMap.put("namespaceId", namespaceId);
		}
		if (protectThreshold != null) {
			serviceMap.put("protectThreshold", String.valueOf(protectThreshold));
		}
		if (groupName != null) {
			serviceMap.put("groupName", groupName);
		}
		if (selector != null) {
			serviceMap.put("selector",selector);
		}
		if (metadata != null) {
			serviceMap.put("metadata", metadata);
		}
		return nacosServiceService.updateService(serviceMap);
	}
	@ApiOperation(value = "获取服务", notes = "获取服务")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "namespaceId", value = "命名空间ID", required = false, dataType = "String"),
		@ApiImplicitParam(name = "groupName", value = "分组名", required = false, dataType = "String"),
		@ApiImplicitParam(name = "serviceName", value = "服务名", required = true, dataType = "String"),
		})
	@GetMapping
	public RestResult<?> getService(
			@Valid @RequestParam(required = false) String namespaceId,
			@Valid @RequestParam String serviceName,
			@Valid @RequestParam(required = false) String groupName) {
		HashMap<String, String> serviceMap = new HashMap<String, String>();
		serviceMap.put("serviceName", serviceName);
		if (namespaceId != null) {
			serviceMap.put("namespaceId", namespaceId);
		}
		if (groupName != null) {
			serviceMap.put("groupName", groupName);
		}
		return nacosServiceService.getService(serviceMap);
	}
	
	@ApiOperation(value = "查询服务列表", notes = "查询服务列表")
	@ApiImplicitParams({ 
			@ApiImplicitParam(name = "pageNo", value = "当前页码", required = true, dataType = "Int"),
			@ApiImplicitParam(name = "pageSize", value = "分页大小", required = true, dataType = "Int"),
			@ApiImplicitParam(name = "hasIpCount", value = "表示空服务是否为隐藏,true：隐藏，false:隐藏", required = true, dataType = "Boolean"),
			@ApiImplicitParam(name = "withInstances", value = "是否返回实例信息，一般默认填充为false", required = true, dataType = "Boolean"),
			@ApiImplicitParam(name = "serviceNameParam", value = "服务名称", required = false, dataType = "String"),
			@ApiImplicitParam(name = "groupNameParam", value = "分组名称", required = false, dataType = "String"),
			@ApiImplicitParam(name = "namespaceId", value = "命名空间ID", required = false, dataType = "String")
			})
	@GetMapping("/serviceList")
	public RestResult<?> updateInstance(
			@Valid @RequestParam Boolean hasIpCount,
			@Valid @RequestParam Boolean withInstances,
			@Valid @RequestParam int pageNo,
			@Valid @RequestParam int pageSize,
			@Valid @RequestParam(required = false) String namespaceId,
			@Valid @RequestParam(required = false) String serviceNameParam,
			@Valid @RequestParam(required = false) String groupNameParam) {
		HashMap<String, String> serviceMap = new HashMap<String, String>();
		serviceMap.put("pageNo", String.valueOf(pageNo));
		serviceMap.put("pageSize", String.valueOf(pageSize));
		serviceMap.put("hasIpCount", String.valueOf(hasIpCount));
		serviceMap.put("withInstances", String.valueOf(withInstances));
		if (namespaceId != null) {
			serviceMap.put("namespaceId", namespaceId);
		}
		if (groupNameParam != null) {
			serviceMap.put("groupNameParam", groupNameParam);
		}
		if (serviceNameParam != null) {
			serviceMap.put("serviceNameParam", serviceNameParam);
		}
		return nacosServiceService.getServiceList(serviceMap);
	}
}
