package com.wenge.tbase.sysadmin.feign;

import com.wenge.tbase.commons.entity.RestResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/**
 * Author: sunlyhm
 * Date: 2020/11/27 17:33
 * FileName: MicroserviceConfigFeign
 * Description: 微服务相关的管理feign
 */
@Component
@FeignClient("tbase-microservice-service")
@RequestMapping("/nacos/v1")
public interface MicroserviceConfigFeign {
    @GetMapping("/cs/configs/configDetail")
    RestResult<?> obtainConfig(@RequestParam("tenant") String tenant,
                               @RequestParam("namespaceId") String namespaceId,
                               @RequestParam ("dataId")String dataId,
                               @RequestParam ("group")String group,
                               @RequestParam("show") String show);
    @DeleteMapping("/cs/configs")
    RestResult<?> deleteConfigs(@RequestParam("tenant") String tenant, @RequestParam("dataId") String dataId, @RequestParam("group") String group);
    @PostMapping("/cs/configs")
    RestResult<?> releaseConfigs(@RequestParam("tenant") String tenant,
                                  @RequestParam("dataId") String dataId,
                                  @RequestParam("group") String group,
                                  @RequestParam ("content") String content,
                                  @RequestParam("type") String type,
                                  @RequestParam("namespaceId") String namespaceId,
                                  @RequestParam("desc") String desc,
                                  @RequestParam("config_tags") String config_tags,
                                  @RequestParam("appName") String appName
    );
    @GetMapping("/cs/configs/configList")
    public RestResult<?> obtainConfigLists(@RequestParam("pageNo") int pageNo,
                                          @RequestParam("pageSize") int pageSize,
                                           @RequestParam("dataId") String dataId,
                                           @RequestParam("group") String group,
                                         @RequestParam("appName") String appName,
                                            @RequestParam("config_tags") String config_tags,
                                            @RequestParam("tenant") String tenant) ;

    @GetMapping("/ns/service/serviceList")
    public RestResult<?> getServiceList(
            @RequestParam ("hasIpCount") Boolean hasIpCount,
            @RequestParam ("withInstances") Boolean withInstances,
            @RequestParam ("pageNo")int pageNo,
            @RequestParam ("pageSize")int pageSize,
            @RequestParam("namespaceId") String namespaceId,
            @RequestParam("serviceNameParam") String serviceNameParam,
            @RequestParam("groupNameParam") String groupNameParam);
    @PutMapping("/ns/service")
    RestResult<?> updateService(
            @RequestParam("namespaceId") String namespaceId,
            @RequestParam("protectThreshold") Float protectThreshold,
            @RequestParam("metadata") String metadata,
            @RequestParam("groupName") String groupName,
            @RequestParam ("serviceName") String serviceName,
            @RequestParam("selector") String selector);
 @PostMapping("/ns/service")
    RestResult<?> postService(
         @RequestParam("namespaceId") String namespaceId,
         @RequestParam("protectThreshold") Float protectThreshold,
         @RequestParam("metadata") String metadata,
         @RequestParam("groupName") String groupName,
         @RequestParam("serviceName") String serviceName,
         @RequestParam("selector") String selector
 );
@GetMapping("/ns/service")
    RestResult<?> getService( @RequestParam("namespaceId") String namespaceId,@RequestParam("serviceName") String serviceName, @RequestParam("groupName") String groupName);
@DeleteMapping("/ns/service")
    RestResult<?> deleteService( @RequestParam("namespaceId") String namespaceId,@RequestParam("serviceName") String serviceName, @RequestParam("groupName") String groupName);

@PostMapping("/ns/instance")
    RestResult<?> registeredInstance (
    @RequestParam("namespaceId") String namespaceId,
    @RequestParam ("ip") String ip,
    @RequestParam ("port") int port,
    @RequestParam ("serviceName") String serviceName,
    @RequestParam("weight") Double weight,
    @RequestParam("enabled") Boolean enabled,
    @RequestParam("healthy") Boolean healthy,
    @RequestParam("clusterName") String clusterName,
    @RequestParam("metadata") String metadata,
    @RequestParam("groupName") String groupName,
    @RequestParam("ephemeral") Boolean ephemeral);



    @GetMapping("/ns/instance")
    RestResult<?> getInstance( @RequestParam("namespaceId") String namespaceId,
                               @RequestParam("ip") String ip,
                               @RequestParam ("port") int port,
                               @RequestParam ("serviceName") String serviceName,
                               @RequestParam("cluster") String cluster,
                               @RequestParam("groupName") String groupName,
                               @RequestParam("ephemeral") Boolean ephemeral,
                               @RequestParam("healthyOnly") Boolean healthyOnly
                               );
    @DeleteMapping("/ns/instance")
    RestResult<?> deleteInstance(  @RequestParam("namespaceId") String namespaceId,
                                   @RequestParam("ip") String ip,
                                   @RequestParam ("port") int port,
                                   @RequestParam ("serviceName") String serviceName,
                                   @RequestParam("clusterName") String clusterName,
                                   @RequestParam("groupName") String groupName,
                                   @RequestParam("ephemeral") Boolean ephemeral);
    @PutMapping("/ns/instance")
    RestResult<?> updateInstance(  @RequestParam("namespaceId") String namespaceId,
                                   @RequestParam ("ip") String ip,
                                   @RequestParam ("port") int port,
                                   @RequestParam ("serviceName") String serviceName,
                                   @RequestParam("weight") Double weight,
                                   @RequestParam("enabled") Boolean enabled,
                                   @RequestParam("clusterName") String clusterName,
                                   @RequestParam("metadata") String metadata,
                                   @RequestParam("groupName") String groupName,
                                   @RequestParam("ephemeral") Boolean ephemeral);
    @GetMapping("/ns/instance/list")
    RestResult<?> getInstanceList( @RequestParam ("serviceName") String serviceName,
                                   @RequestParam("namespaceId") String namespaceId,
                                   @RequestParam("clusterName") String clusterName,
                                   @RequestParam("groupName") String groupName,
                                   @RequestParam("healthyOnly") Boolean healthyOnly
    );
@PutMapping("/ns/cluster")
    RestResult<?> updateServiceCluster(  @RequestParam ("serviceName") String serviceName,
                                         @RequestParam ("clusterName") String clusterName,
                                         @RequestParam ("namespaceId") String namespaceId,
                                         @RequestParam ("useInstancePort4Check") Boolean useInstancePort4Check,
                                         @RequestParam ("metadata") String metadata,
                                         @RequestParam ("checkPort") int checkPort,
                                         @RequestParam ("healthChecker") String healthChecker);


}
