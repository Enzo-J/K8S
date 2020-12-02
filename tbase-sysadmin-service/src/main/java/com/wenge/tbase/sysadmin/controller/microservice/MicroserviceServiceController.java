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
@Api(tags = "微服务模块服务管理相关接口")
@RestController
@Slf4j
@RequestMapping("/microservice/v1/cs/service")
public class MicroserviceServiceController {
    @Resource
    private MicroserviceConfigFeign microserviceConfigFeign;
    @ApiOperation(value = "查询服务列表", notes = "查询服务列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "当前页码", required = true, dataType = "integer"),
            @ApiImplicitParam(name = "pageSize", value = "分页大小", required = true, dataType = "integer"),
            @ApiImplicitParam(name = "hasIpCount", value = "表示服务是否为隐藏,true:表示隐藏，false:表示不隐藏", required = true, dataType = "boolean"),
            @ApiImplicitParam(name = "withInstances", value = "是否返回实例信息，一般默认填充为false", required = true, dataType = "boolean"),
            @ApiImplicitParam(name = "serviceNameParam", value = "服务名称", required = false, dataType = "string"),
            @ApiImplicitParam(name = "groupNameParam", value = "分组名称", required = false, dataType = "string"),
            @ApiImplicitParam(name = "namespaceId", value = "命名空间ID", required = false, dataType = "string")
    }) // @PostMapping(value
    @GetMapping("/serviceList")
    public RestResult<?> getServiceList(
            @Valid @RequestParam Boolean hasIpCount,
            @Valid @RequestParam Boolean withInstances,
            @Valid @RequestParam int pageNo,
            @Valid @RequestParam int pageSize,
            @Valid @RequestParam(required = false) String namespaceId,
            @Valid @RequestParam(required = false) String serviceNameParam,
            @Valid @RequestParam(required = false) String groupNameParam) {
        return microserviceConfigFeign.getServiceList(hasIpCount,withInstances,pageNo,pageSize, namespaceId,serviceNameParam,groupNameParam);
    }

    @ApiOperation(value = "创建服务", notes = "创建服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "namespaceId", value = "命名空间ID", required = false, dataType = "string"),
            @ApiImplicitParam(name = "protectThreshold", value = "保护阈值,取值0到1,默认0", required = false, dataType = "float"),
            @ApiImplicitParam(name = "metadata", value = "元数据", required = false, dataType = "string"),
            @ApiImplicitParam(name = "groupName", value = "分组名", required = false, dataType = "string"),
            @ApiImplicitParam(name = "serviceName", value = "服务名", required = true, dataType = "string"),
            @ApiImplicitParam(name = "selector", value = "访问策略(json格式的字符串)", required = false, dataType = "string")

    })
    @PostMapping
    public RestResult<?> createService(
            @Valid @RequestParam(required = false) String namespaceId,
            @Valid @RequestParam(required = false) Float protectThreshold,
            @Valid @RequestParam(required = false) String metadata,
            @Valid @RequestParam(required = false) String groupName,
            @Valid @RequestParam String serviceName,
            @Valid @RequestParam(required = false) String selector
            ) {
        return microserviceConfigFeign.postService(namespaceId,protectThreshold,metadata,groupName,serviceName,selector);

    }
    @ApiOperation(value = "修改服务", notes = "修改服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "namespaceId", value = "命名空间ID", required = false, dataType = "string"),
            @ApiImplicitParam(name = "protectThreshold", value = "保护阈值,取值0到1,默认0", required = false, dataType = "float"),
            @ApiImplicitParam(name = "metadata", value = "元数据", required = false, dataType = "string"),
            @ApiImplicitParam(name = "groupName", value = "分组名", required = false, dataType = "string"),
            @ApiImplicitParam(name = "serviceName", value = "服务名", required = true, dataType = "string"),
            @ApiImplicitParam(name = "selector", value = "访问策略(json格式的字符串)", required = false, dataType = "string")
    })
    @PutMapping
    public RestResult<?> updateService(
            @Valid @RequestParam(required = false) String namespaceId,
            @Valid @RequestParam(required = false) Float protectThreshold,
            @Valid @RequestParam(required = false) String metadata,
            @Valid @RequestParam(required = false) String groupName,
            @Valid @RequestParam String serviceName,
            @Valid @RequestParam(required = false) String selector){
        return microserviceConfigFeign.updateService(namespaceId,protectThreshold,metadata,groupName,serviceName, selector);

    }

    @ApiOperation(value = "查询服务", notes = "查询服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "namespaceId", value = "命名空间ID", required = false, dataType = "string"),
            @ApiImplicitParam(name = "groupName", value = "分组名", required = false, dataType = "string"),
            @ApiImplicitParam(name = "serviceName", value = "服务名", required = true, dataType = "string"),
    })
    @GetMapping
    public RestResult<?> getService(
            @Valid @RequestParam(required = false) String namespaceId,
            @Valid @RequestParam String serviceName,
            @Valid @RequestParam(required = false) String groupName) {
        return microserviceConfigFeign.getService(namespaceId,serviceName,groupName);
    }


    @ApiOperation(value = "删除服务", notes = "删除服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "namespaceId", value = "命名空间ID(如果是默认空间，可以忽略此字段，不进行字段的传递)", required = false, dataType = "string"),
            @ApiImplicitParam(name = "groupName", value = "分组名", required = false, dataType = "string"),
            @ApiImplicitParam(name = "serviceName", value = "服务名", required = true, dataType = "string"),
    })
    @DeleteMapping
    public RestResult<?> deleteService(
            @Valid @RequestParam(required = false) String namespaceId,
            @Valid @RequestParam String serviceName,
            @Valid @RequestParam(required = false) String groupName){
        return microserviceConfigFeign.deleteService(namespaceId,serviceName,groupName);
    }
    @ApiOperation(value = "集群信息更新服务", notes = "集群信息更新服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "serviceName", value = "服务名称", required = true, dataType = "string"),
            @ApiImplicitParam(name = "clusterName", value = "集群名称", required = true, dataType = "string"),
            @ApiImplicitParam(name = "checkPort", value = "检查端口", required = true, dataType = "integer"),
            @ApiImplicitParam(name = "useInstancePort4Check", value = "是否使用IP端口检查", required = true, dataType = "boolean"),
            @ApiImplicitParam(name = "healthChecker", value = "检查类型（包括tcp,http等）如：{\"type\":\"TCP\"}", required = true, dataType = "string"),
            @ApiImplicitParam(name = "metadata", value = "元数据", required = false, dataType = "string"),
            @ApiImplicitParam(name = "namespaceId", value = "命名空间ID", required = false, dataType = "string")
    })
    @PutMapping("/serviceCluster")
    public RestResult<?> updateServiceCluster(
            @Valid @RequestParam String serviceName,
            @Valid @RequestParam String clusterName,
            @Valid @RequestParam(required = false) String namespaceId,
            @Valid @RequestParam Boolean useInstancePort4Check,
            @Valid @RequestParam(required = false) String metadata,
            @Valid @RequestParam  int checkPort,
            @Valid @RequestParam String healthChecker) {
        return microserviceConfigFeign.updateServiceCluster(serviceName,clusterName,namespaceId,useInstancePort4Check,metadata,checkPort,healthChecker);

    }
}
