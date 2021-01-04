package com.wenge.tbase.k8s.bean.vo.deployment;

import io.fabric8.kubernetes.api.model.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "Deployment Pod亲和性、反亲和性")
public class K8SDeploymentPodAffinityRule {
    @ApiModelProperty(value = "weight, 1-100")
    protected Integer weight = 100;
    @ApiModelProperty(value = "1 Required, 0 Preferred")
    protected int type;
    @ApiModelProperty(value = "亲和性、反亲和性 key")
    protected String key;
    @ApiModelProperty(value = "亲和性、反亲和性操作符")
    protected String operator;
    @ApiModelProperty(value = "拓扑域")
    protected String topologyKey;
    @ApiModelProperty(value = "作用空间")
    protected String namespace;
    @ApiModelProperty(value = "values")
    private List<String> values;

    public WeightedPodAffinityTerm weightedPodAffinityTerm() {
        LabelSelectorRequirementBuilder labelSelectorRequirementBuilder = new LabelSelectorRequirementBuilder();
        labelSelectorRequirementBuilder.withKey(key).withOperator(operator);
        if (!("Exists".equals(operator) || "DoesNotExist".equals(operator))) {
            labelSelectorRequirementBuilder.withValues(values);
        }
        LabelSelectorBuilder labelSelectorBuilder = new LabelSelectorBuilder();
        labelSelectorBuilder.withMatchExpressions(labelSelectorRequirementBuilder.build());

        WeightedPodAffinityTermBuilder weightedPodAffinityTermBuilder = new WeightedPodAffinityTermBuilder();
        PodAffinityTermBuilder podAffinityTermBuilder = new PodAffinityTermBuilder();
        podAffinityTermBuilder.withLabelSelector(labelSelectorBuilder.build());
        podAffinityTermBuilder.withTopologyKey(topologyKey);
        podAffinityTermBuilder.withNamespaces(namespace);

        if (weight != null) {
            weightedPodAffinityTermBuilder.withWeight(weight);
        }
        weightedPodAffinityTermBuilder.withPodAffinityTerm(podAffinityTermBuilder.build());
        return weightedPodAffinityTermBuilder.build();
    }

    public PodAffinity podAffinity(List<K8SDeploymentPodAffinityRule> podAffinityRules) {
        PodAffinityBuilder podAffinityBuilder = new PodAffinityBuilder();
        for (K8SDeploymentPodAffinityRule podAffinityRule : podAffinityRules) {

        }
        podAffinityBuilder.withPreferredDuringSchedulingIgnoredDuringExecution();
        return podAffinityBuilder.build();
    }

    public PodAntiAffinity podAntiAffinity(List<K8SDeploymentPodAffinityRule> podAffinityRules) {

        return null;
    }
}
