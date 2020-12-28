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
import com.wenge.tbase.kubeoperator.service.ProjectsService;

@Service
public class ProjectsServiceImpl implements ProjectsService {

	@Override
	public RestResult<?> showAllProjects(String token) {
		try {
	        RestTemplate restTemplate = new RestTemplate();
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        headers.add("Authorization", "Bearer " +token);
	        ResponseEntity<Map> response = restTemplate.exchange(
	        		ConstantConfig.kubeProjectsAddr,
	                HttpMethod.GET,
	                new HttpEntity<String>(headers),
	                Map.class);
	        return RestResult.ok(response.getBody());
	    } catch (Exception e) {
	        throw new APIException("请求失败");
	    }
	}

	@Override
	public RestResult<?> showAProject(String token, String name) {
		try {
	        RestTemplate restTemplate = new RestTemplate();
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        headers.add("Authorization", "Bearer " +token);
	        ResponseEntity<Map> response = restTemplate.exchange(
	        		ConstantConfig.kubeProjectsAddr+"/"+name,
	                HttpMethod.GET,
	                new HttpEntity<String>(headers),
	                Map.class);
	        return RestResult.ok(response.getBody());
	    } catch (Exception e) {
	        throw new APIException("请求失败");
	    }
	}

	@Override
	public RestResult<?> create(String token, String description, String name) {
		try {
	        RestTemplate restTemplate = new RestTemplate();
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        headers.add("Authorization", "Bearer " +token);
	        HashMap<Object, Object> map = new HashMap<>();
	        map.put("description", description);
	        map.put("name", name);
	        HttpEntity<String> requestEntity = new HttpEntity<>(JSON.toJSONString(map), headers);
	        ResponseEntity<Map> response = restTemplate.exchange(
	        		ConstantConfig.kubeProjectsAddr,
	                HttpMethod.POST,
	                requestEntity,
	                Map.class);
	        return RestResult.ok();
	    } catch (Exception e) {
	        throw new APIException("创建失败");
	    }
	}

	@Override
	public RestResult<?> deleteAProject(String token, String name) {
		try {
	        RestTemplate restTemplate = new RestTemplate();
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        headers.add("Authorization", "Bearer " +token);
	        ResponseEntity<Map> response = restTemplate.exchange(
	        		ConstantConfig.kubeProjectsAddr+"/"+name,
	                HttpMethod.DELETE,
	                new HttpEntity<String>(headers),
	                Map.class);
	        return RestResult.ok(response.getBody());
	    } catch (Exception e) {
	        throw new APIException("删除失败");
	    }
	}

	@Override
	public RestResult<?> updateAProject(String token, String description, String name) {//需要传入id
		// TODO Auto-generated method stub
		return null;
	}

}
