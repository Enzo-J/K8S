package com.wenge.tbase.cicd.controller;


import com.wenge.tbase.cicd.controller.service.PipelineControllerService;
import com.wenge.tbase.cicd.entity.param.UpdatePipelineParam;
import com.wenge.tbase.commons.result.ResultCode;
import com.wenge.tbase.commons.result.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

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
        //判断名称是否重复

        return new ResultVO(ResultCode.SUCCESS, service.createPipeline(name, description));
    }

    @ApiOperation("获取流水线列表数据")
    @GetMapping(value = "/pipeline")
    public ResultVO getPipelineList(@RequestParam(required = false) String name,
                                    @RequestParam Integer current,
                                    @RequestParam Integer size) {
        if (current == null) {
            current = 0;
        }
        if (size == null) {
            size = 0;
        }
        return new ResultVO(ResultCode.SUCCESS, service.getPipelineList(name, current, size));
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

    @ApiOperation(value = "修改流水线内容")
    @PutMapping(value = "/pipeline")
    public ResultVO updatePipeline(@RequestBody UpdatePipelineParam param) {
        if (param == null || param.getId() == null) {
            return new ResultVO(ResultCode.PARAM_IS_EMPTY);
        }
        Integer runningStatus = service.getPipelineRunningStatus(param.getId());
        if (runningStatus == 1) {
            return new ResultVO(2000, "该流水线正在执行", false);
        }
        return new ResultVO(ResultCode.SUCCESS, service.updatePipeline(param));
    }

    @ApiOperation(value = "删除流水线")
    @DeleteMapping(value = "/pipeline/{id}")
    public ResultVO deletePipeline(@PathVariable Long id) {
        if (id == null) {
            return new ResultVO(ResultCode.PARAM_IS_EMPTY);
        }
        return new ResultVO(ResultCode.SUCCESS, service.deletePipeline(id));
    }


}

