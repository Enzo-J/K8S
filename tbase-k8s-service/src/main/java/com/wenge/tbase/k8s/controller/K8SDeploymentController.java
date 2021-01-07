package com.wenge.tbase.k8s.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.common.collect.Lists;
import com.wenge.tbase.commons.result.ResultVO;
import com.wenge.tbase.k8s.bean.vo.K8SDeployment;
import com.wenge.tbase.k8s.bean.vo.deployment.K8SDeploymentCreate;
import com.wenge.tbase.k8s.service.K8SService;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.api.model.apps.DeploymentList;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.Arrays;
import java.util.List;

@Api(tags = "应用管理平台Deployment模块")
@RestController
@RequestMapping("/Screen/Deployment")
@Validated
@Slf4j
public class K8SDeploymentController {
    @Resource
    private K8SService k8SService;
    @Autowired
    private ObjectMapper objectMapper;

    @ApiOperation("创建deployment")
    @PostMapping("/createDeployment")
    @ApiImplicitParams(@ApiImplicitParam(name = "K8SDeployment", value = "部署对象", dataType = "K8SDeployment"))
    public ResultVO createDeployment(@RequestBody K8SDeploymentCreate deploymentInfo) {
        try {
            Deployment deployment = k8SService.createDeployment(deploymentInfo);
            return ResultVO.success(deployment);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResultVO.fail(e.getMessage());
        }
    }

    @ApiOperation("查看Deployment详细信息")
    @GetMapping("/findDeploymentDetail")
    @ApiImplicitParams({@ApiImplicitParam(name = "deploymentName", value = "deployment名称", dataType = "String",
            required = true),
            @ApiImplicitParam(name = "namespace", value = "名称空间", dataType = "String", required = true)})
    public ResultVO findDeploymentDetail(@NotBlank @RequestParam String deploymentName,
                                         @NotBlank @RequestParam String namespace) {
        Deployment deploymentDetail = k8SService.findDeploymentDetail(deploymentName, namespace);
        return new ResultVO(deploymentDetail);
    }

    @ApiOperation("查询名称空间下的所有Deployment")
    @GetMapping("/listDeployment")
    @ApiImplicitParams(@ApiImplicitParam(name = "namespace", value = "名称空间", dataType = "String", required = true))
    public ResultVO listDeployment(@NotBlank @RequestParam String namespace) {
        DeploymentList deploymentList = k8SService.listDeployment(namespace);
        return new ResultVO(deploymentList);
    }

    @ApiOperation("删除单个deployment")
    @PostMapping("/delDeployment")
    @ApiImplicitParams({@ApiImplicitParam(name = "spacename", value = "名称空间", dataType = "String", required = true),
            @ApiImplicitParam(name = "deploymentName", value = "deployment名称", dataType = "String", required = true)})
    public ResultVO delDeployment(@NotBlank @RequestParam String spacename,
                                  @NotBlank @RequestParam String deploymentName) {
        boolean result = k8SService.delDeployment(spacename, deploymentName);
        return new ResultVO(result);
    }

    @ApiOperation("批量删除deployment")
    @PostMapping("/delDeployments")
    @ApiImplicitParams({@ApiImplicitParam(name = "namespace", value = "名称空间", dataType = "String", required = true),
            @ApiImplicitParam(name = "deploymentNames", value = "多个deployment名称", dataType = "String", required =
                    true)})
    public ResultVO delDeployments(@NotBlank @RequestParam String namespace,
                                   @NotBlank @RequestParam String deploymentNames) {
        String[] namesArr = deploymentNames.split(",", -1);
        List<String> namesList = Arrays.asList(namesArr);
        boolean result = k8SService.delDeployments(namespace, namesList);
        return new ResultVO(result);
    }

    @ApiOperation("删除当前命名空间下的所有deployment")
    @PostMapping("/delDeploymentsAll")
    @ApiImplicitParams(@ApiImplicitParam(name = "spacename", value = "名称空间", dataType = "String", required = true))
    public ResultVO delDeploymentsAll(@NotBlank @RequestParam String spacename) {
        boolean result = k8SService.delDeploymentsAll(spacename);
        return new ResultVO(result);
    }


    @ApiOperation("获取deployment Yaml文件")
    @GetMapping("/deploymentYaml")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "namespace", value = "名称空间", dataType = "String", required = true),
                    @ApiImplicitParam(name = "deploymentName", value = "deployment名称", dataType = "String", required =
                            true)}
            )
    public ResultVO deploymentYaml(String namespace, String deploymentName) {
        try {
            ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
            Deployment deployment = k8SService.deploymentYaml(namespace, deploymentName);
            return ResultVO.success(objectMapper.writeValueAsString(deployment));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResultVO.fail(e.getMessage());
        }
    }


    @ApiOperation("根据deployment Yaml反向生成Deployment")
    @PostMapping("/yamlToDeployment")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "namespace", value = "名称空间", dataType = "String", required = true),
                    @ApiImplicitParam(name = "yaml", value = "yaml", dataType = "String", required =
                            true)}
    )
    public ResultVO yamlToDeployment(String namespace, String yaml) {
        try {
            ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
            Deployment deployment = objectMapper.readValue(yaml.getBytes(), Deployment.class);
            return ResultVO.success(k8SService.yamlToDeployment(namespace, deployment));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResultVO.fail(e.getMessage());
        }
    }

}
