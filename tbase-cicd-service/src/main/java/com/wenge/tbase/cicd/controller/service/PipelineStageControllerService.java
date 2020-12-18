package com.wenge.tbase.cicd.controller.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.client.JenkinsHttpClient;
import com.offbytwo.jenkins.helper.Range;
import com.offbytwo.jenkins.model.Build;
import com.offbytwo.jenkins.model.BuildWithDetails;
import com.offbytwo.jenkins.model.JobWithDetails;
import com.wenge.tbase.cicd.entity.CicdPipelineStage;
import com.wenge.tbase.cicd.entity.enums.BuildStatusEnum;
import com.wenge.tbase.cicd.entity.enums.PipelineStageTypeEnum;
import com.wenge.tbase.cicd.entity.param.CreatePipelineStageParam;
import com.wenge.tbase.cicd.entity.vo.BuildHistoryVo;
import com.wenge.tbase.cicd.entity.vo.BuildStageVo;
import com.wenge.tbase.cicd.entity.vo.StageVo;
import com.wenge.tbase.cicd.service.CicdPipelineStageService;
import com.wenge.tbase.commons.result.ListVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: PipelineStageControllerService
 * @Description: PipelineStageControllerService
 * @Author: Wang XingPeng
 * @Date: 2020/12/3 9:55
 */
@Component
@Slf4j
public class PipelineStageControllerService {

    @Resource
    private JenkinsServer jenkinsServer;

    @Resource
    private CicdPipelineStageService pipelineStageService;

    @Resource
    private JenkinsHttpClient jenkinsHttpClient;

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
     * 获取最后构建日志
     *
     * @return
     */
    public String getLastBuildLog(String name) {
        try {
            JobWithDetails job = jenkinsServer.getJob(name);
            if (job.getBuilds().size() != 0) {
                return job.getLastBuild().details().getConsoleOutputText();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取历史构建列表
     *
     * @param name
     * @param current
     * @param size
     * @return
     */
    public ListVo getBuildHistoryList(String name, Integer current, Integer size) {
        try {
            JobWithDetails job = jenkinsServer.getJob(name);
            int f = (current - 1 < 0 ? 0 : current - 1) * size;
            int t = f + size;
            Range range = Range.build().from(f).to(t);
            List<Build> buildList = job.getAllBuilds(range);
            int number = job.getLastBuild().getNumber();
            ListVo listVo = new ListVo();
            listVo.setTotal(Long.valueOf(number));
            List<BuildHistoryVo> buildHistoryVoList = new ArrayList<>();
            for (Build b : buildList) {
                BuildHistoryVo vo = new BuildHistoryVo();
                vo.setBuildNumber(b.getNumber());
                vo.setBuildDate(DateUtil.date(b.details().getTimestamp()));
                vo.setBuildStatus(BuildStatusEnum.getBuildStatus(b.details().getResult().toString()));
                buildHistoryVoList.add(vo);
            }
            listVo.setDataList(buildHistoryVoList);
            return listVo;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取构建历史日志
     *
     * @param name
     * @param buildNumber
     * @return
     */
    public String getBuildHistoryLog(String name, Integer buildNumber) {
        try {
            JobWithDetails job = jenkinsServer.getJob(name);
            return job.getBuildByNumber(buildNumber).details().getConsoleOutputText();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取构建阶段视图列表
     *
     * @param name
     * @return
     */
    public List<BuildStageVo> getBuildStageView(String name) {
        String url = "/job/" + name + "/wfapi/runs";
        List<NameValuePair> data = new ArrayList<>();
        try {
            HttpResponse httpResponse = jenkinsHttpClient.post_form_with_result(url, data, true);

            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity, "UTF-8");
                List<BuildStageVo> buildStageVos = JSONUtil.toList(JSONUtil.parseArray(result), BuildStageVo.class);
                if (buildStageVos != null) {
                    return buildStageVos;
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }
        return null;
    }

    /**
     * 根据编号获取正在构建阶段视图
     *
     * @return
     */
    public BuildStageVo getRunningBuildStageView(String name, Integer id) {
        String url = "/job/" + name + "/wfapi/runs?since=%23" + id + "&fullStages=true";
        List<NameValuePair> data = new ArrayList<>();
        try {
            HttpResponse httpResponse = jenkinsHttpClient.post_form_with_result(url, data, true);
            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity, "UTF-8");
                List<BuildStageVo> buildStageVos = JSONUtil.toList(JSONUtil.parseArray(result), BuildStageVo.class);
                if (buildStageVos != null) {
                    return buildStageVos.get(0);
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }
        return null;
    }
}
