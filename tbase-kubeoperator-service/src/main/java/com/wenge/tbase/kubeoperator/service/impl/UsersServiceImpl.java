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
import com.wenge.tbase.kubeoperator.service.UsersService;

@Service
public class UsersServiceImpl implements UsersService{

	@Override
	public RestResult<?> showAllUsers(String token) {
        try {
        	RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Authorization", "Bearer " +token);
            ResponseEntity<Map> response = restTemplate.exchange(
            		ConstantConfig.kubeUsersAddr,
                    HttpMethod.GET,
                    new HttpEntity<String>(headers),
                    Map.class);
	        return RestResult.ok(response.getBody());
	    } catch (Exception e) {
	        throw new APIException("请求失败");
	    }
	}

	@Override
	public RestResult<?> showAUser(String token, String name) {
		try {
	        RestTemplate restTemplate = new RestTemplate();
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        headers.add("Authorization", "Bearer " +token);
	        ResponseEntity<Map> response = restTemplate.exchange(
	        		ConstantConfig.kubeUsersAddr+"/"+name,
	                HttpMethod.GET,
	                new HttpEntity<String>(headers),
	                Map.class);
	        return RestResult.ok(response.getBody());
	    } catch (Exception e) {
	        throw new APIException("请求失败");
	    }
	}

	@Override
	public RestResult<?> create(String token, String email, String name, String password, int isAdmin) {
		try {
	        RestTemplate restTemplate = new RestTemplate();
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        headers.add("Authorization", "Bearer " +token);
	        HashMap<Object, Object> map = new HashMap<>();
	        boolean isadmin = true;
	        if(isAdmin == 0) {
	        	isadmin = false;
	        }
	        map.put("name", name);
	        map.put("password", password);
	        map.put("isAdmin", isadmin);
	        HttpEntity<String> requestEntity = new HttpEntity<>(JSON.toJSONString(map), headers);
	        ResponseEntity<Map> response = restTemplate.exchange(
	        		ConstantConfig.kubeUsersAddr,
	                HttpMethod.POST,
	                requestEntity,
	                Map.class);
	        return RestResult.ok();
	    } catch (Exception e) {
	        throw new APIException("创建失败");
	    }
	}

	@Override
	public RestResult<?> deleteAUser(String token, String name) {
		try {
	        RestTemplate restTemplate = new RestTemplate();
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        headers.add("Authorization", "Bearer " +token);
	        ResponseEntity<Map> response = restTemplate.exchange(
	        		ConstantConfig.kubeUsersAddr+"/"+name,
	                HttpMethod.DELETE,
	                new HttpEntity<String>(headers),
	                Map.class);
	        return RestResult.ok(response.getBody());
	    } catch (Exception e) {
	        throw new APIException("删除失败");
	    }
	}

	@Override
	public RestResult<?> updateAUser(String token, String email, String name, String password) {
		// TODO Auto-generated method stub
		return null;
	}

}
