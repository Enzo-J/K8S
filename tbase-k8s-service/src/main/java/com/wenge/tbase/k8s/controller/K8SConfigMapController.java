package com.wenge.tbase.k8s.controller;

import com.wenge.tbase.commons.result.ResultCode;
import com.wenge.tbase.commons.result.ResultVO;
import com.wenge.tbase.k8s.bean.vo.K8SConfigMap;
import com.wenge.tbase.k8s.service.K8SService;
import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.api.model.ConfigMapList;
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

@Api(tags = "大屏展示K8S集群ConfigMap模块")
@RestController
@RequestMapping("/Screen/ConfigMap")
@Validated
public class K8SConfigMapController {
    @Resource
    private K8SService k8SService;

    @ApiOperation("创建字典")
    @PostMapping("/createConfigMap")
    @ApiImplicitParams(@ApiImplicitParam(name = "K8SConfigMap", value = "K8SConfigMap对象", dataType = "K8SConfigMap"))
    public ResultVO createConfigMap(@RequestBody K8SConfigMap configMapInfo) {
        if (configMapInfo.getData() == null || configMapInfo.getData().size() == 0) {
            return new ResultVO(ResultCode.PARAM_IS_EMPTY);
        }
        ConfigMap configMap = k8SService.createConfigMap(configMapInfo);
        return new ResultVO(configMap);
    }

    @ApiOperation("查看对应configMap下的详细信息")
    @GetMapping("/findConfigMapDetail")
    @ApiImplicitParams({@ApiImplicitParam(name = "configMapName", value = "configMap名称", dataType = "String",
            required = true),
            @ApiImplicitParam(name = "namespace", value = "名称空间", dataType = "String", required = true)})
    public ResultVO findConfigMapDetail(@NotBlank @RequestParam String configMapName,
                                        @NotBlank @RequestParam String namespace) {
        ConfigMap configMapDetail = k8SService.findConfigMapDetail(configMapName, namespace);
        return new ResultVO(configMapDetail);
    }

    @ApiOperation("查询当前名称空间下的所有字典")
    @GetMapping("/listConfigMap")
    @ApiImplicitParams(@ApiImplicitParam(name = "namespace", value = "名称空间", dataType = "String", required = true))
    public ResultVO listConfigMap(@NotBlank @RequestParam String namespace) {
        ConfigMapList configMapList = k8SService.listConfigMap(namespace);
        return new ResultVO(configMapList);
    }

    @ApiOperation("修改对应字典下的详细信息")
    @PutMapping("/editConfigMapDetail")
    @ApiImplicitParams(@ApiImplicitParam(name = "K8SConfigMap", value = "K8SConfigMap对象", dataType = "K8SConfigMap"))
    public ResultVO editConfigMapDetail(@RequestBody K8SConfigMap configMapInfo) {
        ConfigMap configMap = k8SService.editConfigMapDetail(configMapInfo);
        return new ResultVO(configMap);
    }

    @ApiOperation("删除单个字典")
    @PostMapping("/delConfigMap")
    @ApiImplicitParams({@ApiImplicitParam(name = "name", value = "字典名称", dataType = "String", required = true),
            @ApiImplicitParam(name = "spacename", value = "名称空间", dataType = "String", required = true)})
    public ResultVO delConfigMap(@NotBlank @RequestParam String name, @NotBlank @RequestParam String spacename) {
        boolean result = k8SService.delConfigMap(name, spacename);
        return new ResultVO(result);
    }

    @ApiOperation("批量删除字典")
    @PostMapping("/delConfigMaps")
    @ApiImplicitParams({@ApiImplicitParam(name = "names", value = "多个字典名称", dataType = "String", required = true),
            @ApiImplicitParam(name = "spacename", value = "名称空间", dataType = "String", required = true)})
    public ResultVO delConfigMaps(@NotBlank @RequestParam String names, @NotBlank @RequestParam String spacename) {
        String[] namesArr = names.split(",", -1);
        List<String> namesList = Arrays.asList(namesArr);
        boolean result = k8SService.delConfigMaps(namesList, spacename);
        return new ResultVO(result);
    }

    @ApiOperation("删除当前命名空间下的所有字典")
    @PostMapping("/delConfigMapsAll")
    @ApiImplicitParams(@ApiImplicitParam(name = "spacename", value = "名称空间", dataType = "String", required = true))
    public ResultVO delConfigMapsAll(@NotBlank @RequestParam String spacename) {
        boolean result = k8SService.delConfigMapsAll(spacename);
        return new ResultVO(result);
    }
}
