package com.wenge.tbase.cicd.controller;

import com.wenge.tbase.cicd.controller.service.PipelineControllerService;
import com.wenge.tbase.cicd.entity.vo.LargeScreenPipelineVo;
import com.wenge.tbase.cicd.entity.vo.OverviewVo;
import com.wenge.tbase.commons.result.ResultCode;
import com.wenge.tbase.commons.result.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Api(description = "流水线API")
@RestController
public class PipelineController {

    @Resource
    private PipelineControllerService pipelineControllerService;

    @GetMapping(value = "/overview")
    @ApiOperation("获取总览数据")
    public ResultVO getOverview() {
        return new ResultVO(ResultCode.SUCCESS, pipelineControllerService.getOverview());
    }
}
