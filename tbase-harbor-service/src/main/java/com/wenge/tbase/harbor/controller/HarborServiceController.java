package com.wenge.tbase.harbor.controller;

import com.wenge.tbase.harbor.result.RestResult;
import com.wenge.tbase.harbor.service.HarborRequest;
import com.wenge.tbase.harbor.service.HarborServiceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;

@Api("Harbor服务服务相关接口")
@RestController
@RequestMapping("/harbor/v1/service")
public class HarborServiceController<V> {

	@Autowired
	private HarborServiceService harborServiceService;
	@Autowired
	private HarborRequest harborRequest;

	/**
	 * 测试
	 * @return
	 */
	@GetMapping("/harborTest")
	public RestResult<?> harborTest(){

		harborRequest.queryImagesTagsByImageName(null);
		harborRequest.queryProject(null);
		return RestResult.ok(harborServiceService.testHarbor(null));
	}

	@ApiOperation(value = "获取镜像", notes = "根据命名空间ID和应用ID获取镜像路径")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "namespaceId", value = "命名空间ID", required = true, dataType = "integer"),
			@ApiImplicitParam(name = "applicationId", value = "应用ID", required = true, dataType = "integer")})
	@GetMapping("/search/ImageId")
	public RestResult<?> getImageByNamespaceAppId(
			@Valid @RequestParam(required = true) Integer namespaceId,
			@Valid @RequestParam(required = true) Integer applicationId) {
		HashMap<String, Object> serviceMap = new HashMap<String, Object>();
		serviceMap.put("namespaceId", namespaceId);
		serviceMap.put("applicationId", applicationId);
		if (namespaceId != null) {
			serviceMap.put("namespaceId", namespaceId);
		}
		return harborServiceService.getImageByNamespaceAppId(serviceMap);
	}

	@ApiOperation(value = "获取镜像", notes = "根据命名空间名称和应用名称获取镜像路径")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "namespaceName", value = "命名空间名称", required = true, dataType = "string"),
			@ApiImplicitParam(name = "applicationName", value = "应用名称", required = true, dataType = "string")})
	@GetMapping("/search/ImageName")
	public RestResult<?> getImageByNamespaceAppName(
			@Valid @RequestParam(required = true) String namespaceName,
			@Valid @RequestParam(required = true) String applicationName) {
		HashMap<String, Object> serviceMap = new HashMap<String, Object>();
		serviceMap.put("namespaceName", namespaceName);
		serviceMap.put("applicationName", applicationName);
		return harborServiceService.getImageByNamespaceAppName(serviceMap);
	}
	
}
