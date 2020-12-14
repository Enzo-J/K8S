package com.wenge.tbase.harbor.controller;
import com.wenge.tbase.harbor.bean.Projects;
import com.wenge.tbase.harbor.bean.ResultObject;
import com.wenge.tbase.harbor.bean.Users;
import com.wenge.tbase.harbor.bean.Webhook;
import com.wenge.tbase.harbor.result.RestResult;
import com.wenge.tbase.harbor.result.WengeStatusEnum;
import com.wenge.tbase.harbor.service.HarborWebhookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api("Harbor服务相关测试接口")
@RestController
@RequestMapping("/harbor/v1/webhook")
public class HarborWebhookController {

    @Autowired
    private HarborWebhookService harborWebhookService;


    @ApiOperation(value = "获取项目（应用）下的Webhook列表", notes = "根据项目ID获取Webhook列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "project_id", value = "项目ID", required = true, dataType = "Int")})
    @PostMapping("/getWebhookList")
    public RestResult<?> getWebhookList(@Valid @RequestParam(required = true,value = "project_id") Integer project_id) {
        if(project_id==null){
            return RestResult.error("项目ID不能为空", WengeStatusEnum.SYSYTM_CLIENT_ERROR.getCode());
        }
        return harborWebhookService.getWebhookListService(project_id);
    }

    @ApiOperation(value = "添加Webhook", notes = "根据项目ID在当前项目下添加Webhook -- 前端需要校验名称、通知类型、地址不能为空")
    @ApiImplicitParams({@ApiImplicitParam(name = "project_id", value = "项目ID", required = true, dataType = "Int"),
                        @ApiImplicitParam(name = "webhook", value = "webhook实体", required = true, dataType = "Webhook")})
    @PostMapping("/addWebhookByProjectId")
    public RestResult<?> addWebhookByProjectId(@Valid @RequestParam(required = true,value = "project_id") Integer project_id,
                                        @RequestBody() Webhook webhook) {
        if(project_id==null){
            return RestResult.error("项目ID不能为空", WengeStatusEnum.SYSYTM_CLIENT_ERROR.getCode());
        }
        return harborWebhookService.addWebhookByProjectIdService(project_id,webhook);
    }



}