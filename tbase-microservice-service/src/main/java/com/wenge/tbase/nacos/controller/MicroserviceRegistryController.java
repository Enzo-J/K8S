package com.wenge.tbase.nacos.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wenge.tbase.nacos.entity.MicroserviceRegistry;
import com.wenge.tbase.nacos.result.RestResult;
import com.wenge.tbase.nacos.service.MicroserviceRegistryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
/**
 * Author: sunlyhm
 * Date: 2020/12/8 16:39
 * FileName: RegistryInfoController
 * Description: 注册中心信息的展示
 */
@Api(tags = "微服务注册中心信息模块")
@RestController
@Slf4j
@RequestMapping("/microservice/v1/ns/registry")
public class MicroserviceRegistryController {
    @Autowired
    private MicroserviceRegistryService registryInfoService;
    @ApiOperation(value = "获取注册中心列表", notes = "获取注册中心列表")
    //1、获取注册中心列表
    @GetMapping("/list")
    public RestResult<?> getRegistryList(){
        return RestResult.ok(registryInfoService.list(null));
    }
   //2、添加注册中心记录
   @ApiOperation(value = "添加注册中心记录", notes = "添加注册中心记录")
   @PostMapping("/add")
   public RestResult<?> addRegistry(@RequestBody MicroserviceRegistry microserviceRegistry){
       boolean save = registryInfoService.save(microserviceRegistry);
       if(save){
           return RestResult.ok(save) ;
       }else {
           return RestResult.error(save);
       }
   }
   //3、更新注册中心记录
   @ApiOperation(value = "更新注册中心记录", notes = "更新注册中心记录")
   @PostMapping("/update")
   public RestResult<?> updateRegistry(@RequestBody MicroserviceRegistry microserviceRegistry){
       boolean b = registryInfoService.updateById(microserviceRegistry);
       if(b){
           return RestResult.ok(b) ;
       }else {
           return RestResult.error(b);
       }
   }
   //4、删除注册中心记录
   @ApiOperation(value = "删除注册中心记录", notes = "删除注册中心记录")
   @PostMapping("/delete")
   public RestResult<?> deleteRegistry(@RequestBody MicroserviceRegistry microserviceRegistry){
       boolean b = registryInfoService.remove(new QueryWrapper<>(microserviceRegistry));
       if(b){
           return RestResult.ok(b) ;
       }else {
           return RestResult.error(b);
       }
   }
}

