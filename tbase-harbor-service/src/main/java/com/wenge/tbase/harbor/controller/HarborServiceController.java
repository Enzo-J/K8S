package com.wenge.tbase.harbor.controller;

import com.wenge.tbase.harbor.bean.Artifacts;
import com.wenge.tbase.harbor.bean.Projects;
import com.wenge.tbase.harbor.bean.Repositories;
import com.wenge.tbase.harbor.result.RestResult;
import com.wenge.tbase.harbor.result.WengeStatusEnum;
import com.wenge.tbase.harbor.service.HarborServiceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api("Harbor服务服务相关接口")
@RestController
@RequestMapping("/harbor/v1/service")
public class HarborServiceController<V> {

	@Autowired
	private HarborServiceService harborServiceService;



	@ApiOperation(value = "获取项目（应用）列表", notes = "获取项目（应用）列表-支持项目名称检索")
	@ApiImplicitParams({@ApiImplicitParam(name = "projects", value = "项目实体", required = true, dataType = "Projects")})
	@PostMapping("/getProjectsList")
	public RestResult<?> getProjectsList(@RequestBody() Projects projects) {
		if(projects.getPage()==null){
			projects.setPage(1);
		}
		if(projects.getPage_size()==null){
			projects.setPage_size(10);
		}
		return harborServiceService.getProjectsListService(projects);
	}

	@ApiOperation(value = "获取镜像列表", notes = "根据项目名称获取当前项目下的镜像列表-支持镜像名称检索")
	@ApiImplicitParams({@ApiImplicitParam(name = "repositories", value = "镜像实体", required = true, dataType = "Repositories")})
	@PostMapping("/getRepositoriesList")
	public RestResult<?> getRepositoriesList(@RequestBody() Repositories repositories) {
		if(repositories.getPage()==null){
			repositories.setPage(1);
		}
		if(repositories.getPage_size()==null){
			repositories.setPage_size(10);
		}
		if(StringUtils.isBlank(repositories.getProject_name())){
			return RestResult.error("项目名称不能为空",WengeStatusEnum.SYSYTM_CLIENT_ERROR.getCode());
		}
		return harborServiceService.getRepositoriesListService(repositories);
	}

	@ApiOperation(value = "获取镜像版本（Artifacts）列表", notes = "根据镜像名称获取当前镜像的所有版本列表-支持类型选择检索")
	@ApiImplicitParams({@ApiImplicitParam(name = "artifacts", value = "镜像版本实体", required = true, dataType = "Artifacts")})
	@PostMapping("/getArtifactsList")
	public RestResult<?> getArtifactsList(@RequestBody() Artifacts artifacts) {
		if(artifacts.getPage()==null){
			artifacts.setPage(1);
		}
		if(artifacts.getPage_size()==null){
			artifacts.setPage_size(10);
		}
		if(StringUtils.isBlank(artifacts.getRepository_name())){
			return RestResult.error("镜像名称不能为空",WengeStatusEnum.SYSYTM_CLIENT_ERROR.getCode());
		}
		return harborServiceService.getArtifactsListService(artifacts);
	}

	@ApiOperation(value = "创建项目", notes = "创建项目")
	@ApiImplicitParams({@ApiImplicitParam(name = "projects", value = "项目实体", required = true, dataType = "Projects")})
	@PostMapping("/addProject")
	public RestResult<?> addProject(@RequestBody Projects projects) {
		return harborServiceService.addProjectsService(projects);
	}

	@ApiOperation(value = "获取项目概要", notes = "根据项目的ID获取项目的概要信息")
	@ApiImplicitParams({@ApiImplicitParam(name = "project_id", value = "项目ID", required = true, dataType = "Int")})
	@PostMapping("/getProjectSummaryById")
	public RestResult<?>  getProjectSummaryById (@Valid @RequestParam(required = true,value = "project_id") Integer project_id) {
		return harborServiceService.getProjectSummaryByIdService(project_id);
	}

	@ApiOperation(value = "获取项目（应用）以及镜像统计", notes = "获取项目（应用）以及项目下镜像的数量统计及其公私有镜像统计")
	@GetMapping("/getProjectStatistics")
	public RestResult<?> getProjectStatistics(){
		return harborServiceService.getProjectStatisticsService();
	}

	@ApiOperation(value = "获取所以项目（应用）占用的存储信息", notes = "获取占用的存储信息-包含总数、已占用（返回值是一个kb为单位的long型，前端展示需要转换 1024）")
	@GetMapping("/getProjectStorage")
	public RestResult<?> getProjectStorage(){
		return harborServiceService.getProjectStorageService();
	}


	
}
