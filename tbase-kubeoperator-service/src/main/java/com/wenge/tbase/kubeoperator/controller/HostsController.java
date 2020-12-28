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
import com.wenge.tbase.kubeoperator.service.HostsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "节点接口")
@RestController
@RequestMapping("/hosts")
@Validated
public class HostsController {
	@Autowired
	private HostsService hostsService;
	
	@GetMapping("/showallhosts")
    @ApiOperation("获取所有节点")
	public RestResult<?>  showAllHosts(String token) {
		try {
	        return hostsService.showAllHosts(token);
	    } catch (Exception e) {
	        throw new APIException("请求失败");
	    }
	}
	
	@GetMapping("/showahosts")
    @ApiOperation("获取一个节点")
	public RestResult<?>  showAHosts(String token,String name) {
		try {
	        return hostsService.showAHosts(token, name);
	    } catch (Exception e) {
	        throw new APIException("请求失败");
	    }
	}
	
	@PostMapping("/create")
    @ApiOperation("创建一个节点")
	public RestResult<?> creat(String token, String credentialId, String ip, String name, int port) {
		try {
	        return hostsService.create(token, credentialId, ip, name, port);
	    } catch (Exception e) {
	        throw new APIException("请求失败");
	    }
	}
	
	@DeleteMapping("/delete")
    @ApiOperation("删除一个节点")
	public RestResult<?> deleteAHosts(String token,String name) {
		try {
	        return hostsService.deleteAHosts(token, name);
	    } catch (Exception e) {
	        throw new APIException("请求失败");
	    }
	}
	
}
