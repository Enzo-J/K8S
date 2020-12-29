package com.wenge.tbase.k8s.controller;

import com.wenge.tbase.commons.result.ResultVO;
import com.wenge.tbase.k8s.bean.vo.K8SPersistentVolumeClaim;
import com.wenge.tbase.k8s.service.K8SService;
import io.fabric8.kubernetes.api.model.PersistentVolumeClaim;
import io.fabric8.kubernetes.api.model.PersistentVolumeClaimList;
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

@Api(tags = "大屏展示K8S集群PVC模块")
@RestController
@RequestMapping("/Screen/PVC")
@Validated
public class K8SPVCController {
    @Resource
    private K8SService k8SService;

    @ApiOperation("创建存储卷声明")
    @PostMapping("/createPVC")
    @ApiImplicitParams(@ApiImplicitParam(name = "K8SPersistentVolumeClaim", value = "K8SPersistentVolumeClaim对象",
            dataType = "K8SPersistentVolumeClaim"))
    public ResultVO createPVC(@RequestBody K8SPersistentVolumeClaim pvcInfo) {
        PersistentVolumeClaim pvc = k8SService.createPVC(pvcInfo);
        return new ResultVO(pvc);
    }

    @ApiOperation("查看存储卷声明详细信息")
    @GetMapping("/findPVCDetail")
    @ApiImplicitParams({@ApiImplicitParam(name = "PVCname", value = "PNC名称", dataType = "String", required = true),
            @ApiImplicitParam(name = "namespace", value = "名称空间", dataType = "String", required = true)})
    public ResultVO findPVCDetail(@NotBlank @RequestParam String PVCname,
                                  @NotBlank @RequestParam String namespace) {
        PersistentVolumeClaim pvcDetail = k8SService.findPVCDetail(PVCname, namespace);
        return new ResultVO(pvcDetail);
    }

    @ApiOperation("查询当前名称空间下所有存储卷")
    @GetMapping("/listPVC")
    @ApiImplicitParams(@ApiImplicitParam(name = "namespace", value = "名称空间", dataType = "String", required = true))
    public ResultVO listPVC(@NotBlank @RequestParam String namespace) {
        PersistentVolumeClaimList persistentVolumeClaimList = k8SService.listPVC(namespace);
        return new ResultVO(persistentVolumeClaimList);
    }

    @ApiOperation("删除单个PVC")
    @PostMapping("/delPVC")
    @ApiImplicitParams({@ApiImplicitParam(name = "spacename", value = "命名空间名称", dataType = "String", required = true),
            @ApiImplicitParam(name = "PVCname", value = "PVC名称", dataType = "String", required = true)})
    public ResultVO delPVC(@NotBlank @RequestParam String spacename, @NotBlank @RequestParam String PVCname) {
        boolean res = k8SService.delPVC(spacename, PVCname);
        return new ResultVO(res);
    }

    @ApiOperation("批量删除PVC")
    @PostMapping("/delPVCs")
    @ApiImplicitParams({@ApiImplicitParam(name = "spacename", value = "命名空间名称", dataType = "String", required = true),
            @ApiImplicitParam(name = "PVCnames", value = "多个PVC名称", dataType = "String", required = true)})
    public ResultVO delPVCs(@NotBlank @RequestParam String spacename, @NotBlank @RequestParam String PVCnames) {
        String[] PVCnamesArr = PVCnames.split(",", -1);
        List<String> PVCnamesList = Arrays.asList(PVCnamesArr);
        boolean res = k8SService.delPVCs(spacename, PVCnamesList);
        return new ResultVO(res);
    }

    @ApiOperation("删除该命名空间下的所有PVC")
    @PostMapping("/delPVCsAll")
    @ApiImplicitParams(@ApiImplicitParam(name = "spacename", value = "命名空间名称", dataType = "String", required = true))
    public ResultVO delPVCsAll(@NotBlank @RequestParam String spacename) {
        boolean res = k8SService.delPVCsAll(spacename);
        return new ResultVO(res);
    }
}
