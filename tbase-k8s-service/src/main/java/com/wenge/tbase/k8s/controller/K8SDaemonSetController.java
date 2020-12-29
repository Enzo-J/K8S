package com.wenge.tbase.k8s.controller;

import com.wenge.tbase.commons.result.ResultVO;
import com.wenge.tbase.k8s.service.K8SService;
import io.fabric8.kubernetes.api.model.apps.DaemonSet;
import io.fabric8.kubernetes.api.model.apps.DaemonSetList;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.Arrays;
import java.util.List;

@Api(tags = "应用管理平台DaemonSet模块")
@RestController
@RequestMapping("/Screen/DaemonSet")
@Validated
public class K8SDaemonSetController {
    @Resource
    private K8SService k8SService;

    @ApiOperation("创建daemonSet")
    @PostMapping("/createDaemonSet")
    @ApiImplicitParams(@ApiImplicitParam(name = "DaemonSet", value = "部署对象", dataType = "DaemonSet"))
    public ResultVO createDaemonSet(@RequestBody DaemonSet daemonSetInfo) {
        DaemonSet daemonSet = k8SService.createDaemonSet(daemonSetInfo);
        return new ResultVO(daemonSet);
    }

    @ApiOperation("查看DaemonSet详细信息")
    @GetMapping("/findDaemonSetDetail")
    @ApiImplicitParams({@ApiImplicitParam(name = "daemonSetName", value = "daemonSet名称", dataType = "String", required = true),
            @ApiImplicitParam(name = "namespace", value = "名称空间", dataType = "String", required = true)})
    public ResultVO findDaemonSetDetail(@NotBlank @RequestParam String daemonSetName,
                                        @NotBlank @RequestParam String namespace) {
        DaemonSet daemonSetDetail = k8SService.findDaemonSetDetail(daemonSetName, namespace);
        return new ResultVO(daemonSetDetail);
    }

    @ApiOperation("查询名称空间下的所有DaemonSet")
    @GetMapping("/listDaemonSet")
    @ApiImplicitParams(@ApiImplicitParam(name = "namespace", value = "名称空间", dataType = "String", required = true))
    public ResultVO listDaemonSet(@NotBlank @RequestParam String namespace) {
        DaemonSetList daemonSetList = k8SService.listDaemonSet(namespace);
        return new ResultVO(daemonSetList);
    }

    @ApiOperation("删除单个daemonSet")
    @PostMapping("/delDaemonSet")
    @ApiImplicitParams({@ApiImplicitParam(name = "spacename", value = "名称空间", dataType = "String", required = true),
            @ApiImplicitParam(name = "daemonSetName", value = "daemonSet名称", dataType = "String", required = true)})
    public ResultVO delDaemonSet(@NotBlank @RequestParam String spacename,
                                 @NotBlank @RequestParam String daemonSetName) {
        boolean result = k8SService.delDaemonSet(spacename, daemonSetName);
        return new ResultVO(result);
    }

    @ApiOperation("批量删除daemonSet")
    @PostMapping("/delDaemonSetNames")
    @ApiImplicitParams({@ApiImplicitParam(name = "namespace", value = "名称空间", dataType = "String", required = true),
            @ApiImplicitParam(name = "daemonSetNames", value = "多个daemonSet名称", dataType = "String", required = true)})
    public ResultVO delDaemonSetNames(@NotBlank @RequestParam String namespace,
                                      @NotBlank @RequestParam String daemonSetNames) {
        String[] namesArr = daemonSetNames.split(",", -1);
        List<String> namesList = Arrays.asList(namesArr);
        boolean result = k8SService.delDaemonSetNames(namespace, namesList);
        return new ResultVO(result);
    }

    @ApiOperation("删除当前命名空间下的所有daemonSet")
    @PostMapping("/delDaemonSetsAll")
    @ApiImplicitParams(@ApiImplicitParam(name = "spacename", value = "名称空间", dataType = "String", required = true))
    public ResultVO delDaemonSetsAll(@NotBlank @RequestParam String spacename) {
        boolean result = k8SService.delDaemonSetsAll(spacename);
        return new ResultVO(result);
    }
}
