package com.wenge.tbase.harbor.service.impl;

import com.alibaba.fastjson.JSON;
import com.wenge.tbase.harbor.bean.*;
import com.wenge.tbase.harbor.result.RestResult;
import com.wenge.tbase.harbor.result.WengeStatusEnum;
import com.wenge.tbase.harbor.service.HarborWebhookService;
import com.wenge.tbase.harbor.utilsTest.JSONUtils;
import com.wenge.tbase.harbor.utilscors.HttpClientUtil;
import com.wenge.tbase.harbor.utilscors.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class HarborWebhookServiceImpl implements HarborWebhookService {

    @Value("${harbor.host}")
    private String host;
    @Value("${harbor.path}")
    private String path;
    @Value("${harbor.url}")
    private String url;
    @Value("${harbor.encoding}")
    private String encoding;
    @Value("${harbor.timeout}")
    private Long timeout;

    @Override
    public RestResult<?> getWebhookListService(Integer project_id) {
        try {
            Map<String,String> mapHeader = HttpClientUtil.getBasicHearder();
//			https://172.16.0.11/api/v2.0/projects/6/webhook/policies
            System.out.println(url+"/projects/"+project_id+"/webhook/policies");
            HttpResponse response = HttpUtils.doGetWithHeader(url+"/projects/"+project_id+"/webhook/policies", mapHeader);
            String string = EntityUtils.toString(response.getEntity());
            Object jsonObject = JSON.parse(string);
            return RestResult.ok(jsonObject);
        } catch (Exception e) {
            System.err.println(e);
            return RestResult.error(WengeStatusEnum.SYSTEM_ERROR.getMsg());
        }
    }

    @Override
    public RestResult<?> addWebhookByProjectIdService(Integer project_id, Webhook webhook) {
        try {
            Map<String,String> mapHeader = HttpClientUtil.getBasicHearder();
            System.out.println(url+"/projects/"+project_id+"/webhook/policies");
            HttpResponse response = HttpUtils.doPost(host,path+"/projects/"+project_id+"/webhook/policies", mapHeader,null,JSON.toJSONString(webhook));
            String string = EntityUtils.toString(response.getEntity());
            Object jsonObject = JSON.parse(string);
            return RestResult.ok(jsonObject);
        } catch (Exception e) {
            System.err.println(e);
            return RestResult.error(WengeStatusEnum.SYSTEM_ERROR.getMsg());
        }
    }
}
