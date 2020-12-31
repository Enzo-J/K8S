package com.wenge.tbase.gateway.fegin;

import com.wenge.tbase.commons.entity.RestResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/**
 * Author: sunlyhm Date: 2020/11/27 17:33 FileName: MicroserviceConfigFeign
 * Description: 微服务相关的管理feign
 */
@Component
@FeignClient("tbase-microservice-service")
@RequestMapping("/nacos/v1")
public interface MicroserviceConfigFeign {

	@GetMapping("/ns/service/serviceList")
	public RestResult<?> getServiceList(@RequestParam("hasIpCount") Boolean hasIpCount,
			@RequestParam("withInstances") Boolean withInstances, @RequestParam("pageNo") int pageNo,
			@RequestParam("pageSize") int pageSize, @RequestParam("namespaceId") String namespaceId,
			@RequestParam("serviceNameParam") String serviceNameParam,
			@RequestParam("groupNameParam") String groupNameParam);

	@GetMapping("/ns/instance/list")
	RestResult<?> getInstanceList(@RequestParam("serviceName") String serviceName,
			@RequestParam("namespaceId") String namespaceId, @RequestParam("clusterName") String clusterName,
			@RequestParam("groupName") String groupName, @RequestParam("healthyOnly") Boolean healthyOnly);

}
