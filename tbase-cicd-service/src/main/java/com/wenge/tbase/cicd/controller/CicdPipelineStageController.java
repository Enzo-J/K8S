package com.wenge.tbase.cicd.controller;


import com.wenge.tbase.cicd.controller.service.PipelineStageControllerService;
import com.wenge.tbase.cicd.entity.param.CreatePipelineStageParam;
import com.wenge.tbase.commons.result.ResultCode;
import com.wenge.tbase.commons.result.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(description = "流水线阶段API接口", tags = "流水线阶段API接口")
@RestController
public class CicdPipelineStageController {

    @Resource
    private PipelineStageControllerService service;

    @ApiOperation(value = "增加流水线阶段")
    @PostMapping(value = "/pipelineStage")
    public ResultVO createPipelineStage(@RequestBody CreatePipelineStageParam param) {
        if (param == null || param.getPipelineId() == null) {
            return new ResultVO(ResultCode.PARAM_IS_EMPTY);
        }
        return new ResultVO(ResultCode.SUCCESS, service.createPipelineStage(param));
    }
}

