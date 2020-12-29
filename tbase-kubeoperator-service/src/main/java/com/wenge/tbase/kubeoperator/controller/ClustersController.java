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
import com.wenge.tbase.kubeoperator.service.ClustersService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "集群接口")
@RestController
@RequestMapping("/clusters")
@Validated
public class ClustersController {

	@Autowired
	private ClustersService clustersService;
	
	@GetMapping("/showallclusters")
    @ApiOperation("获取所有集群")
	public RestResult<?>  showAllHosts(String token) {
		try {
	        return clustersService.showAllClusters(token);
	    } catch (Exception e) {
	        throw new APIException("请求失败");
	    }
	}
	
	@GetMapping("/showacluster")
    @ApiOperation("获取一个节点")
	public RestResult<?>  showACluster(String token,String name) {
		try {
	        return clustersService.showACluster(token, name);
	    } catch (Exception e) {
	        throw new APIException("请求失败");
	    }
	}
	
	@DeleteMapping("/delete")
    @ApiOperation("删除一个集群")
	public RestResult<?> deleteACluster(String token,String name) {
		try {
	        return clustersService.deleteACluster(token, name);
	    } catch (Exception e) {
	        throw new APIException("请求失败");
	    }
	}
}
