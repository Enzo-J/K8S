package com.wenge.tbase.cicd.controller;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.client.JenkinsHttpClient;
import com.offbytwo.jenkins.helper.Range;
import com.offbytwo.jenkins.model.*;
import com.wenge.tbase.cicd.entity.dto.BaseFileEntity;
import com.wenge.tbase.cicd.entity.vo.BuildStageVo;
import com.wenge.tbase.cicd.entity.vo.K8SDeployment;
import com.wenge.tbase.cicd.entity.vo.StageVo;
import com.wenge.tbase.cicd.feignService.FeignK8SService;
import com.wenge.tbase.cicd.feignService.FeignWOSService;
import com.wenge.tbase.commons.result.ResultCode;
import com.wenge.tbase.commons.result.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Wang XingPeng
 * @since 2020-11-18
 */
@RestController
@RequestMapping("/demo")
@Slf4j
public class DemoController {

    @Value("${server.port}")
    private String port;

    @Resource
    private JenkinsServer jenkinsServer;
    @Resource
    private JenkinsHttpClient jenkinsHttpClient;
    @Resource
    private FeignK8SService k8SService;

    @Resource
    private FeignWOSService wosService;

    @GetMapping(value = "/job")
    public void getJob() {
        try {
            JobWithDetails job = jenkinsServer.getJob("tensquare_back");
            log.info(job.getNextBuildNumber() + "");
            Map<String, String> param = new HashMap<>();
            param.put("token", "wenge");
            job.build(param, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping(value = "/createJob")
    public void createJob() {
        try {
            /**创建一个流水线任务，且设置一个简单的脚本**/
            // 创建 Pipeline 脚本
            String script = "node(){ \n" +
                    "echo 'hello world!' \n" +
                    "}";
            // xml配置文件，且将脚本加入到配置中
            String xml = "<flow-definition plugin=\"workflow-job@2.32\">\n" +
                    "<description>测试项目</description>\n" +
                    "<definition class=\"org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition\" plugin=\"workflow-cps@2.85\">\n" +
                    "<script>" + script + "</script>\n" +
                    "<sandbox>true</sandbox>\n" +
                    "</definition>\n" +
                    "</flow-definition>";
            // 创建 Job
            jenkinsServer.createJob("wang-test-job", xml, true);
            // data
            String data = "json: {\"\": \"0\", \n" +
                    "\"credentials\": {\"scope\": \"GLOBAL\", \"username\": \"test\", \"password\": \"test\", \"$redact\": \"password\", \"id\": \"\", \"description\": \"11111\", \"stapler-class\": \"com.cloudbees.plugins.credentials.impl.UsernamePasswordCredentialsImpl\", \"$class\": \"com.cloudbees.plugins.credentials.impl.UsernamePasswordCredentialsImpl\"}, \"Jenkins-Crumb\": \"af4dc308c3a68a75727715b84a3019ae576a1ab080a73b8cb88134552bd0d5ad\"}";
            String url = "/credentials/store/system/domain/_/createCredentials";
            jenkinsHttpClient.post(url, data, null, null, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/createCredentials")
    public void createCredentials() throws IOException {
        String url = "/credentials/";
        List<NameValuePair> data = new ArrayList<>();
        final HttpResponse httpResponse = jenkinsHttpClient.post_form_with_result(url, data, true);
        System.out.println(httpResponse);
    }

    @GetMapping("/getXml")
    public String getXml() {
        try {
            String jobXml = jenkinsServer.getJobXml("test-pipeline");
            Map<String, String> param = new HashMap<>();
            param.put("token", "123456");
            QueueReference build = jenkinsServer.getJob("test-pipeline").build(param, true);
            log.info(jobXml);
            return jobXml;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping(value = "/createDeployment")
    public void createDeployment() {
        K8SDeployment deployment = new K8SDeployment();
        deployment.setNamespace("dp");
        deployment.setName("cicd-service");
        deployment.setImageUrl("172.16.0.11/app-manage-platform/tbase-cicd-service:v1");
        deployment.setPort(10010);
        ResultVO resultVO = k8SService.createDeployment(deployment);
        Object data = resultVO.getData();
        if (data != null) {
            System.out.println(data);
        }
    }

    @GetMapping(value = "/createService")
    public void createService() {
        K8SDeployment deployment = new K8SDeployment();
        deployment.setNamespace("dp");
        deployment.setName("cicd-service");
        deployment.setPort(10010);
        ResultVO resultVO = k8SService.createService(deployment);
        Object data = resultVO.getData();
        if (data != null) {
            System.out.println(data);
        }
    }

    @GetMapping(value = "/getJenkinsLog")
    public String getJenkinsLog() {
        try {
            JobWithDetails job = jenkinsServer.getJob("test-101");
            BuildWithDetails details = job.getLastBuild().details();
            return details.getConsoleOutputHtml();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping(value = "/getBuildList")
    public ResultVO getBuildList() {
        try {
            JobWithDetails job = jenkinsServer.getJob("test-101");
            Range range = Range.build().from(1).to(3);
            List<Build> allBuilds = job.getAllBuilds(range);
            for (Build build : allBuilds) {
                System.out.println(build.getNumber());
                System.out.println(build.details().getTimestamp());
                System.out.println(build.details().getResult());
            }
            return new ResultVO(ResultCode.SUCCESS, allBuilds);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping(value = "/getBuildStageView")
    public void getBuildStageView() {
        String url = "/job/app-manage-platform-pipeline/wfapi/runs";
        List<NameValuePair> data = new ArrayList<>();
        try {
            HttpResponse httpResponse = jenkinsHttpClient.post_form_with_result(url, data, true);

            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity, "UTF-8");
                List<BuildStageVo> buildStageVos = JSONUtil.toList(JSONUtil.parseArray(result), BuildStageVo.class);
                if (buildStageVos != null) {
                    BuildStageVo vo = buildStageVos.get(0);
                    if (vo.getStages() != null) {
                        for (StageVo stageVo : vo.getStages()) {
                            System.out.println(stageVo.getName());
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/upload")
    public void upload(@RequestBody MultipartFile file) {
        JSONObject upload = wosService.upload(file, "szwg-bucket");
        Map<String, Object> msg = JSONUtil.toBean(upload, Map.class);
        List<BaseFileEntity> objects = JSONUtil.toList(JSONUtil.parseArray(msg.get("msg")),BaseFileEntity.class);
        System.out.println(objects.get(0).getUrl());
    }

    @GetMapping("/writerFile")
    public void writerFile(String content){
        try {
            String path = System.getProperty("user.dir");
            FileWriter writer = new FileWriter(path + "/tbase-cicd-service/Dockerfile");
            writer.append(content);
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
//        String property = System.getProperty("user.dir");
//        System.out.println(property);
//        FileReader fileReader = new FileReader(property + "/" + "tbase-cicd-service/src/main/sonar/sonar-project.properties");
//        String result = fileReader.readString();
//        System.out.println(result);
        final DateTime date = DateUtil.date(1608876537315L);
        System.out.println(date);

        final long l = DateUtil.current(false);
        System.out.println(l);
    }
}

