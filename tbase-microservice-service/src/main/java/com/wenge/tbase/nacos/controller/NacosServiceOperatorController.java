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
import com.wenge.tbase.nacos.service.NacosOperatorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api("nacos服务操作相关的接口")
@RestController
@RequestMapping("/nacos/v1/ns/operator")
public class NacosServiceOperatorController<V> {
	@Autowired
	private NacosOperatorService nacosOperatorService;
	@ApiOperation(value = "查询系统开关", notes = "查询系统开关")
	@GetMapping("/switches")
	public RestResult<?> getSwitches() {
		return nacosOperatorService.getSwitches();
	}
	
	@ApiOperation(value = "修改系统开关", notes = "修改系统开关")
	@ApiImplicitParams({ @ApiImplicitParam(name = "entry", value = "命名空间ID", required = true, dataType = "string"),
			@ApiImplicitParam(name = "value", value = "开关值", required = true, dataType = "string"),
			@ApiImplicitParam(name = "debug", value = "是否只在本机生效,true表示本机生效,false表示集群生效", required = false, dataType = "boolean") })
	@PutMapping("/switches")
	public RestResult<?> putSwitches(@Valid @RequestParam String entry, @Valid @RequestParam String value,
			@Valid @RequestParam(required = false) Boolean debug) {
		HashMap<String, String> serviceMap = new HashMap<String, String>();
		serviceMap.put("entry", entry);
		serviceMap.put("value", value);
		if (debug != null) {
			serviceMap.put("debug", String.valueOf(debug));
		}
		return nacosOperatorService.putSwitches(serviceMap);
	}
	@ApiOperation(value = "查看系统当前数据指标", notes = "查看系统当前数据指标")
	@GetMapping("/metrics")
	public RestResult<?> getMetrics() {
		return nacosOperatorService.getMetrics();
	}
	@ApiOperation(value = "查看当前集群Server列表", notes = "查看当前集群Server列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "healthy", value = "是否只返回健康Server节点", required = false, dataType = "boolean"), })
	@GetMapping("/servers")
	public RestResult<?> getService(@Valid @RequestParam(required = false) Boolean healthy) {
		HashMap<String, String> serviceMap = new HashMap<String, String>();
		if (healthy != null) {
			serviceMap.put("healthy", String.valueOf(healthy));
		}
		return nacosOperatorService.getServices(serviceMap);
	}
}
