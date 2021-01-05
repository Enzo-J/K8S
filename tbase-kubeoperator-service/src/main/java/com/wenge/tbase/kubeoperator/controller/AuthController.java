package com.wenge.tbase.kubeoperator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wenge.tbase.commons.entity.RestResult;
import com.wenge.tbase.commons.exception.APIException;
import com.wenge.tbase.kubeoperator.service.AuthService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "登录认证接口")
@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {

	@Autowired
	private AuthService authService;
	
	@GetMapping("/token")
    @ApiOperation("获取token")
	public RestResult<?> login(String username, String password) {
		try {
	        return authService.getToken(username, password);
	    } catch (Exception e) {
	        throw new APIException("请求失败");
	    }
	}
}
