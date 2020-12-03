package com.wenge.tbase.nacos.controller;
import javax.validation.Valid;

import com.wenge.tbase.nacos.result.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.wenge.tbase.nacos.service.NacosConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.HashMap;
/**
 * Author: sunlyhm
 * Date: 2020/11/29 15:06
 * FileName: NacosServiceClusterController
 * Description: nacos服务配置管理接口
 */
@Api(tags = "微服务配置管理相关接口")
@RestController
@RequestMapping("/nacos/v1/cs/configs")
public class NacosServiceConfigController {	
	@Autowired
	private NacosConfigService nacosConfigService;
	@ApiOperation(value = "获取微服务的配置列表", notes = "获取微服务的配置列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "pageNo", value = "分页页码", required = true, dataType = "Int"),
			@ApiImplicitParam(name = "pageSize", value = "每页显示数量", required = true, dataType = "Int"),
			@ApiImplicitParam(name = "dataId", value = "配置ID，如若没有对应值，可传递空值", required = true, dataType = "String"),
			@ApiImplicitParam(name = "group", value = "配置分组，如若没有对应值，可传递空值", required = true, dataType = "String"),
			@ApiImplicitParam(name = "appName", value = "所属应用，如若没有对应值，可传递空值", required = true, dataType = "String"),
			@ApiImplicitParam(name = "config_tags", value = "配置标签，如若没有对应值，可传递空值", required = true, dataType = "String"),
			@ApiImplicitParam(name = "tenant", value = "租户信息，对应Nacos的命名空间ID字段", required = false, dataType = "String")
	})
	//tenant租户信息需要和项目紧密结合如：某个项目下的某个模块对应的租户信息
	@GetMapping("/configList")
	public RestResult<?> obtainConfigLists(
			                             @Valid @RequestParam int pageNo,
										 @Valid @RequestParam int pageSize,
										 @Valid @RequestParam String dataId,
										 @Valid @RequestParam String group,
										 @Valid @RequestParam String appName,
										 @Valid @RequestParam String config_tags,
										 @Valid @RequestParam(required = false) String tenant) {
		HashMap<String, String> configMap = new HashMap<String, String>();
		configMap.put("dataId", dataId);
		configMap.put("pageNo", String.valueOf(pageNo));
		configMap.put("pageSize", String.valueOf(pageSize));
		configMap.put("group", group);
		configMap.put("appName", appName);
		configMap.put("config_tags", config_tags);
		configMap.put("search", "accurate");
		if (tenant != null) {
			configMap.put("tenant", tenant);
		}
		return nacosConfigService.obtainConfigLists(configMap);
	}


		@ApiOperation(value = "获取nacos配置详情信息", notes = "获取nacos配置详情信息")
		@ApiImplicitParams({
			@ApiImplicitParam(name = "tenant", value = "租户信息，对应Nacos的命名空间ID字段(此字段若有对应信息，必须进行传递)", required = false, dataType = "String"),
			@ApiImplicitParam(name = "dataId", value = "配置ID", required = true, dataType = "String"),
			@ApiImplicitParam(name = "group", value = "配置分组", required = true, dataType = "String"),
			@ApiImplicitParam(name = "show", value = "展示配置信息，如果需要获取详细配置请填充为all", required = false, dataType = "String"),
			@ApiImplicitParam(name = "namespaceId", value = "命名空间", required = false, dataType = "String")
	})
		//须标明什么情况必须传递tenant
		@GetMapping("/configDetail")
		public RestResult<?> obtainConfig(@Valid @RequestParam(required = false) String tenant,
										  @Valid @RequestParam(required = false) String namespaceId,
										  @Valid @RequestParam String dataId,
										  @Valid @RequestParam String group,
										  @Valid @RequestParam(required = false) String show) {
			HashMap<String, String> configMap = new HashMap<String, String>();
			configMap.put("dataId", dataId);
			configMap.put("group", group);
			if (tenant != null) {
				configMap.put("tenant", tenant);
			}
			if (show != null) {
				configMap.put("show", show);
			}
			if (namespaceId != null) {
				configMap.put("namespaceId", namespaceId);
			}

		return nacosConfigService.obtainConfig(configMap);
		}
	@ApiOperation(value = "监听nacos上面的配置", notes = "监听nacos配置")
	@ApiImplicitParam(name = "listeningConfigs", value = "监听数据报文", required = true,  dataType = "String")
	@PostMapping("/listener")
	public RestResult<?> listenConfigs(@Valid @RequestParam String listeningConfigs) {
		return nacosConfigService.listenConfigs(listeningConfigs);
	}	
	
	@ApiOperation(value = "创建发布nacos配置", notes = "创建发布nacos配置")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "tenant", value = "租户信息，对应 Nacos 的命名空间ID字段", required = false, dataType = "String"),
			@ApiImplicitParam(name = "dataId", value = "配置ID", required = true, dataType = "String"),
			@ApiImplicitParam(name = "content", value = "配置内容", required = true, dataType = "String"),
			@ApiImplicitParam(name = "group", value = "配置分组", required = true, dataType = "String"),
			@ApiImplicitParam(name = "type", value = "配置类型", required = false, dataType = "String"),
			@ApiImplicitParam(name = "namespaceId", value = "命名空间", required = false, dataType = "String"),
			@ApiImplicitParam(name = "desc", value = "配置描述", required = false, dataType = "String"),
			@ApiImplicitParam(name = "config_tags", value = "配置标签", required = false, dataType = "String"),
			@ApiImplicitParam(name = "appName", value = "所属应用", required = false, dataType = "String")

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
		HashMap<String, String> configMap = new HashMap<String, String>();
		configMap.put("dataId", dataId);
		configMap.put("content", content);
		configMap.put("group", group);
		if (namespaceId != null) {
			configMap.put("namespaceId", namespaceId);
		}
		if (tenant != null) {
			configMap.put("tenant", tenant);
		}
		if (type != null) {
			configMap.put("type", type);
		}
		if (desc != null) {
			configMap.put("desc", desc);
		}
		if (config_tags != null) {
			configMap.put("config_tags", config_tags);
		}
		if (appName != null) {
			configMap.put("appName", appName);
		}
		return nacosConfigService.releaseConfigs(configMap);
	}	
	
	@ApiOperation(value = "删除nacos配置", notes = "删除nacos配置")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "tenant", value = "租户信息，对应 Nacos 的命名空间ID字段", required = false, dataType = "String"),
        @ApiImplicitParam(name = "dataId", value = "配置ID", required = true, dataType = "String"),
        @ApiImplicitParam(name = "group", value = "配置分组", required = true, dataType = "String")
})
	@DeleteMapping
	public RestResult<?> deleteConfigs(@Valid @RequestParam(required = false) String tenant,@Valid @RequestParam String dataId,@Valid @RequestParam String group) {
		HashMap<String, String> configMap = new HashMap<String, String>();
		configMap.put("dataId", dataId);
		configMap.put("group", group);
		if (tenant != null) {
			configMap.put("tenant", tenant);
		}
		return nacosConfigService.deleteConfigs(configMap);
	}	
}