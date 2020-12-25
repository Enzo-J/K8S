package com.wenge.tbase.kubeoperator.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.wenge.tbase.commons.exception.APIException;
import com.wenge.tbase.commons.result.ResultVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "登录认证接口")
@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {

	
	@GetMapping("/token")
    @ApiOperation("获取token")
	public ResultVO login(String username, String password) {
		try {
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        RestTemplate restTemplate = new RestTemplate();
	        HashMap<Object, Object> map = new HashMap<>();
	        map.put("username", username);
	        map.put("password", password);
	        map.put("language", "简体中文");
	        HttpEntity<String> requestEntity = new HttpEntity<>(JSON.toJSONString(map), headers);
	        ResponseEntity<Map> entity = restTemplate.postForEntity("http://172.16.0.14/api/v1/auth/login", requestEntity, Map.class);
	        
	        return new ResultVO(entity.getBody());
	    } catch (Exception e) {
	        throw new APIException("请求失败");
	    }
		
	}
}
