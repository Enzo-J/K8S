package com.wenge.tbase.kubeoperator.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.wenge.tbase.commons.entity.RestResult;
import com.wenge.tbase.commons.result.ResultCode;
import com.wenge.tbase.kubeoperator.config.ConstantConfig;
import com.wenge.tbase.kubeoperator.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService{

	@Override
    public RestResult<?> getToken(String username, String password) {
        try {
        	
        	HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        RestTemplate restTemplate = new RestTemplate();
	        HashMap<Object, Object> map = new HashMap<>();
	        map.put("username", username);
	        map.put("password", password);
	        map.put("language", "简体中文");
	        HttpEntity<String> requestEntity = new HttpEntity<>(JSON.toJSONString(map), headers);
	        ResponseEntity<Map> entity = restTemplate.postForEntity(ConstantConfig.kubeAuthAddr, requestEntity, Map.class);
            return RestResult.ok(entity.getBody());
        } catch (Exception e) {
            return RestResult.error(ResultCode.NOT_FIND_RESOURCE.getMsg());
        }
    }
}
