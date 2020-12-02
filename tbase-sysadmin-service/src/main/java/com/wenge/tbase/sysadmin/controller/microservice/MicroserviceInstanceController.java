package com.wenge.tbase.sysadmin.controller.microservice;
import com.wenge.tbase.commons.entity.RestResult;
import com.wenge.tbase.sysadmin.feign.MicroserviceConfigFeign;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * Author: sunlyhm
 * Date: 2020/11/29 16:32
 * FileName: MicroserviceInstanceController
 * Description: 微服务实例管理
 */
@Api(tags = "微服务模块服务实例管理接口")
@Slf4j
@RestController
@RequestMapping("/microservice/v1/cs/instance")
public class MicroserviceInstanceController {
    @Resource
    private MicroserviceConfigFeign microserviceConfigFeign;
    @ApiOperation(value = "注册实例", notes = "注册实例")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ip", value = "服务实例IP", required = true, dataType = "string"),
            @ApiImplicitParam(name = "port", value = "服务实例port", required = true, dataType = "integer"),
            @ApiImplicitParam(name = "namespaceId", value = "命名空间ID", required = false, dataType = "string"),
            @ApiImplicitParam(name = "weight", value = "权重", required = false, dataType = "double"),
            @ApiImplicitParam(name = "enabled", value = "是否上线", required = false, dataType = "boolean"),
            @ApiImplicitParam(name = "healthy", value = "是否健康", required = false, dataType = "boolean"),
            @ApiImplicitParam(name = "clusterName", value = "集群名", required = false, dataType = "string"),
            @ApiImplicitParam(name = "metadata", value = "扩展信息", required = false, dataType = "string"),
            @ApiImplicitParam(name = "serviceName", value = "服务名", required = true, dataType = "string"),
            @ApiImplicitParam(name = "groupName", value = "分组名", required = false, dataType = "string"),
            @ApiImplicitParam(name = "ephemeral", value = "是否临时实例", required = false, dataType = "boolean") }) // @PostMapping(value
    @PostMapping
    public RestResult<?> registeredInstance(@Valid @RequestParam(required = false) String namespaceId,
                                            @Valid @RequestParam String ip,
                                            @Valid @RequestParam int port,
                                            @Valid @RequestParam String serviceName,
                                            @Valid @RequestParam(required = false) Double weight,
                                            @Valid @RequestParam(required = false) Boolean enabled,
                                            @Valid @RequestParam(required = false) Boolean healthy,
                                            @Valid @RequestParam(required = false) String clusterName,
                                            @Valid @RequestParam(required = false) String metadata,
                                            @Valid @RequestParam(required = false) String groupName,
                                            @Valid @RequestParam(required = false) Boolean ephemeral)
    {
        return microserviceConfigFeign.registeredInstance(namespaceId,ip,port,serviceName,weight,enabled,healthy,clusterName,metadata,groupName,ephemeral);
    }
    @ApiOperation(value = "注销实例", notes = "注销实例")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ip", value = "服务实例IP", required = true, dataType = "string"),
            @ApiImplicitParam(name = "port", value = "服务实例port", required = true, dataType = "integer"),
            @ApiImplicitParam(name = "namespaceId", value = "命名空间ID", required = false, dataType = "string"),
            @ApiImplicitParam(name = "clusterName", value = "集群名", required = false, dataType = "string"),
            @ApiImplicitParam(name = "serviceName", value = "服务名", required = true, dataType = "string"),
            @ApiImplicitParam(name = "groupName", value = "分组名", required = false, dataType = "string"),
            @ApiImplicitParam(name = "ephemeral", value = "是否临时实例", required = false, dataType = "boolean") }) // @PostMapping(value
    @DeleteMapping
    public RestResult<?> deleteInstance(
            @Valid @RequestParam(required = false) String namespaceId,
            @Valid @RequestParam String ip,
            @Valid @RequestParam int port,
            @Valid @RequestParam String serviceName,
            @Valid @RequestParam(required = false) String clusterName,
            @Valid @RequestParam(required = false) String groupName,
            @Valid @RequestParam(required = false) Boolean ephemeral) {
        return microserviceConfigFeign.deleteInstance(namespaceId,ip,port,serviceName,clusterName,groupName,ephemeral);

    }
    @ApiOperation(value = "修改实例", notes = "修改实例")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ip", value = "服务实例IP", required = true, dataType = "string"),
            @ApiImplicitParam(name = "port", value = "服务实例port", required = true, dataType = "integer"),
            @ApiImplicitParam(name = "namespaceId", value = "命名空间ID", required = false, dataType = "string"),
            @ApiImplicitParam(name = "weight", value = "权重,如，1.0，2.0等", required = false, dataType = "double"),
            @ApiImplicitParam(name = "enabled", value = "是否上线（此字段进行服务实例的上线、下线管理配置）", required = false, dataType = "boolean"),
            @ApiImplicitParam(name = "clusterName", value = "集群名", required = false, dataType = "string"),
            @ApiImplicitParam(name = "metadata", value = "扩展信息", required = false, dataType = "string"),
            @ApiImplicitParam(name = "serviceName", value = "服务名", required = true, dataType = "string"),
            @ApiImplicitParam(name = "groupName", value = "分组名", required = false, dataType = "string"),
            @ApiImplicitParam(name = "ephemeral", value = "是否临时实例", required = false, dataType = "boolean") }) // @PostMapping(value
    @PutMapping
    public RestResult<?> updateInstance(@Valid @RequestParam(required = false) String namespaceId,
                                        @Valid @RequestParam String ip,
                                        @Valid @RequestParam int port,
                                        @Valid @RequestParam String serviceName,
                                        @Valid @RequestParam(required = false) Double weight ,
                                        @Valid @RequestParam(required = false) Boolean enabled,
                                        @Valid @RequestParam(required = false) String clusterName,
                                        @Valid @RequestParam(required = false) String metadata,
                                        @Valid @RequestParam(required = false) String groupName,
                                        @Valid @RequestParam(required = false) Boolean ephemeral) {
        return microserviceConfigFeign.updateInstance(namespaceId,ip,port,serviceName,weight,enabled,clusterName,metadata,groupName,ephemeral);
    }

    @ApiOperation(value = "获取实例列表", notes = "获取实例列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "clusters", value = "服务实例port", required = false, dataType = "string"),
            @ApiImplicitParam(name = "namespaceId", value = "命名空间ID", required = false, dataType = "string"),
            @ApiImplicitParam(name = "serviceName", value = "服务名", required = true, dataType = "string"),
            @ApiImplicitParam(name = "groupName", value = "分组名", required = false, dataType = "string"),
            @ApiImplicitParam(name = "healthyOnly", value = "是否只返回健康实例", required = false, dataType = "boolean") }) // @PostMapping(value
    @GetMapping("/list")
    public RestResult<?> getInstanceList(
            @Valid @RequestParam String serviceName,
            @Valid @RequestParam(required = false) String namespaceId,
            @Valid @RequestParam(required = false) String clusterName,
            @Valid @RequestParam(required = false) String groupName,
            @Valid @RequestParam(required = false) Boolean healthyOnly) {

        return microserviceConfigFeign.getInstanceList(serviceName,namespaceId,clusterName,groupName,healthyOnly);

    }
    @ApiOperation(value = "查询实例详情", notes = "查询实例列详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ip", value = "服务实例IP", required = true, dataType = "string"),
            @ApiImplicitParam(name = "port", value = "服务实例port", required = true, dataType = "integer"),
            @ApiImplicitParam(name = "namespaceId", value = "命名空间ID", required = false, dataType = "string"),
            @ApiImplicitParam(name = "healthyOnly", value = "是否只返回健康实例", required = false, dataType = "boolean"),
            @ApiImplicitParam(name = "cluster", value = "集群名称", required = false, dataType = "string"),
            @ApiImplicitParam(name = "serviceName", value = "服务名", required = true, dataType = "string"),
            @ApiImplicitParam(name = "groupName", value = "分组名", required = false, dataType = "string"),
            @ApiImplicitParam(name = "ephemeral", value = "是否临时实例", required = false, dataType = "boolean") }) // @PostMapping(value
    @GetMapping
    public RestResult<?> getInstance(
            @Valid @RequestParam(required = false) String namespaceId,
            @Valid @RequestParam String ip,
            @Valid @RequestParam int port,
            @Valid @RequestParam String serviceName,
            @Valid @RequestParam(required = false) String cluster,
            @Valid @RequestParam(required = false) String groupName,
            @Valid @RequestParam(required = false) Boolean healthyOnly,
            @Valid @RequestParam(required = false) Boolean ephemeral){

        return microserviceConfigFeign.getInstance(namespaceId,ip,port,serviceName,cluster,groupName,ephemeral,healthyOnly);

    }

}
