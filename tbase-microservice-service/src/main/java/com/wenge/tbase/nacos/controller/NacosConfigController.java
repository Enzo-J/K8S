package com.wenge.tbase.nacos.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: sunlyhm
 * Date: 2020/11/29 15:06
 * FileName: NacosServiceClusterController
 * Description: nacos服务的配置管理测试类
 */
@RestController
@RefreshScope
public class NacosConfigController {
	//获取配置文件中定义的数值
	@Value("${server.port}")
	private String port;
	@GetMapping("/configs")
	public String getUser() {
	return port;
}
}
