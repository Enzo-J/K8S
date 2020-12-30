package com.wenge.tbase.k8s.controller;


import com.wenge.tbase.commons.result.ResultVO;
import com.wenge.tbase.k8s.service.K8SService;
import io.fabric8.kubernetes.api.model.apps.StatefulSet;
import io.fabric8.kubernetes.api.model.apps.StatefulSetList;
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

@Api(tags = "应用管理平台StatefulSet模块")
@RestController
@RequestMapping("/Screen/StatefulSet")
@Validated
public class K8SStatefulSetController {
    @Resource
    private K8SService k8SService;

    @ApiOperation("创建statefulSet")
    @PostMapping("/createStatefulSet")
    @ApiImplicitParams(@ApiImplicitParam(name = "StatefulSet", value = "部署对象", dataType = "StatefulSet"))
    public ResultVO createStatefulSet(@RequestBody StatefulSet statefulSetInfo) {
        StatefulSet statefulSet = k8SService.createStatefulSet(statefulSetInfo);
        return new ResultVO(statefulSet);
    }

    @ApiOperation("查看StatefulSet的详细信息")
    @GetMapping("/findStatefulSetDetail")
    @ApiImplicitParams({@ApiImplicitParam(name = "statefulSetName", value = "statefulSet名称", dataType = "String",
            required = true),
            @ApiImplicitParam(name = "namespace", value = "名称空间", dataType = "String", required = true)})
    public ResultVO findStatefulSetDetail(@NotBlank @RequestParam String statefulSetName,
                                          @NotBlank @RequestParam String namespace) {
        StatefulSet statefulSetDetail = k8SService.findStatefulSetDetail(statefulSetName, namespace);
        return new ResultVO(statefulSetDetail);
    }

    @ApiOperation("查询名称空间下的所有statefulSet")
    @GetMapping("/listStatefulSet")
    @ApiImplicitParams(@ApiImplicitParam(name = "namespace", value = "名称空间", dataType = "String", required = true))
    public ResultVO listStatefulSet(@NotBlank @RequestParam String namespace) {
        StatefulSetList statefulSetList = k8SService.listStatefulSet(namespace);
        return new ResultVO(statefulSetList);
    }

    @ApiOperation("删除单个statefulSet")
    @PostMapping("/delStatefulSet")
    @ApiImplicitParams({@ApiImplicitParam(name = "spacename", value = "名称空间", dataType = "String", required = true),
            @ApiImplicitParam(name = "statefulSetName", value = "statefulSet名称", dataType = "String", required = true)})
    public ResultVO delStatefulSet(@NotBlank @RequestParam String spacename,
                                   @NotBlank @RequestParam String statefulSetName) {
        boolean result = k8SService.delStatefulSet(spacename, statefulSetName);
        return new ResultVO(result);
    }

    @ApiOperation("批量删除statefulSet")
    @PostMapping("/delStatefulSets")
    @ApiImplicitParams({@ApiImplicitParam(name = "namespace", value = "名称空间", dataType = "String", required = true),
            @ApiImplicitParam(name = "statefulSetNames", value = "多个statefulSet名称", dataType = "String", required =
                    true)})
    public ResultVO delStatefulSets(@NotBlank @RequestParam String namespace,
                                    @NotBlank @RequestParam String statefulSetNames) {
        String[] namesArr = statefulSetNames.split(",", -1);
        List<String> namesList = Arrays.asList(namesArr);
        boolean result = k8SService.delStatefulSets(namespace, namesList);
        return new ResultVO(result);
    }

    @ApiOperation("删除当前命名空间下的所有statefulSet")
    @PostMapping("/delStatefulSetsAll")
    @ApiImplicitParams(@ApiImplicitParam(name = "spacename", value = "名称空间", dataType = "String", required = true))
    public ResultVO delStatefulSetsAll(@NotBlank @RequestParam String spacename) {
        boolean result = k8SService.delStatefulSetsAll(spacename);
        return new ResultVO(result);
    }
}
