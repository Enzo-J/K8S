package com.wenge.tbase.nacos.controller;

import com.wenge.tbase.nacos.result.RestResult;
import com.wenge.tbase.nacos.service.NacosServiceClusterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;

/**
 * Author: sunlyhm
 * Date: 2020/11/29 15:06
 * FileName: NacosServiceClusterController
 * Description: nacos服务集群配置管理
 */
@Api(tags= "微服务集群配置管理接口")
@RestController
@Slf4j
@RequestMapping("/nacos/v1/ns/cluster")
public class NacosServiceClusterController {
    @Autowired
    private NacosServiceClusterService nacosServiceClusterService;
    @ApiOperation(value = "集群信息更新服务", notes = "集群信息更新服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "serviceName", value = "服务名称", required = true, dataType = "String"),
            @ApiImplicitParam(name = "clusterName", value = "集群名称", required = true, dataType = "String"),
            @ApiImplicitParam(name = "checkPort", value = "检查端口", required = true, dataType = "Int"),
            @ApiImplicitParam(name = "useInstancePort4Check", value = "是否使用IP端口检查", required = true, dataType = "Boolean"),
            @ApiImplicitParam(name = "healthChecker", value = "检查类型（包括tcp,http等）如：{\"type\":\"TCP\"}", required = true, dataType = "String"),
            @ApiImplicitParam(name = "metadata", value = "元数据", required = false, dataType = "String"),
            @ApiImplicitParam(name = "namespaceId", value = "命名空间ID", required = false, dataType = "String")
    })
    @PutMapping
    public RestResult<?> updateServiceClster(
            @Valid @RequestParam String serviceName,
            @Valid @RequestParam String clusterName,
            @Valid @RequestParam(required = false) String namespaceId,
            @Valid @RequestParam Boolean useInstancePort4Check,
            @Valid @RequestParam(required = false) String metadata,
            @Valid @RequestParam  int checkPort,
            @Valid @RequestParam String healthChecker) {
        HashMap<String, String> clusterMap = new HashMap<String, String>();
        clusterMap.put("serviceName", serviceName);
        clusterMap.put("clusterName", clusterName);
        clusterMap.put("useInstancePort4Check", String.valueOf(useInstancePort4Check));
        clusterMap.put("checkPort", String.valueOf(checkPort));
        clusterMap.put("healthChecker", String.valueOf(healthChecker));
        if (namespaceId != null) {
            clusterMap.put("namespaceId", namespaceId);
        }
        if (metadata != null) {
            clusterMap.put("metadata", metadata);
        }
        return nacosServiceClusterService.putClusterService(clusterMap);
    }

}
