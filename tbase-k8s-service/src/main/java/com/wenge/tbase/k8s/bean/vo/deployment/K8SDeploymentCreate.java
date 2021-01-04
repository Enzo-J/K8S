package com.wenge.tbase.k8s.bean.vo.deployment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "Deployment部署参数")
public class K8SDeploymentCreate {
    @ApiModelProperty(value = "deployment名称")
    private String name;
    @ApiModelProperty(value = "所属空间")
    private String namespace;
    @ApiModelProperty(value = "期望副本数")
    private int replicas = 1;
    @ApiModelProperty(value = "发布策略,0滚动更新，1重新发布")
    private int deployStrategy = 0;
    @ApiModelProperty(value = "Deployment更新策略,deployStrategy为0，可以指定这个")
    private K8SDeploymentRollingUpdateStrategy rollingUpdateStrategy;
    @ApiModelProperty(value = "容器配置")
    private List<K8SDeploymentContainer> containers;
    @ApiModelProperty(value = "仓库密钥")
    private String repoKey;
    @ApiModelProperty(value = "标签，作用于deployment、pod，以及deployment Selector")
    private List<K8SKV> labels;
    @ApiModelProperty(value = "pod 注释")
    private List<K8SKV> annotations;
    @ApiModelProperty(value = "HostNetwork")
    private boolean hostNetwork;
    @ApiModelProperty(value = "节点亲和性")
    private List<K8SDeploymentAffinityRule> nodeAffinity;
    @ApiModelProperty(value = "副本亲和性")
    private List<K8SDeploymentPodAffinityRule> podAffinity;
    @ApiModelProperty(value = "容忍")
    private List<K8SDeploymentToleration> tolerations;
    public String valid() {

        return null;
    }
}
