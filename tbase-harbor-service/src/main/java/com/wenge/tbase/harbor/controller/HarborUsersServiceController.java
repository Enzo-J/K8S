package com.wenge.tbase.harbor.controller;

import com.wenge.tbase.harbor.bean.Users;
import com.wenge.tbase.harbor.result.RestResult;
import com.wenge.tbase.harbor.service.HarborRequest;
import com.wenge.tbase.harbor.service.HarborServiceService;
import com.wenge.tbase.harbor.service.HarborUsersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;

@Api("Harbor服务用户服务相关接口")
@RestController
@RequestMapping("/harbor/v1/users")
public class HarborUsersServiceController<V> {

	@Autowired
	private HarborUsersService harborUsersService;

	@ApiOperation(value = "添加用户", notes = "添加用户")
	@ApiImplicitParams({@ApiImplicitParam(name = "users", value = "用户实体", required = true, dataType = "Users")})
	@PostMapping("/addUsers")
	public RestResult<?> addUsers(@RequestBody() Users users) {
		return harborUsersService.addUsersService(users);
	}

	@ApiOperation(value = "编辑用户", notes = "编辑用户")
	@ApiImplicitParams({@ApiImplicitParam(name = "users", value = "用户实体", required = true, dataType = "Users")})
	@PostMapping("/updateUsers")
	public RestResult<?> updateUsers(@RequestBody() Users users) {
		return harborUsersService.updateUsersService(users);
	}

	@ApiOperation(value = "删除指定用户", notes = "通过用户ID删除指定用户")
	@ApiImplicitParams({@ApiImplicitParam(name = "user_id", value = "用户ID", required = true, dataType = "integer")})
	@GetMapping("/deleteUsers")
	public RestResult<?> deleteUsers(@Valid @RequestParam(required = true,value = "user_id") Integer user_id) {
		Users users = new Users();
		users.setUser_id(user_id);
		return harborUsersService.deleteUsersService(users);
	}

	@ApiOperation(value = "重置密码", notes = "通过用户ID重置密码")
	@ApiImplicitParams({@ApiImplicitParam(name = "user_id", value = "用户ID", required = true, dataType = "integer")})
	@GetMapping("/resetPassword")
	public RestResult<?> resetPassword(@Valid @RequestParam(required = true,value = "user_id") Integer user_id,
									   @Valid @RequestParam(required = true,value = "old_password") String old_password,
									   @Valid @RequestParam(required = true,value = "new_password") String new_password) {
		Users users = new Users();
		users.setUser_id(user_id);
		users.setNew_password(new_password);
		users.setOld_password(old_password);
		return harborUsersService.resetPasswordService(users);
	}

	@ApiOperation(value = "用户列表", notes = "查询用户列表-支持用户名称模糊检索")
	@ApiImplicitParams({@ApiImplicitParam(name = "users", value = "用户实体", required = true, dataType = "Users")})
	@PostMapping("/getUsersList")
	public RestResult<?> getUsersList(@RequestBody() Users users) {
		if(users.getPage()==null){
			users.setPage(1);
		}
		if(users.getPage_size()==null){
			users.setPage_size(10);
		}
		return harborUsersService.getUsersListService(users);
	}

	@ApiOperation(value = "用户详情", notes = "根据用户ID查询用户详情")
	@ApiImplicitParams({@ApiImplicitParam(name = "user_id", value = "用户ID", required = true, dataType = "integer")})
	@GetMapping("/getUsersById")
	public RestResult<?> getUsersById(@Valid @RequestParam(required = true,value = "user_id") Integer user_id) {
		Users users = new Users();
		users.setUser_id(user_id);
		return harborUsersService.getUsersByIdService(users);
	}

	
}
