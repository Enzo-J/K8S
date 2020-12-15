package com.wenge.tbase.k8s.controller;


import com.wenge.tbase.commons.result.ResultVO;
import com.wenge.tbase.k8s.bean.vo.K8SDeployment;
import com.wenge.tbase.k8s.service.K8SService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Api(tags = "大屏展示K8S集群相关信息")
@RestController
@RequestMapping("/Screen")
@Validated
public class ScreenInfoController {
    @Resource
    private K8SService k8SService;

    @ApiOperation("获取命名空间信息")
    @GetMapping("/getNameSpaceInfo")
    public ResultVO getNameSpaceInfo(){
        List result=k8SService.getAllNameSpace();
        return new ResultVO(result);
    }
    @ApiOperation("获取节点信息")
    @GetMapping("/getNodesInfo")
    public ResultVO getNodesInfo(){
        Map result=k8SService.getNodeInfo();
        return new ResultVO(result);
    }

    @ApiOperation("获取存储信息")
    @GetMapping("/getStorageInfo")
    public ResultVO getStorageInfo(){
        Map result=k8SService.findStorageInfo();
        return new ResultVO(result);
    }

    @ApiOperation("查看存储类")
    @GetMapping("/getStorageDescribeInfo")
    @ApiImplicitParams(@ApiImplicitParam(name = "name",value = "存储类名称",dataType = "String"))
    public ResultVO getStorageDescribeInfo(@RequestParam String name){
        String result=k8SService.findStorageDescribeInfo(name);
        return new ResultVO(result);
    }

    @ApiOperation("创建deployment")
    @PostMapping("/createDeployment")
    @ApiImplicitParams(@ApiImplicitParam(name = "K8SDeployment",value = "部署对象",dataType = "K8SDeployment"))
    public ResultVO createDeployment(@RequestBody K8SDeployment k8SDeployment){
        String result=k8SService.createDeployment(k8SDeployment);
        return new ResultVO(result);
    }

    @ApiOperation("创建service")
    @PostMapping("/createService")
    @ApiImplicitParams(@ApiImplicitParam(name = "K8SDeployment",value = "Service对象",dataType = "K8SDeployment"))
    public ResultVO createService(@RequestBody K8SDeployment k8SDeployment){
        String result=k8SService.createService(k8SDeployment);
        return new ResultVO(result);
    }
}
