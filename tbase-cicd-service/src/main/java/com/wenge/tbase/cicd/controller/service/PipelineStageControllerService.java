package com.wenge.tbase.cicd.controller.service;

import cn.hutool.json.JSONUtil;
import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.JobWithDetails;
import com.wenge.tbase.cicd.entity.CicdPipelineStage;
import com.wenge.tbase.cicd.entity.enums.PipelineStageTypeEnum;
import com.wenge.tbase.cicd.entity.param.CreatePipelineStageParam;
import com.wenge.tbase.cicd.service.CicdPipelineStageService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @ClassName: PipelineStageControllerService
 * @Description: PipelineStageControllerService
 * @Author: Wang XingPeng
 * @Date: 2020/12/3 9:55
 */
@Component
public class PipelineStageControllerService {

    @Resource
    private JenkinsServer jenkinsServer;

    @Resource
    private CicdPipelineStageService pipelineStageService;

    /**
     * 创建流水线阶段
     */
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public Boolean createPipelineStage(CreatePipelineStageParam param) {
        CicdPipelineStage pipelineStage;
        // 1.代码拉取步骤
        if (param.getCodePullParam() != null) {
            pipelineStage = new CicdPipelineStage();
            pipelineStage.setPipelineId(param.getPipelineId());
            pipelineStage.setType(PipelineStageTypeEnum.CODE_PULL.getType());
            pipelineStage.setName(param.getCodePullParam().getStageName());
            pipelineStage.setParameter(JSONUtil.toJsonStr(param.getCodePullParam()));
            pipelineStageService.save(pipelineStage);
        }
        // 2.代码检测步骤
        if (param.getCodeCheckParam() != null) {
            pipelineStage = new CicdPipelineStage();
            pipelineStage.setPipelineId(param.getPipelineId());
            pipelineStage.setType(PipelineStageTypeEnum.CODE_CHECK.getType());
            pipelineStage.setName(param.getCodeCheckParam().getStageName());
            pipelineStage.setParameter(JSONUtil.toJsonStr(param.getCodeCheckParam()));
            pipelineStageService.save(pipelineStage);
        }
        if (param.getPackageCommonParam() != null) {
            pipelineStage = new CicdPipelineStage();
            pipelineStage.setPipelineId(param.getPipelineId());
            pipelineStage.setType(PipelineStageTypeEnum.PACKAGE_COMMON.getType());
            pipelineStage.setName(param.getPackageCommonParam().getStageName());
            pipelineStage.setParameter(JSONUtil.toJsonStr(param.getPackageCommonParam()));
            pipelineStageService.save(pipelineStage);
        }
        // 3.编译打包步骤
        if (param.getPackageParam() != null) {
            pipelineStage = new CicdPipelineStage();
            pipelineStage.setPipelineId(param.getPipelineId());
            pipelineStage.setType(PipelineStageTypeEnum.PACKAGE.getType());
            pipelineStage.setName(param.getPackageParam().getStageName());
            pipelineStage.setParameter(JSONUtil.toJsonStr(param.getPackageParam()));
            pipelineStageService.save(pipelineStage);
        }
        // 4.镜像构建步骤
        if (param.getImageBuildParam() != null) {
            pipelineStage = new CicdPipelineStage();
            pipelineStage.setPipelineId(param.getPipelineId());
            pipelineStage.setType(PipelineStageTypeEnum.IMAGE_BUILD.getType());
            pipelineStage.setName(param.getImageBuildParam().getStageName());
            pipelineStage.setParameter(JSONUtil.toJsonStr(param.getImageBuildParam()));
            pipelineStageService.save(pipelineStage);
        }
        // 5.镜像上传步骤
        if (param.getImageUploadParam() != null) {
            pipelineStage = new CicdPipelineStage();
            pipelineStage.setPipelineId(param.getPipelineId());
            pipelineStage.setType(PipelineStageTypeEnum.IMAGE_UPLOAD.getType());
            pipelineStage.setName(param.getImageUploadParam().getStageName());
            pipelineStage.setParameter(JSONUtil.toJsonStr(param.getImageUploadParam()));
            pipelineStageService.save(pipelineStage);
        }
        // 6.部署步骤
        return true;
    }


    /**
     * 获取流水线日志
     *
     * @return
     */
    public String getPipelineStageLog(String name) {
        try {
            JobWithDetails job = jenkinsServer.getJob(name);
            return job.getLastBuild().details().getConsoleOutputText();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
