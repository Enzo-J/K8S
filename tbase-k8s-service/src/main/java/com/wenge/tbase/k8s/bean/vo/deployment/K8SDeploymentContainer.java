package com.wenge.tbase.k8s.bean.vo.deployment;

import com.google.common.collect.Lists;
import io.fabric8.kubernetes.api.model.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

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
    private List<K8SKV> envs;
    @ApiModelProperty(value = "启动容器命令")
    private List<String> commands;
    @ApiModelProperty(value = "启动容器命令参数")
    private List<String> commandArgs;
    @ApiModelProperty(value = "存活探测")
    private K8sDeploymentProbe livenessProbe;
    @ApiModelProperty(value = "就绪探测")
    private K8sDeploymentProbe startupProbe;
    @ApiModelProperty(value = "生命周期-启动前")
    private K8sDeploymentProbe lifecyclePreStop;
    @ApiModelProperty(value = "生命周期-启动后")
    private K8sDeploymentProbe lifecyclePostStart;
    @ApiModelProperty(value = "容器暴露端口")
    private List<K8SDeploymentContainerPort> ports;
    @ApiModelProperty(value = "镜像拉取策略,IfNotPresent,Always,Never,默认不用传")
    private String imagePullPolicy;

    public Container container() {
        ContainerBuilder containerBuilder = new ContainerBuilder().withName(name).withImage(imageName);

        ResourceRequirementsBuilder resourceRequirementsBuilder = new ResourceRequirementsBuilder();
        if (StringUtils.isNotBlank(cpuRequest)) {
            resourceRequirementsBuilder.addToRequests("cpu", Quantity.parse(cpuRequest));
        }
        if (StringUtils.isNotBlank(memoryRequest)) {
            resourceRequirementsBuilder.addToRequests("memory", Quantity.parse(memoryRequest));
        }
        if (StringUtils.isNotBlank(cpuLimit)) {
            resourceRequirementsBuilder.addToLimits("cpu", Quantity.parse(cpuLimit));
        }
        if (StringUtils.isNotBlank(memoryLimit)) {
            resourceRequirementsBuilder.addToLimits("memory", Quantity.parse(memoryLimit));
        }
        if ((resourceRequirementsBuilder.getLimits() != null && !resourceRequirementsBuilder.getLimits().isEmpty()) || (resourceRequirementsBuilder.getRequests() != null && !resourceRequirementsBuilder.getRequests().isEmpty())) {
            containerBuilder.withResources(resourceRequirementsBuilder.build());
        }

        if (volumes != null) {
            List<VolumeMount> volumeMounts = Lists.newArrayList();
            for (K8SDeploymentVolume volume : volumes) {
                volumeMounts.add(volume.volumeMount());
            }
            containerBuilder.withVolumeMounts(volumeMounts);
        }
        if (envs != null) {
            List<EnvVar> envVars = Lists.newArrayList();
            List<EnvFromSource> envFromSources = Lists.newArrayList();
            for (K8SKV env : envs) {
                if (env.getType() == 1 || env.getType() == 2) {
                    EnvFromSource envFromSource = env.envFromSource();
                    if (envFromSource != null) {
                        envFromSources.add(envFromSource);
                    }
                } else if (env.getType() == 0 || env.getType() == 3 || env.getType() == 4) {
                    envVars.add(env.envVar());
                }
            }
            containerBuilder.withEnv(envVars);
            containerBuilder.withEnvFrom(envFromSources);
        }
        if (commands != null) {
            containerBuilder.addAllToCommand(commands);
        }
        if (commandArgs != null) {
            containerBuilder.addAllToArgs(commandArgs);
        }
        if (livenessProbe != null) {
            containerBuilder.withLivenessProbe(livenessProbe.probe());
        }
        if (startupProbe != null) {
            containerBuilder.withStartupProbe(startupProbe.probe());
        }
        if (lifecyclePreStop != null) {
            containerBuilder.withLifecycle(lifecyclePreStop.lifecycle(true));
        }
        if (lifecyclePostStart != null) {
            containerBuilder.withLifecycle(lifecyclePostStart.lifecycle(false));
        }
        if (ports != null) {
            List<ContainerPort> containerPorts = Lists.newArrayList();
            for (K8SDeploymentContainerPort port : ports) {
                containerPorts.add(port.containerPort());
            }
            containerBuilder.withPorts(containerPorts);
        }

        if (StringUtils.isNotBlank(imagePullPolicy)) {
            containerBuilder.withImagePullPolicy(imagePullPolicy);
        }
        return containerBuilder.build();
    }
}
