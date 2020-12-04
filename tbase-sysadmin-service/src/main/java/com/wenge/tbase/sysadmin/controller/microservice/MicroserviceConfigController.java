package com.wenge.tbase.sysadmin.controller.microservice;

import com.wenge.tbase.commons.entity.RestResult;
import com.wenge.tbase.sysadmin.feign.MicroserviceConfigFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * Author: sunlyhm
 * Date: 2020/11/27 16:40
 * FileName: MicroserviceConfigController
 * Description: 微服务配置管理controller
 */
@Api(tags = "微服务模块配置管理接口")
@RestController
@Slf4j
@RequestMapping("/microservice/v1/cs/configs")
public class MicroserviceConfigController {
    @Resource
    private MicroserviceConfigFeign microserviceConfigFeign;
    @ApiOperation(value = "获取微服务的配置列表", notes = "获取微服务的配置列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "分页页码", required = true, dataType = "integer"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示数量", required = true, dataType = "integer"),
            @ApiImplicitParam(name = "dataId", value = "配置ID，如若没有对应值，可传递空值", required = true, dataType = "string"),
            @ApiImplicitParam(name = "group", value = "配置分组，如若没有对应值，可传递空值", required = true, dataType = "string"),
            @ApiImplicitParam(name = "appName", value = "所属应用，如若没有对应值，可传递空值", required = true, dataType = "string"),
            @ApiImplicitParam(name = "config_tags", value = "配置标签，如若没有对应值，可传递空值", required = true, dataType = "string"),
            @ApiImplicitParam(name = "tenant", value = "租户信息，对应Nacos的命名空间ID字段", required = false, dataType = "string")
    })
    //tenant租户信息需要和项目紧密结合如：某个项目下的某个模块对应的租户信息
    @GetMapping("/configList")
    public RestResult<?> obtainConfigLists(@Valid @RequestParam int pageNo,
                                           @Valid @RequestParam int pageSize,
                                           @Valid @RequestParam String dataId,
                                           @Valid @RequestParam String group,
                                           @Valid @RequestParam String appName,
                                           @Valid @RequestParam String config_tags,
                                           @Valid @RequestParam(required = false) String tenant) {
        return microserviceConfigFeign.obtainConfigLists(pageNo,pageSize,dataId,group,appName,config_tags,tenant);
    }

    @ApiOperation(value = "获取nacos配置详情信息", notes = "获取nacos配置详情信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tenant", value = "租户信息，对应Nacos的命名空间ID字段(此字段若有对应信息，必须进行传递)", required = false, dataType = "string"),
            @ApiImplicitParam(name = "dataId", value = "配置ID", required = true, dataType = "string"),
            @ApiImplicitParam(name = "group", value = "配置分组", required = true, dataType = "string"),
            @ApiImplicitParam(name = "show", value = "展示详细配置请填充为all", required = true, dataType = "string"),
            @ApiImplicitParam(name = "namespaceId", value = "命名空间", required = false, dataType = "string")
    })
    //须标明什么情况必须传递tenant
    @GetMapping("/configDetail")
    public RestResult<?> obtainConfig(@Valid @RequestParam(required = false) String tenant,
                                      @Valid @RequestParam(required = false) String namespaceId,
                                      @Valid @RequestParam String dataId,
                                      @Valid @RequestParam String group,
                                      @Valid @RequestParam String show) {
        return microserviceConfigFeign.obtainConfig(tenant,namespaceId,dataId,group,show);
    }

    @ApiOperation(value = "创建发布nacos配置", notes = "创建发布nacos配置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tenant", value = "租户信息，对应 Nacos 的命名空间ID字段", required = false, dataType = "string"),
            @ApiImplicitParam(name = "dataId", value = "配置ID", required = true, dataType = "string"),
            @ApiImplicitParam(name = "content", value = "配置内容", required = true, dataType = "string"),
            @ApiImplicitParam(name = "group", value = "配置分组", required = true, dataType = "string"),
            @ApiImplicitParam(name = "type", value = "配置类型", required = false, dataType = "string"),
            @ApiImplicitParam(name = "namespaceId", value = "命名空间", required = false, dataType = "string"),
            @ApiImplicitParam(name = "desc", value = "配置描述", required = false, dataType = "string"),
            @ApiImplicitParam(name = "config_tags", value = "配置标签", required = false, dataType = "string"),
            @ApiImplicitParam(name = "appName", value = "所属应用", required = false, dataType = "string")
    })
    @PostMapping
    public RestResult<?> releaseConfigs(@Valid @RequestParam(required = false) String tenant,
                                        @Valid @RequestParam String dataId,
                                        @Valid @RequestParam String group,
                                        @Valid @RequestParam String content,
                                        @Valid @RequestParam(required = false) String type,
                                        @Valid @RequestParam(required = false) String namespaceId,
                                        @Valid @RequestParam(required = false) String desc,
                                        @Valid @RequestParam(required = false) String config_tags,
                                        @Valid @RequestParam(required = false) String appName
    ) {

        return microserviceConfigFeign.releaseConfigs(tenant,dataId,group,content,type,namespaceId,desc,config_tags,appName);
    }

    @ApiOperation(value = "删除nacos配置", notes = "删除nacos配置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tenant", value = "租户信息，对应 Nacos 的命名空间ID字段", required = false, dataType = "string"),
            @ApiImplicitParam(name = "dataId", value = "配置ID", required = true, dataType = "string"),
            @ApiImplicitParam(name = "group", value = "配置分组", required = true, dataType = "string")
    })
    @DeleteMapping
    public RestResult<?> deleteConfigs(@Valid @RequestParam(required = false) String tenant,@Valid @RequestParam String dataId,@Valid @RequestParam String group) {

        return microserviceConfigFeign.deleteConfigs(tenant,dataId,group);
    }



}
