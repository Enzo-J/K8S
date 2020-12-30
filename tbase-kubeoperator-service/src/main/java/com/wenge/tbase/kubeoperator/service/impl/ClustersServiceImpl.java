package com.wenge.tbase.kubeoperator.service.impl;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.wenge.tbase.commons.entity.RestResult;
import com.wenge.tbase.commons.exception.APIException;
import com.wenge.tbase.kubeoperator.config.ConstantConfig;
import com.wenge.tbase.kubeoperator.service.ClustersService;

@Service
public class ClustersServiceImpl implements ClustersService {

	@Override
	public RestResult<?> showAllClusters(String token) {
		try {
	        RestTemplate restTemplate = new RestTemplate();
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        headers.add("Authorization", "Bearer " +token);
	        ResponseEntity<Map> response = restTemplate.exchange(
	        		ConstantConfig.kubeClustersAddr,
	                HttpMethod.GET,
	                new HttpEntity<String>(headers),
	                Map.class);
	        return RestResult.ok(response.getBody());
	    } catch (Exception e) {
	        throw new APIException("请求失败");
	    }
	}

	@Override
	public RestResult<?> showACluster(String token, String name) {
		try {
	        RestTemplate restTemplate = new RestTemplate();
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        headers.add("Authorization", "Bearer " +token);
	        ResponseEntity<Map> response = restTemplate.exchange(
	        		ConstantConfig.kubeClustersAddr+"/"+name,
	                HttpMethod.GET,
	                new HttpEntity<String>(headers),
	                Map.class);
	        return RestResult.ok(response.getBody());
	    } catch (Exception e) {
	        throw new APIException("请求失败");
	    }
	}

	@Override
	public RestResult<?> deleteACluster(String token, String name) {
		try {
	        RestTemplate restTemplate = new RestTemplate();
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        headers.add("Authorization", "Bearer " +token);
	        ResponseEntity<Map> response = restTemplate.exchange(
	        		ConstantConfig.kubeClustersAddr+"/"+name,
	                HttpMethod.DELETE,
	                new HttpEntity<String>(headers),
	                Map.class);
	        return RestResult.ok(response.getBody());
	    } catch (Exception e) {
	        throw new APIException("删除失败");
	    }
	}

}
