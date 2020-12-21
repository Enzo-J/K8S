package com.wenge.tbase.cicd.controller.service;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.offbytwo.jenkins.JenkinsServer;
import com.wenge.tbase.cicd.entity.*;
import com.wenge.tbase.cicd.entity.enums.PipelineStageTypeEnum;
import com.wenge.tbase.cicd.entity.param.*;
import com.wenge.tbase.cicd.entity.vo.*;
import com.wenge.tbase.cicd.jenkins.template.JenkinsTemplate;
import com.wenge.tbase.cicd.service.*;
import com.wenge.tbase.commons.result.ListVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    private CicdReposService reposService;

    @Resource
    private CicdDockerfileService dockerfileService;

    @Resource
    private CicdSonarqubeService sonarqubeService;

    @Resource
    private PipelineStageControllerService pipelineStageControllerService;

    @Resource
    private Jenkins jenkins;

    @Value("${harbor.url}")
    private String harborUrl;


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
        String xml = JenkinsTemplate.getBuildJenkinsTemplateXml(description, jenkins.getToken());
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
     * 获取流水线列表
     *
     * @param name
     * @param current
     * @param size
     * @return
     */
    public ListVo getPipelineList(String name, Integer current, Integer size) {
        QueryWrapper<CicdPipeline> wrapper = new QueryWrapper();
        if (StringUtils.isNotEmpty(name)) {
            wrapper.like("name", name);
        }
        Page page = new Page(current, size);
        IPage list = pipelineService.page(page, wrapper);
        List<CicdPipeline> records = list.getRecords();
        List<GetPipelineListVo> listVos = new ArrayList<>();
        records.stream().forEach(o -> {
            GetPipelineListVo vo = new GetPipelineListVo();
            BeanUtil.copyProperties(o, vo);
            // 根据pipeline id查询流水线阶段内容
            if (o.getExecResult() != 2) {
                vo.setBuildStageVo(getBuildStageStatusVo(o.getName()));
            }
            listVos.add(vo);
        });
        ListVo listVo = new ListVo();
        listVo.setTotal(list.getTotal());
        listVo.setDataList(listVos);
        return listVo;
    }

    /**
     * 获取阶段状态信息
     * l
     *
     * @param name
     * @return
     */
    public BuildStageVo getBuildStageStatusVo(String name) {
        try {
            int number = jenkinsServer.getJob(name).getLastBuild().getNumber();
            BuildStageVo buildStageVo = pipelineStageControllerService.getRunningBuildStageView(name, number);
            return buildStageVo;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 修改流水线内容
     *
     * @param param
     * @return
     */
    public Boolean updatePipeline(UpdatePipelineParam param) {
        CicdPipeline cicdPipeline = new CicdPipeline();
        BeanUtil.copyProperties(param, cicdPipeline);
        return pipelineService.updateById(cicdPipeline);
    }

    /**
     * 删除流水线
     *
     * @param id
     * @return
     */
    public Boolean deletePipeline(Long id) {
        return pipelineService.removeById(id);
    }

    /**
     * 获取运行状态
     *
     * @param id
     * @return
     */
    public Integer getPipelineRunningStatus(Long id) {
        CicdPipeline cicdPipeline = pipelineService.getById(id);
        return cicdPipeline.getRunningStatus();
    }

    /**
     * 执行完成修改状态
     *
     * @param param
     * @return
     */
    public Boolean executeFinishUpdateStatus(UpdatePipelineStatusParam param) {
        CicdPipeline cicdPipeline = new CicdPipeline();
        BeanUtil.copyProperties(param, cicdPipeline);
        return pipelineService.updateById(cicdPipeline);
    }

    /**
     * 执行流水线
     *
     * @return
     */
    public Integer executePipeline(Long pipelineId, String name) {
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
                String codePullStage = JenkinsTemplate.getCodePullStage(codePullParam);
                script.append(codePullStage);
            }
            //代码检测
            if (pipelineStage.getType() == PipelineStageTypeEnum.CODE_CHECK.getType()) {
                CodeCheckParam codeCheckParam = JSONUtil.toBean(pipelineStage.getParameter(), CodeCheckParam.class);
                //来源于平台
                if (codeCheckParam.getSonarFileSource() == 2) {
                    CicdSonarqube sonarqube = sonarqubeService.getById(codeCheckParam.getSonarId());
                    String property = System.getProperty("user.dir");
                    FileWriter writer = new FileWriter(property + "/" + codeCheckParam.getSonarFileAddress() + "/" + "sonar-project.properties");
                    writer.write(sonarqube.getContent());
                }
                script.append(JenkinsTemplate.getCodeCheckStage(codeCheckParam));
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
                //来源于平台
                if (imageBuildParam.getDockerfileSource() == 2) {
                    CicdDockerfile dockerfile = dockerfileService.getById(imageBuildParam.getDockerfileId());
                    String property = System.getProperty("user.dir");
                    FileWriter writer = new FileWriter(property + "/" + imageBuildParam.getProjectName() + "/" + "Dockerfile");
                    writer.write(dockerfile.getContent());
                }
                script.append(JenkinsTemplate.getImageBuildStage(imageBuildParam));
            }
            //镜像上传
            if (pipelineStage.getType() == PipelineStageTypeEnum.IMAGE_UPLOAD.getType()) {
                ImageUploadParam imageUploadParam = JSONUtil.toBean(pipelineStage.getParameter(), ImageUploadParam.class);
                CicdRepos cicdRepos = reposService.getById(imageUploadParam.getReposId());
                script.append(JenkinsTemplate.getImageUploadStage(cicdRepos, imageUploadParam, harborUrl));
            }
        }
        script.append(" \n}");
        try {
            String jenkinsTemplateXml = JenkinsTemplate.getJenkinsTemplateXml(script.toString(), jenkins.getToken());
            jenkinsServer.updateJob(name, jenkinsTemplateXml, true);
            int nextBuildNumber = jenkinsServer.getJob(name).getNextBuildNumber();
            jenkinsServer.getJob(name).build(true);
            // 修改执行状态
            CicdPipeline cicdPipeline = new CicdPipeline();
            cicdPipeline.setRunningStatus(1);
            cicdPipeline.setId(pipelineId);
            pipelineService.updateById(cicdPipeline);
            return nextBuildNumber;
        } catch (IOException e) {
            log.error("执行流水线错误" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据名称判断流水线是否存在
     *
     * @param name
     * @return
     */
    public Boolean judgePipelineExist(String name) {
        QueryWrapper<CicdPipeline> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        CicdPipeline pipeline = pipelineService.getOne(queryWrapper);
        if (pipeline == null) {
            return false;
        } else {
            return true;
        }
    }

}
