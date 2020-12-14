package com.wenge.tbase.nacos.controller;

import com.wenge.tbase.nacos.result.RestResult;
import com.wenge.tbase.nacos.service.MicroserviceGovernanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: sunlyhm
 * Date: 2020/12/8 21:18
 * FileName: MicroserviceGovernanceController
 * Description: 微服务治理模块
 */
@Api(tags = "微服务治理模块")
@RestController
@Slf4j
@RequestMapping("/microservice/v1/ns/sentinal")
public class MicroserviceGovernanceController {
    @Autowired
    private MicroserviceGovernanceService microserviceGovernanceService;
    //进行sentinal页面的嵌入
    @ApiOperation(value = "返回sentinal登录页面地址", notes = "返回sentinal登录页面地址")
    @GetMapping("/getSentinalPage")
    public RestResult<?> getRegistryList(){
        return microserviceGovernanceService.getRegistryList();
    }
}
