package com.wenge.tbase.kubeoperator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wenge.tbase.commons.entity.RestResult;
import com.wenge.tbase.commons.exception.APIException;
import com.wenge.tbase.kubeoperator.service.UsersService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "用户接口")
@RestController
@RequestMapping("/users")
@Validated
public class UserController {

	@Autowired
	private UsersService usersService;
	
	
	@GetMapping("/showallusers")
    @ApiOperation("获取所有用户")
	public RestResult<?>  showAllUsers(String token) {
		try {
	        return usersService.showAllUsers(token);
	    } catch (Exception e) {
	        throw new APIException("请求失败");
	    }
	}
	
	@GetMapping("/showauser")
    @ApiOperation("获取一个用户")
	public RestResult<?>  showAHosts(String token,String name) {
		try {
	        return usersService.showAUser(token, name);
	    } catch (Exception e) {
	        throw new APIException("请求失败");
	    }
	}
	
	@PostMapping("/create")
    @ApiOperation("创建一个节点")
	public RestResult<?> creat(String token, String email, String name, String password, int isAdmin) {
		try {
	        return usersService.create(token, email, name, password, isAdmin);
	    } catch (Exception e) {
	        throw new APIException("请求失败");
	    }
	}
	
	@DeleteMapping("/delete")
    @ApiOperation("删除一个用户")
	public RestResult<?> deleteAHosts(String token,String name) {
		try {
	        return usersService.deleteAUser(token, name);
	    } catch (Exception e) {
	        throw new APIException("请求失败");
	    }
	}
	
	
}
