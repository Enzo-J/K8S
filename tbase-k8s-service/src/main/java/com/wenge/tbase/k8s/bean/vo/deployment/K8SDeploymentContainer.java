package com.wenge.tbase.k8s.bean.vo.deployment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "Deployment容器配置")
public class K8SDeploymentContainer {
    @ApiModelProperty(value = "是否是初始化容器，false，非初始化容器，true,初始化容器")
    private boolean init;
    @ApiModelProperty(value = "容器名称")
    private String name;
    @ApiModelProperty(value = "镜像名称")
    private String imageName;
    @ApiModelProperty(value = "cpu配额请求")
    private String cpuRequest;
    @ApiModelProperty(value = "cpu配额限制")
    private String cpuLimit;
    @ApiModelProperty(value = "内存配额请求")
    private String memoryRequest;
    @ApiModelProperty(value = "内存配额限制")
    private String memoryLimit;
    @ApiModelProperty(value = "数据卷")
    private List<K8SDeploymentVolume> volumes;
    @ApiModelProperty(value = "日志目录")
    private List<String> logPaths;
    @ApiModelProperty(value = "容器环境变量")
    private List<K8SKV> environmentVars;
    @ApiModelProperty(value = "启动容器命令")
    private List<String> command;
    @ApiModelProperty(value = "启动容器命令参数")
    private List<String> commandArgs;
    @ApiModelProperty(value = "存活探测")
    private K8sDeploymentProbe livenessProbe;
    @ApiModelProperty(value = "就绪探测")
    private K8sDeploymentProbe startupProbe;
    @ApiModelProperty(value = "生命周期-启动前")
    private K8sDeploymentProbe lifecyclePreStop;
    @ApiModelProperty(value = "生命周期-启动后")
    private K8sDeploymentProbe lifecyclePostStop;
    @ApiModelProperty(value = "容器暴露端口")
    private List<K8SDeploymentContainerPort> ports;
    @ApiModelProperty(value = "镜像拉取策略,IfNotPresent,Always,Never,默认不用传")
    private String imagePullPolicy;
}
