package com.wenge.tbase.cicd.controller.service;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.QueueReference;
import com.wenge.tbase.cicd.entity.CicdPipeline;
import com.wenge.tbase.cicd.entity.CicdPipelineStage;
import com.wenge.tbase.cicd.entity.Jenkins;
import com.wenge.tbase.cicd.entity.dto.JenkinsTemplateDTO;
import com.wenge.tbase.cicd.entity.enums.PipelineStageTypeEnum;
import com.wenge.tbase.cicd.entity.param.*;
import com.wenge.tbase.cicd.entity.vo.LargeScreenPipelineVo;
import com.wenge.tbase.cicd.entity.vo.OverviewVo;
import com.wenge.tbase.cicd.jenkins.template.JenkinsTemplate;
import com.wenge.tbase.cicd.service.CicdPipelineService;
import com.wenge.tbase.cicd.service.CicdPipelineStageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class PipelineControllerService {

    @Resource
    private CicdPipelineService pipelineService;

    @Resource
    private CicdPipelineStageService pipelineStageService;

    @Resource
    private JenkinsServer jenkinsServer;

    @Resource
    private Jenkins jenkins;

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


    /**
     * 创建流水线
     *
     * @param name
     * @param description
     * @return
     */
    public Long createPipeline(String name, String description) {
        // 插入到jenkins服务器中
        String xml = JenkinsTemplate.getBulidJenkinsTemplateXml(description, jenkins.getToken());
        try {
            jenkinsServer.createJob(name, xml, true);
        } catch (IOException e) {
            log.error("创建流水线失败", e.getMessage());
            return null;
        }
        //插入到数据库中信息
        CicdPipeline cicdPipeline = new CicdPipeline();
        cicdPipeline.setName(name);
        cicdPipeline.setDescription(description);
        pipelineService.insertPipeline(cicdPipeline);
        return cicdPipeline.getId();
    }

    /**
     * 执行流水线
     *
     * @return
     */
    public Boolean executePipeline(Long pipelineId, String name) {
        // 1.根据id查询流水线阶段
        QueryWrapper<CicdPipelineStage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pipeline_id", pipelineId)
                .orderByAsc("type");
        List<CicdPipelineStage> list = pipelineStageService.list(queryWrapper);
        if (list == null || list.size() == 0) {
            return null;
        }
        StringBuffer script = new StringBuffer();
        script.append("node { \n");
        for (CicdPipelineStage pipelineStage : list) {
            //代码拉取
            if (pipelineStage.getType() == PipelineStageTypeEnum.CODE_PULL.getType()) {
                CodePullParam codePullParam = JSONUtil.toBean(pipelineStage.getParameter(), CodePullParam.class);
                String codePullStage = JenkinsTemplate.getCodePullStage(codePullParam.getBranch(), codePullParam.getCodeUrl(), codePullParam.getCredentialId());
                script.append(codePullStage);
            }
            //代码检测
            if (pipelineStage.getType() == PipelineStageTypeEnum.CODE_CHECK.getType()) {
                CodeCheckParam codeCheckParam = JSONUtil.toBean(pipelineStage.getParameter(), CodeCheckParam.class);
                //来源于仓库
                if (codeCheckParam.getSonarFileSource() == 1) {
                    script.append(JenkinsTemplate.getCodeCheckStage(codeCheckParam.getSonarFileAddress()));
                } else if (codeCheckParam.getSonarFileSource() == 2) {
                    //来源于平台
                }
            }
            //编译打包公共子工程
            if (pipelineStage.getType() == PipelineStageTypeEnum.PACKAGE_COMMON.getType()) {
                PackageCommonParam packageCommonParam = JSONUtil.toBean(pipelineStage.getParameter(), PackageCommonParam.class);
                script.append(JenkinsTemplate.getPackageCommonStage(packageCommonParam));
            }
            //编译打包工程
            if (pipelineStage.getType() == PipelineStageTypeEnum.PACKAGE.getType()) {
                PackageParam packageParam = JSONUtil.toBean(pipelineStage.getParameter(), PackageParam.class);
                script.append(JenkinsTemplate.getPackageStage(packageParam));
            }
            //镜像构建
            if (pipelineStage.getType() == PipelineStageTypeEnum.IMAGE_BUILD.getType()) {
                ImageBuildParam imageBuildParam = JSONUtil.toBean(pipelineStage.getParameter(), ImageBuildParam.class);
                //来源于仓库
                if (imageBuildParam.getDockerfileSource() == 1) {
                    script.append(JenkinsTemplate.getImageBuildStage(imageBuildParam));
                } else if (imageBuildParam.getDockerfileSource() == 2) {
                    //来源于平台
                }
            }
        }
        script.append(" \n}");
        try {
            String jenkinsTemplateXml = JenkinsTemplate.getJenkinsTemplateXml(script.toString(), jenkins.getToken());
            jenkinsServer.updateJob(name, jenkinsTemplateXml, true);
            jenkinsServer.getJob(name).build(true);
        } catch (IOException e) {
            log.error("执行流水线错误" + e.getMessage());
            e.printStackTrace();
        }
        return true;
    }
}
