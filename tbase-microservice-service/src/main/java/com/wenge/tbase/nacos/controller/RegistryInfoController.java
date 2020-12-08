package com.wenge.tbase.nacos.controller;

import com.wenge.tbase.nacos.result.RestResult;
import com.wenge.tbase.nacos.service.RegistryInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Author: sunlyhm
 * Date: 2020/12/8 16:39
 * FileName: RegistryInfoController
 * Description: 注册中心信息的展示
 */
@Api(tags = "微服务注册中心信息显示模块")
@RestController
@Slf4j
@RequestMapping("/microservice/v1/ns/registry")
public class RegistryInfoController {
@Autowired
    private RegistryInfoService registryInfoService;
    @ApiOperation(value = "获取nacos上面应用服务的统计数", notes = "获取nacos上面应用服务的统计数")

    //须标明什么情况必须传递tenant
    @GetMapping
    public RestResult<?> getRegistry(){
        return registryInfoService.getRegistry();

    }

}
