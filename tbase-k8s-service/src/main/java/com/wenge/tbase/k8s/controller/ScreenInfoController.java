package com.wenge.tbase.k8s.controller;


import com.wenge.tbase.commons.result.ResultCode;
import com.wenge.tbase.commons.result.ResultVO;
import com.wenge.tbase.k8s.bean.vo.K8SConfigMap;
import com.wenge.tbase.k8s.bean.vo.K8SDeployment;
import com.wenge.tbase.k8s.bean.vo.K8SPersistentVolumeClaim;
import com.wenge.tbase.k8s.bean.vo.K8SSecret;
import com.wenge.tbase.k8s.service.K8SService;
import io.fabric8.kubernetes.api.model.*;
import io.fabric8.kubernetes.api.model.apps.*;
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
    public ResultVO getNameSpaceInfo() {
        List result = k8SService.getAllNameSpace();
        return new ResultVO(result);
    }

    @ApiOperation("获取节点信息")
    @GetMapping("/getNodesInfo")
    public ResultVO getNodesInfo() {
        Map result = k8SService.getNodeInfo();
        return new ResultVO(result);
    }

    @ApiOperation("获取存储信息")
    @GetMapping("/getStorageInfo")
    public ResultVO getStorageInfo() {
        Map result = k8SService.findStorageInfo();
        return new ResultVO(result);
    }

    @ApiOperation("查看存储类")
    @GetMapping("/getStorageDescribeInfo")
    @ApiImplicitParams(@ApiImplicitParam(name = "name", value = "存储类名称", dataType = "String"))
    public ResultVO getStorageDescribeInfo(@RequestParam String name) {
        String result = k8SService.findStorageDescribeInfo(name);
        return new ResultVO(result);
    }

    @ApiOperation("创建deployment")
    @PostMapping("/createDeployment")
    @ApiImplicitParams(@ApiImplicitParam(name = "K8SDeployment", value = "部署对象", dataType = "K8SDeployment"))
    public ResultVO createDeployment(@RequestBody K8SDeployment deploymentInfo) {
        Deployment deployment = k8SService.createDeployment(deploymentInfo);
        return new ResultVO(deployment);
    }

    @ApiOperation("查看Deployment详细信息")
    @GetMapping("/findDeploymentDetail")
    @ApiImplicitParams({@ApiImplicitParam(name = "deploymentName", value = "deployment名称", dataType = "String", required = true),
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
            @ApiImplicitParam(name = "deploymentNames", value = "多个deployment名称", dataType = "String", required = true)})
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

    @ApiOperation("创建statefulSet")
    @PostMapping("/createStatefulSet")
    @ApiImplicitParams(@ApiImplicitParam(name = "StatefulSet", value = "部署对象", dataType = "StatefulSet"))
    public ResultVO createStatefulSet(@RequestBody StatefulSet statefulSetInfo) {
        StatefulSet statefulSet = k8SService.createStatefulSet(statefulSetInfo);
        return new ResultVO(statefulSet);
    }

    @ApiOperation("查看StatefulSet的详细信息")
    @GetMapping("/findStatefulSetDetail")
    @ApiImplicitParams({@ApiImplicitParam(name = "statefulSetName", value = "statefulSet名称", dataType = "String", required = true),
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

    @ApiOperation("创建service")
    @PostMapping("/createService")
    @ApiImplicitParams(@ApiImplicitParam(name = "K8SDeployment", value = "Service对象", dataType = "K8SDeployment"))
    public ResultVO createService(@RequestBody K8SDeployment k8SDeployment) {
        String result = k8SService.createService(k8SDeployment);
        return new ResultVO(result);
    }

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
    @ApiImplicitParams({@ApiImplicitParam(name = "configMapName", value = "configMap名称", dataType = "String", required = true),
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

    @ApiOperation("生成密文")
    @PostMapping("/createSecret")
    @ApiImplicitParams(@ApiImplicitParam(name = "K8SSecret", value = "K8SSecret对象", dataType = "K8SSecret"))
    public ResultVO createSecret(@RequestBody K8SSecret secretInfo) {
        String secretInfoType = secretInfo.getType();
        if (secretInfoType.equals("kubernetes.io/dockerconfigjson")) {
            if (secretInfo.getDockerSecret() == null || secretInfo.getDockerServer() == null || secretInfo.getDockerUsername() == null) {
                return new ResultVO(ResultCode.PARAM_IS_EMPTY);
            }
        }
        Secret secret = k8SService.createSecret(secretInfo);
        return new ResultVO(secret);
    }

    @ApiOperation("查看对应密文的详细信息")
    @GetMapping("/findSecretDetail")
    @ApiImplicitParams({@ApiImplicitParam(name = "secretName", value = "secret名称", dataType = "String", required = true),
            @ApiImplicitParam(name = "namespace", value = "名称空间", dataType = "String", required = true)})
    public ResultVO findSecretDetail(@NotBlank @RequestParam String secretName,
                                        @NotBlank @RequestParam String namespace) {
        Secret secretDetail = k8SService.findSecretDetail(secretName, namespace);
        return new ResultVO(secretDetail);
    }

    @ApiOperation("查询当前名称空间下所有密文")
    @GetMapping("/listSecret")
    @ApiImplicitParams(@ApiImplicitParam(name = "namespace", value = "名称空间", dataType = "String", required = true))
    public ResultVO listSecret(@NotBlank @RequestParam String namespace) {
        SecretList secretList = k8SService.listSecret(namespace);
        return new ResultVO(secretList);
    }

    @ApiOperation("修改密文")
    @PutMapping("/editSecret")
    @ApiImplicitParams(@ApiImplicitParam(name = "K8SSecret", value = "K8SSecret对象", dataType = "K8SSecret"))
    public ResultVO editSecret(@RequestBody K8SSecret secretInfo) {
        Secret secret = k8SService.editSecret(secretInfo);
        return new ResultVO(secret);
    }

    @ApiOperation("删除单个密文")
    @PostMapping("/delSecret")
    @ApiImplicitParams({@ApiImplicitParam(name = "spacename", value = "命名空间名称", dataType = "String", required = true),
            @ApiImplicitParam(name = "secretname", value = "密文名称", dataType = "String", required = true)})
    public ResultVO delSecret(@NotBlank @RequestParam String spacename, @NotBlank @RequestParam String secretname) {
        boolean res = k8SService.delSecret(spacename, secretname);
        return new ResultVO(res);
    }

    @ApiOperation("批量删除密文")
    @PostMapping("/delSecrets")
    @ApiImplicitParams({@ApiImplicitParam(name = "spacename", value = "命名空间名称", dataType = "String", required = true),
            @ApiImplicitParam(name = "secretnames", value = "多个密文名称", dataType = "String", required = true)})
    public ResultVO delSecrets(@NotBlank @RequestParam String spacename, @NotBlank @RequestParam String secretnames) {
        String[] secretnamesArr = secretnames.split(",", -1);
        List<String> secretnamesList = Arrays.asList(secretnamesArr);
        boolean res = k8SService.delSecrets(spacename, secretnamesList);
        return new ResultVO(res);
    }

    @ApiOperation("删除该命名空间下的所有密文")
    @PostMapping("/delSecretsAll")
    @ApiImplicitParams(@ApiImplicitParam(name = "spacename", value = "命名空间名称", dataType = "String", required = true))
    public ResultVO delSecretsAll(@NotBlank @RequestParam String spacename) {
        boolean res = k8SService.delSecretsAll(spacename);
        return new ResultVO(res);
    }

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
