package com.wenge.tbase.gateway.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wenge.tbase.commons.entity.ErrorType;
import com.wenge.tbase.commons.entity.RestResult;
import com.wenge.tbase.gateway.entity.param.GatewayServiceParam;
import com.wenge.tbase.gateway.entity.po.GatewayService;
import com.wenge.tbase.gateway.entity.vo.GatewayServiceVo;
import com.wenge.tbase.gateway.entity.vo.GatewayServiceVoPage;
import com.wenge.tbase.gateway.entity.vo.SynchronizServiceVo;
import com.wenge.tbase.gateway.service.IGatewayServiceService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author dangwei
 * @since 2020-12-17
 */
@RestController
@RequestMapping("/gateway/service")
@CrossOrigin
@Api(description = "网关服务api",tags = "网关服务api")
public class GatewayServiceController {

	@Autowired
	private IGatewayServiceService gatewayServiceService;

	@ApiOperation(value = "新增微服务", notes = "新增一个微服务")
	@ApiImplicitParam(name = "serviceParam", value = "新增服务独享", required = true, dataType = "GatewayServiceParam")
	@ApiResponses(
             @ApiResponse(code = 200, message = "处理成功", response = Boolean.class)
     )
	@PostMapping
	public RestResult<?> add(@RequestBody GatewayServiceParam gatewayServiceParam) {
		GatewayService gatewayService = gatewayServiceParam.toPo();
		if (gatewayService.getServerType().isEmpty() || gatewayService.getServiceName().isEmpty()
				)
			RestResult.error(ErrorType.PARAM_NOT_NULL.getMsg(), ErrorType.PARAM_NOT_NULL.getCode());
		boolean isSuccess = gatewayServiceService.save(gatewayService);
		if (!isSuccess)
			return RestResult.error(ErrorType.FAILED.getMsg(), ErrorType.FAILED.getCode());
		return RestResult.ok(isSuccess);
	}
	
	@ApiOperation(value = "删除微服务", notes = "根据id来删除指定的对象")
    @ApiImplicitParam(paramType = "path", name = "id", value = "服务ID", required = true, dataType = "string")
	 @ApiResponses(
             @ApiResponse(code = 200, message = "处理成功", response = Boolean.class)
     )
    @DeleteMapping(value = "/{id}")
    public RestResult<?> delete(@PathVariable String id) {
		if(id.isEmpty())
			RestResult.error(ErrorType.PARAM_NOT_NULL.getMsg(), ErrorType.PARAM_NOT_NULL.getCode());
		boolean isSuccess=gatewayServiceService.removeById(id);
		if(!isSuccess)
			return RestResult.error(ErrorType.FAILED.getMsg(), ErrorType.FAILED.getCode());		
        return RestResult.ok(isSuccess);
    }	
	
	@ApiOperation(value = "修改服务", notes = "修改指定服务信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "服务ID", required = true, dataType = "string"),
            @ApiImplicitParam(name = "updateServiceParam", value = "更新服务", required = true, dataType = "GatewayServiceParam")
    })	
	 @ApiResponses(
             @ApiResponse(code = 200, message = "处理成功", response = Boolean.class)
     )
    @PutMapping(value = "/{id}")
    public RestResult<?> update(@PathVariable String id,@RequestBody GatewayServiceParam gatewayServiceParam) {
		if(id.isEmpty())
			RestResult.error(ErrorType.PARAM_NOT_NULL.getMsg(), ErrorType.PARAM_NOT_NULL.getCode());
		GatewayService gatewayService = gatewayServiceParam.toPo();    	
		gatewayService.setId(id);
		boolean isSuccess= gatewayServiceService.updateById(gatewayService);		
		if(!isSuccess)
			return RestResult.error(ErrorType.FAILED.getMsg(), ErrorType.FAILED.getCode());		
        return RestResult.ok(isSuccess);
	}	
	
	 @ApiOperation(value = "获取服务详情", notes = "根据id获取指定服务信息")
     @ApiImplicitParam(paramType = "path", name = "id", value = "网关路由ID", required = true, dataType = "string")
     @ApiResponses(
             @ApiResponse(code = 200, message = "处理成功", response = GatewayServiceVo.class)
     )
     @GetMapping(value = "/{id}")
     public RestResult<?> get(@PathVariable String id) {
		 if(id.isEmpty())
				RestResult.error(ErrorType.PARAM_NOT_NULL.getMsg(), ErrorType.PARAM_NOT_NULL.getCode());
		 GatewayService gatewayService=gatewayServiceService.getById(id);
		 if(null ==gatewayService)
			 return RestResult.ok(gatewayService);	
		 GatewayServiceVo result=new GatewayServiceVo(gatewayService);		 
         return RestResult.ok(result);
     }	
	
	 @ApiOperation(value = "根据服务名称模糊搜索", notes = "根据服务名称模糊搜索服务信息")
	 @ApiImplicitParams({
	     @ApiImplicitParam(name = "serviceName", value = "服务名称", required = false, dataType = "string"),
	     @ApiImplicitParam(name = "pageIndex", value = "分页序号", required = true, dataType = "Long"),
	     @ApiImplicitParam(name = "pageSize", value = "每页显示的条数", required = true, dataType = "Long")
	 })	
	 @ApiResponses(
             @ApiResponse(code = 200, message = "处理成功", response = GatewayServiceVoPage.class)
     )	
     @GetMapping
     public RestResult<?> query(@RequestParam(required = false) String serviceName,@Valid@RequestParam long pageIndex,@Valid@RequestParam long pageSize) {
	 	
         return RestResult.ok(gatewayServiceService.query(serviceName, pageIndex, pageSize));
     }
	 
	 @ApiOperation(value = "获取所有服务列表信息", notes = "获取所有服务列表信息")
     @GetMapping("/all")
     public RestResult<?> getAll() {	 	
         return RestResult.ok(gatewayServiceService.getAll());
     }	 
	 
	 @ApiOperation(value = "同步注册中心服务", notes = "同步注册中心服务至本地")	 
	 @ApiResponses(
             @ApiResponse(code = 200, message = "处理成功", response = SynchronizServiceVo.class)
     )
     @GetMapping("/sync")
     public RestResult<?> synchroniz() {			
         return RestResult.ok(gatewayServiceService.synchronizServices());
     }	 
	 
	 @ApiOperation(value = "提交同步结果至数据库", notes = "提交同步结果至数据库")	 
	 @ApiImplicitParams({
	     @ApiImplicitParam(name = "synchronizServiceVo", value = "同步服务集合", required = false, dataType = "SynchronizServiceVo")
	     
	 })	
	 @ApiResponses(
             @ApiResponse(code = 200, message = "处理成功", response = SynchronizServiceVo.class)
     )
     @PostMapping("/submit")
     public RestResult<?> submit2Db(@Valid @RequestBody SynchronizServiceVo synchronizServiceVo) {			
         return RestResult.ok(gatewayServiceService.submit2Db(synchronizServiceVo.getOverrideServiceList(), synchronizServiceVo.getAddServiceList(), synchronizServiceVo.getOfflineServiceList()));
     }
	

}
