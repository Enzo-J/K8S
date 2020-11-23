package com.wenge.tbase.nacos.controller;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.wenge.tbase.nacos.result.RestResult;
import com.wenge.tbase.nacos.service.NacosConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api("nacos配置管理接口")
@RestController
@RequestMapping("/nacos/v1/cs/configs")
public class NacosServiceConfigController {	
    @Autowired
	private NacosConfigService nacosConfigService;
	@ApiOperation(value = "获取nacos上面的配置", notes = "获取nacos配置")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "tenant", value = "租户信息，对应 Nacos 的命名空间ID字段(此字段若有对应信息，必须进行传递)", required = false, dataType = "string"),
        @ApiImplicitParam(name = "dataId", value = "配置 ID", required = true, dataType = "string"),
        @ApiImplicitParam(name = "group", value = "配置分组", required = true, dataType = "string")
})
	//须标明什么情况必须传递tenant
    @GetMapping
	public RestResult<?> getConfigs(@Valid @RequestParam(required = false) String tenant,@Valid @RequestParam String dataId,@Valid @RequestParam String group) {
		return nacosConfigService.getConfigs(tenant, dataId,group);
	}	
	@ApiOperation(value = "监听nacos上面的配置", notes = "监听nacos配置")
	@ApiImplicitParam(name = "listeningConfigs", value = "监听数据报文", required = true,  dataType = "string")
//	@PostMapping(value = "/uploadBatch", headers = "content-type=multipart/form-data")
	@PostMapping("/listener")
	public RestResult<?> listenConfigs(@Valid @RequestParam String listeningConfigs) {
		return nacosConfigService.listenConfigs(listeningConfigs);
	}	
	
	@ApiOperation(value = "发布nacos配置", notes = "发布nacos配置")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "tenant", value = "租户信息，对应 Nacos 的命名空间ID字段", required = false, dataType = "string"),
        @ApiImplicitParam(name = "dataId", value = "配置ID", required = true, dataType = "string"),
        @ApiImplicitParam(name = "content", value = "配置内容", required = true, dataType = "string"),
        @ApiImplicitParam(name = "group", value = "配置分组", required = true, dataType = "string"),
        @ApiImplicitParam(name = "type", value = "配置类型", required = false, dataType = "string")
})//	@PostMapping(value = "/uploadBatch", headers = "content-type=multipart/form-data")
	@PostMapping
	public RestResult<?> releaseConfigs(@Valid @RequestParam(required = false) String tenant,@Valid @RequestParam String dataId,@Valid @RequestParam String group,@Valid @RequestParam String content,@Valid @RequestParam(required = false) String type ) {
		return nacosConfigService.releaseConfigs(tenant,dataId,content,group,type);
	}	
	
	@ApiOperation(value = "删除nacos配置", notes = "删除nacos配置")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "tenant", value = "租户信息，对应 Nacos 的命名空间ID字段", required = false, dataType = "string"),
        @ApiImplicitParam(name = "dataId", value = "配置ID", required = true, dataType = "string"),
        @ApiImplicitParam(name = "group", value = "配置分组", required = true, dataType = "string")
})//	@PostMapping(value = "/uploadBatch", headers = "content-type=multipart/form-data")
	@DeleteMapping
	public RestResult<?> deleteConfigs(@Valid @RequestParam(required = false) String tenant,@Valid @RequestParam String dataId,@Valid @RequestParam String group) {
		return nacosConfigService.deleteConfigs(tenant,dataId,group);
	}	
}