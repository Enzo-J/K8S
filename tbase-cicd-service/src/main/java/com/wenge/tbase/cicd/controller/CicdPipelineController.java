package com.wenge.tbase.cicd.controller;


import com.wenge.tbase.cicd.controller.service.PipelineControllerService;
import com.wenge.tbase.commons.result.ResultCode;
import com.wenge.tbase.commons.result.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Wang XingPeng
 * @since 2020-11-19
 */
@Api(description = "流水线API接口", tags = "流水线API接口")
@RestController
public class CicdPipelineController {

    @Resource
    private PipelineControllerService service;

    @ApiOperation("获取总览数据")
    @GetMapping(value = "/overview")
    public ResultVO getOverview() {
        return new ResultVO(ResultCode.SUCCESS, service.getOverview());
    }

    @ApiOperation("创建流水线数据")
    @PostMapping(value = "/pipeline")
    public ResultVO createPipeline(@RequestParam String name,
                                   @RequestParam(required = false) String description) {
        if (name == null) {
            return new ResultVO(ResultCode.PARAM_IS_EMPTY);
        }
        return new ResultVO(ResultCode.SUCCESS, service.createPipeline(name, description));
    }

    @ApiOperation("执行流水线数据")
    @GetMapping(value = "/executePipeline")
    public ResultVO executePipeline(@RequestParam Long pipelineId,
                                    @RequestParam String name) {
        if (pipelineId == null || name == null) {
            return new ResultVO(ResultCode.PARAM_IS_EMPTY);
        }
        return new ResultVO(ResultCode.SUCCESS, service.executePipeline(pipelineId, name));
    }

}

