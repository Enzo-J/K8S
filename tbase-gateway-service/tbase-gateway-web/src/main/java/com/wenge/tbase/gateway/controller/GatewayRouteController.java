package com.wenge.tbase.gateway.controller;


import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.wenge.tbase.commons.entity.RestResult;
import com.wenge.tbase.gateway.dao.ServiceInterceptMybatisDAO;
import com.wenge.tbase.gateway.entity.base.ServiceIntercept;
import com.wenge.tbase.gateway.entity.form.GatewayRouteForm;
//import com.wenge.tbase.gateway.entity.form.GatewayRouteForm;
import com.wenge.tbase.gateway.entity.form.GatewayRouteQueryForm;
import com.wenge.tbase.gateway.entity.param.GatewayRouteParam;
import com.wenge.tbase.gateway.entity.po.GatewayRoute;
import com.wenge.tbase.gateway.entity.vo.GatewayRouteVo;
import com.wenge.tbase.gateway.entity.vo.GatewayRouteVoPage;
import com.wenge.tbase.gateway.service.IGatewayRouteService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/gateway/routes")
@Api(description = "网关路由api",tags = "网关路由api")
@Slf4j 
@CrossOrigin
public class GatewayRouteController {
    
    @Autowired
    private IGatewayRouteService gatewayRoutService;
    
    @Autowired
    private ServiceInterceptMybatisDAO serviceInterceptMybatisDAO;
    
    @GetMapping(value = "/serviceIntercept")
    public List<ServiceIntercept> getServiceIntercept() {
        return serviceInterceptMybatisDAO.getAllReleaseUrl();
    }    

    @ApiOperation(value = "新增网关路由", notes = "新增一个网关路由")
    @ApiImplicitParam(name = "gatewayRoutParam", value = "新增网关路由form表单", required = true, dataType = "GatewayRouteParam")
    @PostMapping
    public RestResult<?> add(@Valid @RequestBody GatewayRouteParam gatewayRoutParam) {
        log.info("name:", gatewayRoutParam);
        GatewayRouteForm  gatewayRoutForm=gatewayRoutParam.toForm();
        GatewayRoute gatewayRout = gatewayRoutForm.toPo(GatewayRoute.class);
        return RestResult.ok(gatewayRoutService.add(gatewayRout));
    }

    @ApiOperation(value = "删除网关路由", notes = "根据url的id来指定删除对象")
    @ApiImplicitParam(paramType = "path", name = "id", value = "网关路由ID", required = true, dataType = "string")
    @DeleteMapping(value = "/{id}")
    public RestResult<?> delete(@PathVariable String id) {
        return RestResult.ok(gatewayRoutService.delete(id));
    }

    @ApiOperation(value = "修改网关路由", notes = "修改指定网关路由信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "网关路由ID", required = true, dataType = "string"),
            @ApiImplicitParam(name = "gatewayRoutForm", value = "网关路由实体", required = true, dataType = "GatewayRouteForm")
    })
    @PutMapping(value = "/{id}")
    public RestResult<?> update(@PathVariable String id, @Valid @RequestBody GatewayRouteParam gatewayRoutParam) {
    	GatewayRouteForm  gatewayRoutForm=gatewayRoutParam.toForm();
        GatewayRoute gatewayRout = gatewayRoutForm.toPo(GatewayRoute.class);
        gatewayRout.setId(id);
        return RestResult.ok(gatewayRoutService.update(gatewayRout));
    }
    
    @ApiOperation(value = "更新网关发布状态", notes = "更新网关发布状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "网关路由ID", required = true, dataType = "string"),
            @ApiImplicitParam(name = "status", value = "Y：发布 N：未发布", required = true, dataType = "string")
    })
    @PutMapping(value = "/status")
    public RestResult<?> updateStatus(@Valid@RequestParam String id, @Valid@RequestParam String status ) {
    	
        return RestResult.ok(gatewayRoutService.updateStatus(id,status));
    }
    
    

    @ApiOperation(value = "获取网关路由", notes = "根据id获取指定网关路由信息")
    @ApiImplicitParam(paramType = "path", name = "id", value = "网关路由ID", required = true, dataType = "string")
    @ApiResponses(
            @ApiResponse(code = 200, message = "处理成功", response = GatewayRouteVo.class)
    )
    @GetMapping(value = "/{id}")
    public RestResult<?> get(@PathVariable String id) {
        log.info("get with id:{}", id);
        return RestResult.ok(new GatewayRouteVo(gatewayRoutService.get(id)));
    }

//    @ApiOperation(value = "根据服务名获取网关路由", notes = "根据服务名网关路由信息，简单查询")
//    @ApiImplicitParam(paramType = "query", name = "name", value = "网关路由路径", required = true, dataType = "string")
//    @ApiResponses(
//            @ApiResponse(code = 200, message = "处理成功", response = RestResult.class)
//    )
//    @GetMapping
//    public RestResult<?> getByUri(@RequestParam String uri) {
//        return RestResult.ok(gatewayRoutService.query(new GatewayRouteQueryParam(uri)).stream().findFirst());
//    }

    @ApiOperation(value = "搜索网关路由", notes = "根据条件查询网关路由信息")
    @ApiImplicitParam(name = "gatewayRoutQueryForm", value = "网关路由查询参数", required = true, dataType = "GatewayRouteQueryForm")
    @ApiResponses(
            @ApiResponse(code = 200, message = "处理成功", response = GatewayRouteVoPage.class)
    )
    @PostMapping(value = "/conditions")
    public RestResult<?> search(@Valid @RequestBody GatewayRouteQueryForm gatewayRouteQueryForm) {
        return RestResult.ok(gatewayRoutService.query(gatewayRouteQueryForm));
    }

    @ApiOperation(value = "重载网关路由", notes = "将所以网关的路由全部重载到redis中")
    @ApiResponses(
            @ApiResponse(code = 200, message = "处理成功", response = RestResult.class)
    )
    @PostMapping(value = "/overload")
    public RestResult<?> overload() {
        return RestResult.ok(gatewayRoutService.overload());
    }

}