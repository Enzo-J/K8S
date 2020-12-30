package com.wenge.tbase.kubeoperator.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.wenge.tbase.commons.entity.RestResult;
import com.wenge.tbase.commons.exception.APIException;
import com.wenge.tbase.kubeoperator.config.ConstantConfig;
import com.wenge.tbase.kubeoperator.service.HostsService;

@Service
public class HostsServiceImpl implements HostsService {

	@Override
	public RestResult<?> showAllHosts(String token) {
		try {
	        RestTemplate restTemplate = new RestTemplate();
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        headers.add("Authorization", "Bearer " +token);
	        ResponseEntity<Map> response = restTemplate.exchange(
	        		ConstantConfig.kubeHostsAddr,
	                HttpMethod.GET,
	                new HttpEntity<String>(headers),
	                Map.class);
	        return RestResult.ok(response.getBody());
	    } catch (Exception e) {
	        throw new APIException("请求失败");
	    }
	}
	
	@Override
	public RestResult<?> showAHosts(String token, String name) {
		try {
	        RestTemplate restTemplate = new RestTemplate();
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        headers.add("Authorization", "Bearer " +token);
	        ResponseEntity<Map> response = restTemplate.exchange(
	        		ConstantConfig.kubeHostsAddr+"/"+name,
	                HttpMethod.GET,
	                new HttpEntity<String>(headers),
	                Map.class);
	        return RestResult.ok(response.getBody());
	    } catch (Exception e) {
	        throw new APIException("请求失败");
	    }
	}

	
	@Override
	public RestResult<?> create(String token, String credentialId, String ip, String name, int port) {
		try {
	        RestTemplate restTemplate = new RestTemplate();
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        headers.add("Authorization", "Bearer " +token);
	        HashMap<Object, Object> map = new HashMap<>();
	        map.put("credentialId", credentialId);
	        map.put("ip", ip);
	        map.put("name", name);
	        map.put("port", port);
	        HttpEntity<String> requestEntity = new HttpEntity<>(JSON.toJSONString(map), headers);
	        ResponseEntity<Map> response = restTemplate.exchange(
	        		ConstantConfig.kubeHostsAddr,
	                HttpMethod.POST,
	                requestEntity,
	                Map.class);
	        return RestResult.ok();
	    } catch (Exception e) {
	        throw new APIException("创建失败");
	    }
	}

	@Override
	public RestResult<?> deleteAHosts(String token, String name) {
		try {
	        RestTemplate restTemplate = new RestTemplate();
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        headers.add("Authorization", "Bearer " +token);
	        ResponseEntity<Map> response = restTemplate.exchange(
	        		ConstantConfig.kubeHostsAddr+"/"+name,
	                HttpMethod.DELETE,
	                new HttpEntity<String>(headers),
	                Map.class);
	        return RestResult.ok(response.getBody());
	    } catch (Exception e) {
	        throw new APIException("删除失败");
	    }
	}
}
