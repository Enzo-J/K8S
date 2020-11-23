package com.wenge.tbase.nacos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wenge.tbase.nacos.result.RestResult;
import com.wenge.tbase.nacos.service.NacosCountService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("nacos配置管理接口")
@RestController
@RequestMapping("/nacos/v1/cs/count")
public class NacosServiceCountController {	
    @Autowired
	private NacosCountService nacosCountService;
	@ApiOperation(value = "获取nacos上面应用服务的统计数", notes = "获取nacos上面应用服务的统计数")
	
	//须标明什么情况必须传递tenant
    @GetMapping("/getCounts")
	public RestResult<?> getCounts() {
		return nacosCountService.getCounts();
	}	
	
}
