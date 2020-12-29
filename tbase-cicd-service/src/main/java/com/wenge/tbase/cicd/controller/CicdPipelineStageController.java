package com.wenge.tbase.cicd.controller;


import com.wenge.tbase.cicd.controller.service.PipelineStageControllerService;
import com.wenge.tbase.cicd.entity.param.CreatePipelineStageParam;
import com.wenge.tbase.commons.result.ResultCode;
import com.wenge.tbase.commons.result.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(description = "流水线阶段API接口", tags = "流水线阶段API接口")
@RestController
public class CicdPipelineStageController {

    @Resource
    private PipelineStageControllerService service;

    @ApiOperation(value = "增加或修改流水线阶段")
    @PostMapping(value = "/pipelineStage")
    public ResultVO createOrUpdatePipelineStage(@RequestBody CreatePipelineStageParam param) {
        if (param == null || param.getPipelineId() == null) {
            return new ResultVO(ResultCode.PARAM_IS_EMPTY);
        }
        return new ResultVO(ResultCode.SUCCESS, service.createOrUpdatePipelineStage(param));
    }

    @ApiOperation(value = "获取流水线阶段内容")
    @GetMapping(value = "/pipelineStage/{pipelineId}")
    public ResultVO getPipelineStage(@PathVariable Long pipelineId) {
        if (pipelineId == null) {
            return new ResultVO(ResultCode.PARAM_IS_EMPTY);
        }
        return new ResultVO(ResultCode.SUCCESS, service.getPipelineStage(pipelineId));
    }

    @ApiOperation(value = "获取最后构建日志")
    @GetMapping(value = "/getLastBuildLog")
    public ResultVO getLastBuildLog(@RequestParam String name) {
        if (name == null) {
            return new ResultVO(ResultCode.PARAM_IS_EMPTY);
        }
        return new ResultVO(ResultCode.SUCCESS, service.getLastBuildLog(name));
    }

    @ApiOperation(value = "获取构建列表数据")
    @GetMapping(value = "/getBuildHistoryList")
    public ResultVO getBuildHistoryList(@RequestParam String name,
                                        @RequestParam Integer current,
                                        @RequestParam Integer size) {
        if (name == null) {
            return new ResultVO(ResultCode.PARAM_IS_EMPTY);
        }
        if (current == null) {
            current = 0;
        }
        if (size == null) {
            size = 10;
        }
        return new ResultVO(ResultCode.SUCCESS, service.getBuildHistoryList(name, current, size));
    }

    @ApiOperation(value = "获取构建历史日志")
    @GetMapping(value = "/getBuildHistoryLog")
    public ResultVO getBuildHistoryLog(@RequestParam String name,
                                       @RequestParam Integer buildNumber) {
        if (name == null || buildNumber == null) {
            return new ResultVO(ResultCode.PARAM_IS_EMPTY);
        }
        return new ResultVO(ResultCode.SUCCESS, service.getBuildHistoryLog(name, buildNumber));
    }

    @ApiOperation(value = "获取构建阶段视图信息列表")
    @GetMapping(value = "/getBuildStageView")
    public ResultVO getBuildStageView(@RequestParam String name) {
        if (name == null) {
            return new ResultVO(ResultCode.PARAM_IS_EMPTY);
        }
        return new ResultVO(ResultCode.SUCCESS, service.getBuildStageView(name));
    }

    @ApiOperation(value = "获取正在运行中的构建阶段视图信息")
    @GetMapping(value = "/getRunningBuildStageView")
    public ResultVO getRunningBuildStageView(@RequestParam String name,
                                             @RequestParam Integer id) {
        if (name == null || id == null) {
            return new ResultVO(ResultCode.PARAM_IS_EMPTY);
        }
        return new ResultVO(ResultCode.SUCCESS, service.getRunningBuildStageView(name, id));
    }


}

