package com.wenge.tbase.harbor.service.impl;

import com.wenge.tbase.harbor.bean.*;
import com.wenge.tbase.harbor.service.HarborRequest;
import com.wenge.tbase.harbor.utilsTest.JSONUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: chf
 * @ClassName: HarborRequestImpl
 * @Description: todo
 * @Date: 2020/11/27
 */
@Slf4j
@Service
public class HarborRequestImpl implements HarborRequest {

//    @Autowired
    private HarborConfig harborConfig;

    RestTemplate restTemplate = new RestTemplate();
    /**
     * harbor管理员账户
     */
    private final String harborAdminUsername = "admin";
    private final String harborAdminPassword = "Szwg%2020";

    @Override
    public void createUser(HarborUser harborUser) {
        //url
        String url = harborConfig.getApi() + "/users";

        //设置header和认证
        HttpHeaders headers = createHeaders(harborAdminUsername, harborAdminPassword);

        //拼接参数和header
        HttpEntity<String> httpEntity = new HttpEntity<>(JSONUtils.beanToJson(harborUser), headers);

        //重定义restTemplate
        RestTemplate restTemplate = new RestTemplate();

        //提交请求
        ResponseEntity<String> resp = restTemplate.exchange(url, HttpMethod.POST, httpEntity, new ParameterizedTypeReference<String>() {
        });

        if (resp.getStatusCode().value() == 201) {
            log.info("####Harbor用户【{}】创建成功", harborUser.getUsername());
        } else {
            log.info("####Harbor用户【{}】创建失败，错误信息{}：", harborUser.getUsername(), resp.getStatusCode());
        }
    }

    @Override
    public void createProject(HarborProject harborProject, String username, String password) {
        //url
        String url = harborConfig.getApi() + "/projects";

        //设置header和认证
        HttpHeaders headers = createHeaders(username, password);

        //拼接参数和header
        HttpEntity<String> httpEntity = new HttpEntity<>(JSONUtils.beanToJson(harborProject), headers);

        //重定义restTemplate
        restTemplate = new RestTemplate();

        //提交请求
        ResponseEntity<String> resp = restTemplate.exchange(url, HttpMethod.POST, httpEntity, new ParameterizedTypeReference<String>() {});

        if (resp.getStatusCode().value() == 201) {
            log.info("####Harbor项目【{}】创建成功", harborProject.getProjectName());
        } else {
            log.info("####Harbor项目【{}】创建失败，错误信息{}：", harborProject.getProjectName(), resp.getStatusCode());
        }
    }

    @Override
    public Integer queryProject(String name) {
        name = "wg";

        //url
        String url = "http://172.16.0.11/api/v2.0/projects?name=" + name;

        //设置header和认证
        HttpHeaders headers = createHeaders(harborAdminUsername, harborAdminPassword);

        //拼接参数和header
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        //重定义restTemplate
        restTemplate = new RestTemplate();

        //提交请求
        ResponseEntity<List<Object>> resp = restTemplate.exchange(url, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<List<Object>>() {
        });

//        if (resp.getStatusCode().value() == 200) {
//            if (resp.getBody() != null && resp.getBody().size() > 0) {
//                Project project = resp.getBody().get(0);
//                if (project != null) {
//                    log.info("####Harbor项目【{}】id为:{}", name, project.getProject_id());
//                    return project.getProject_id();
//                }
//            } else {
//                log.info("####Harbor项目【{}】基本信息查询失败：", name);
//            }
//        } else {
//            log.info("####Harbor项目【{}】基本信息查询失败：", name);
//        }
        return null;
    }

    @Override
    public List<Repositories> queryImagesByProjectId(Integer projectId) {
        //url
        String url = harborConfig.getApi() + "/repositories?page=1&page_size=100&project_id=" + projectId;

        //设置header和认证
        HttpHeaders headers = createHeaders(harborAdminUsername, harborAdminPassword);

        //拼接参数和header
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        //重定义restTemplate
        restTemplate = new RestTemplate();

        //提交请求
        ResponseEntity<List<Repositories>> resp = restTemplate.exchange(url, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<List<Repositories>>() {
        });

        if (resp.getStatusCode().value() == 200) {
            return resp.getBody();
        } else {
            log.info("####Harbor项目ID【{}】镜像列表获取失败：", projectId);
            return null;
        }

    }

    @Override
    public List<ImagesTags> queryImagesTagsByImageName(String imageName) {
        //url
//        String url = harborConfig.getApi() + "/repositories/" + imageName + "/tags";
        String url = "http://172.16.0.11/api/v2.0/projects/1/scanner";
        //设置header和认证
//        HttpHeaders headers = new HttpHeaders();
        HttpHeaders headers = createHeaders("admin", "Szwg%2020");
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Accept", "application/json");
        headers.add("Content-Type", "application/json");
        Map<String, Object> params = new HashMap<>();
        //拼接参数和header
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
//        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<Map<String, Object>>(params,headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> resp = restTemplate.postForEntity(url, httpEntity,String.class);
        //重定义restTemplate
        restTemplate = new RestTemplate();
//
//        //提交请求
//        ResponseEntity<List<ImagesTags>> resp = restTemplate.exchange(url, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<List<ImagesTags>>() {
////        ResponseEntity<Object> resp = restTemplate.exchange(url, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<Object>() {
//        });
        if (resp.getStatusCode().value() == 200) {
            Object string1 = resp.getBody();
            System.err.println(string1);
            return null;
        } else {
            log.info("####Harbor镜像【{}】镜像标签获取失败：", imageName);
            return null;
        }
//    return null;
    }

    /**
     * Authorization Basic认证
     *
     * @return
     */
    private static HttpHeaders createHeaders(String username, String password) {
        return new HttpHeaders() {
            {
                String auth = username + ":" + password;
                String authHeader = "Basic " + Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.US_ASCII));
                set("Authorization", authHeader);
            }
        };
    }

    public static void main(String[] args) {
        HttpHeaders headers = createHeaders("admin","Szwg%2020");
        System.err.println(headers);
    }
}
