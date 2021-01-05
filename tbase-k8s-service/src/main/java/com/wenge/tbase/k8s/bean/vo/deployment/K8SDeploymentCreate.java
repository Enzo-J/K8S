package com.wenge.tbase.k8s.bean.vo.deployment;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.fabric8.kubernetes.api.model.*;
import io.fabric8.kubernetes.api.model.apps.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

@Data
@ApiModel(value = "Deployment部署参数")
public class K8SDeploymentCreate {
    @ApiModelProperty(value = "deployment名称")
    private String name;
    @ApiModelProperty(value = "所属空间")
    private String namespace;
    @ApiModelProperty(value = "期望副本数")
    private int replicas = 1;
    @ApiModelProperty(value = "最短就绪时间")
    private Integer minReadySeconds;
    @ApiModelProperty(value = "最大不可用副本数")
    private String maxUnavailable;
    @ApiModelProperty(value = "最大超预期副本数")
    private String maxSurge;
    @ApiModelProperty(value = "容器配置")
    private List<K8SDeploymentContainer> containers;
    @ApiModelProperty(value = "仓库密钥")
    private String repoKey;
    @ApiModelProperty(value = "标签，作用于deployment、pod，以及deployment Selector")
    private List<K8SKV> labels;
    @ApiModelProperty(value = "pod 注释")
    private List<K8SKV> annotations;
    @ApiModelProperty(value = "HostNetwork")
    private Boolean hostNetwork;
    @ApiModelProperty(value = "节点亲和性")
    private List<K8SDeploymentNodeAffinityRule> nodeAffinitys;
    @ApiModelProperty(value = "副本亲和性")
    private List<K8SDeploymentPodAffinityRule> podAffinitys;
    @ApiModelProperty(value = "副本反亲和性")
    private List<K8SDeploymentPodAffinityRule> podAntiAffinitys;
    @ApiModelProperty(value = "容忍")
    private List<K8SDeploymentToleration> tolerations;

    public ObjectMeta metadata() {
        Map<String, String> labelMap = Maps.newHashMap();
        if (labels != null) {
            for (K8SKV label : labels) {
                labelMap.put(label.getKey(), label.getValue());
            }
        }
        Map<String, String> annotationMap = Maps.newHashMap();
        if (annotations != null) {
            for (K8SKV annotation : annotations) {
                annotationMap.put(annotation.getKey(), annotation.getValue());
            }
        }
        return new ObjectMetaBuilder().withNamespace(namespace)
                .withName(name)
                .withLabels(labelMap)
                .withAnnotations(annotationMap).build();
    }

    public DeploymentSpec spec() {
        Map<String, String> labelMap = Maps.newHashMap();
        if (labels != null) {
            for (K8SKV label : labels) {
                labelMap.put(label.getKey(), label.getValue());
            }
        }
        LabelSelector labelSelector = new LabelSelectorBuilder()
                .withMatchLabels(labelMap)
                .build();
        ObjectMeta podObjectMeta = new ObjectMetaBuilder()
                .withLabels(labelMap)
                .build();
        PodTemplateSpecBuilder podTemplateSpecBuilder = new PodTemplateSpecBuilder()
                .withMetadata(podObjectMeta);
        podTemplateSpecBuilder.withSpec(podSpec());

        DeploymentSpecBuilder deploymentSpecBuilder = new DeploymentSpecBuilder();

        deploymentSpecBuilder.withReplicas(replicas)
                .withSelector(labelSelector)
                .withMinReadySeconds(minReadySeconds)
                .withTemplate(podTemplateSpecBuilder.build());
        if (StringUtils.isNotBlank(maxSurge) || StringUtils.isNotBlank(maxUnavailable)) {
            deploymentSpecBuilder.withStrategy(deploymentStrategy());
        }
        return deploymentSpecBuilder.build();
    }

    private DeploymentStrategy deploymentStrategy() {
        DeploymentStrategyBuilder deploymentStrategyBuilder = new DeploymentStrategyBuilder();
        RollingUpdateDeployment rollingUpdateDeployment = deploymentStrategyBuilder.buildRollingUpdate();
        if (StringUtils.isNotBlank(maxUnavailable)) {
            rollingUpdateDeployment.setMaxUnavailable(new IntOrStringBuilder().withStrVal(maxUnavailable).build());
        }
        if (StringUtils.isNotBlank(maxSurge)) {
            rollingUpdateDeployment.setMaxSurge(new IntOrStringBuilder().withStrVal(maxSurge).build());
        }
        return deploymentStrategyBuilder.build();
    }

    public PodSpec podSpec() {
        List<Container> regularContainers = Lists.newArrayList();
        List<Container> initContainers = Lists.newArrayList();
        for (K8SDeploymentContainer container : containers) {
            if (container.isInit()) {
                initContainers.add(container.container());
            } else {
                regularContainers.add(container.container());
            }
        }
        PodSpecBuilder podSpecBuilder = new PodSpecBuilder();
        podSpecBuilder.addToInitContainers(initContainers.toArray(new Container[0]));
        podSpecBuilder.addToContainers(regularContainers.toArray(new Container[0]));
        podSpecBuilder.withHostNetwork(hostNetwork);
        if ((nodeAffinitys != null && !nodeAffinitys.isEmpty()) || (podAffinitys != null && !podAffinitys.isEmpty()) || (podAntiAffinitys != null && !podAntiAffinitys.isEmpty())) {
            podSpecBuilder.withAffinity(affinity());
        }
        if (tolerations != null && !tolerations.isEmpty()) {
            List<Toleration> tolerationList = Lists.newArrayList();
            for (K8SDeploymentToleration toleration : tolerations) {
                tolerationList.add(toleration.toleration());
            }
            podSpecBuilder.withTolerations(tolerationList);
        }
        return new PodSpecBuilder().build();
    }


    public Affinity affinity() {
        AffinityBuilder affinityBuilder = new AffinityBuilder();
        if (nodeAffinitys != null && !nodeAffinitys.isEmpty()) {
            affinityBuilder.withNodeAffinity(K8SDeploymentNodeAffinityRule.nodeAffinity(nodeAffinitys));
        }
        if (podAffinitys != null && !podAffinitys.isEmpty()) {
            affinityBuilder.withPodAffinity(K8SDeploymentPodAffinityRule.podAffinity(podAffinitys));
        }
        if (podAntiAffinitys != null && podAntiAffinitys.isEmpty()) {
            affinityBuilder.withPodAntiAffinity(K8SDeploymentPodAffinityRule.podAntiAffinity(podAntiAffinitys));
        }
        return affinityBuilder.build();
    }


}
