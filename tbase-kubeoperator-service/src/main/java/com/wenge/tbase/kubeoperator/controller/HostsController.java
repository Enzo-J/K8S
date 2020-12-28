package com.wenge.tbase.kubeoperator.controller;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.wenge.tbase.commons.exception.APIException;
import com.wenge.tbase.commons.result.ResultVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "节点接口")
@RestController
@RequestMapping("/hosts")
@Validated
public class HostsController {
	@GetMapping("/showallhosts")
    @ApiOperation("获取所有节点")
	public ResultVO showAllHosts(String token) {
		try {
	        RestTemplate restTemplate = new RestTemplate();
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        headers.add("Authorization", "Bearer " +token);
	        ResponseEntity<Map> response = restTemplate.exchange(
	                "http://172.16.0.14/api/v1/hosts",
	                HttpMethod.GET,
	                new HttpEntity<String>(headers),
	                Map.class);
	        return new ResultVO(response.getBody());
	    } catch (Exception e) {
	        throw new APIException("请求失败");
	    }
		
	}
}
