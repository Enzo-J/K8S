package com.wenge.tbase.cicd.controller;


import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;
import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.client.JenkinsHttpClient;
import com.offbytwo.jenkins.model.JobWithDetails;
import com.offbytwo.jenkins.model.QueueReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static void main(String[] args) {
        String property = System.getProperty("user.dir");
        System.out.println(property);
        FileReader fileReader = new FileReader(property + "/" + "tbase-cicd-service/src/main/sonar/sonar-project.properties");
        String result = fileReader.readString();
        System.out.println(result);
    }
}

