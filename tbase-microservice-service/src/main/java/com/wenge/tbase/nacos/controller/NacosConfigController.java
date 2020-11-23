package com.wenge.tbase.nacos.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class NacosConfigController {
	@Value("${server.port}")
	private String port;
	@GetMapping("/configs")
	public String getUser() {
	return port;
}
}
