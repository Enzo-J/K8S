package com.wenge.tbase.cicd.controller.service;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wenge.tbase.cicd.entity.CicdPipeline;
import com.wenge.tbase.cicd.entity.vo.LargeScreenPipelineVo;
import com.wenge.tbase.cicd.entity.vo.OverviewVo;
import com.wenge.tbase.cicd.service.CicdPipelineService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class PipelineControllerService {

    @Resource
    private CicdPipelineService pipelineService;

    /**
     * 获取大屏信息
     *
     * @return
     */
    public OverviewVo getOverview() {
        //获取执行成功数量
        OverviewVo overviewVo = new OverviewVo();
        QueryWrapper<CicdPipeline> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("exec_result", "1");
        overviewVo.setExecSuccessCount(pipelineService.count(queryWrapper));
        //获取执行失败数量
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("exec_result", "0");
        overviewVo.setExecFailureCount(pipelineService.count(queryWrapper));
        //获取未执行数量
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("exec_result", "2");
        overviewVo.setUnExecCount(pipelineService.count(queryWrapper));
        queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_modified").last("limit 5");
        List<CicdPipeline> list = pipelineService.list(queryWrapper);
        List<LargeScreenPipelineVo> pipelineVos = new ArrayList<>();
        list.stream().forEach(o -> {
            LargeScreenPipelineVo pipelineVo = new LargeScreenPipelineVo();
            BeanUtil.copyProperties(o, pipelineVo);
            pipelineVos.add(pipelineVo);
        });
        overviewVo.setPipelineList(pipelineVos);
        return overviewVo;
    }
}
