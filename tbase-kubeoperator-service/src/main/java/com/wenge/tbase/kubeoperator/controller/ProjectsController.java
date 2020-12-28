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
import com.wenge.tbase.kubeoperator.service.ProjectsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "项目接口")
@RestController
@RequestMapping("/projects")
@Validated
public class ProjectsController {

	@Autowired
	private ProjectsService projectsService;
	
	@GetMapping("/showallprojects")
    @ApiOperation("获取所有项目")
	public RestResult<?>  showAllProjects(String token) {
		try {
	        return projectsService.showAllProjects(token);
	    } catch (Exception e) {
	        throw new APIException("请求失败");
	    }
	}
	
	@GetMapping("/showaproject")
    @ApiOperation("获取一个项目")
	public RestResult<?>  showAProject(String token,String name) {
		try {
	        return projectsService.showAProject(token, name);
	    } catch (Exception e) {
	        throw new APIException("请求失败");
	    }
	}
	
	@PostMapping("/create")
    @ApiOperation("创建一个项目")
	public RestResult<?> creat(String token, String description, String name) {
		try {
	        return projectsService.create(token, description, name);
	    } catch (Exception e) {
	        throw new APIException("请求失败");
	    }
	}
	
	@DeleteMapping("/delete")
    @ApiOperation("删除一个节点")
	public RestResult<?> deleteAProject(String token,String name) {
		try {
	        return projectsService.deleteAProject(token, name);
	    } catch (Exception e) {
	        throw new APIException("请求失败");
	    }
	}
	
}
