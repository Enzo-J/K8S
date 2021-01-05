package com.wenge.tbase.k8s.bean.vo.deployment;

import com.google.common.collect.Lists;
import io.fabric8.kubernetes.api.model.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "Deployment Pod亲和性、反亲和性")
public class K8SDeploymentPodAffinityRule {
    @ApiModelProperty(value = "weight, 1-100")
    protected Integer weight;
//    @ApiModelProperty(value = "1 Required, 0 Preferred")
//    protected int type;
    @ApiModelProperty(value = "亲和性、反亲和性 key")
    protected String key;
    @ApiModelProperty(value = "亲和性、反亲和性操作符,In,DoesNotExist,NotIn,Exists")
    protected String operator;
    @ApiModelProperty(value = "拓扑域,kubernetes.io/hostname,kubernetes.io/zone,kubernetes.io/region")
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

    public static PodAffinity podAffinity(List<K8SDeploymentPodAffinityRule> podAffinityRules) {
        PodAffinityBuilder podAffinityBuilder = new PodAffinityBuilder();
        List<WeightedPodAffinityTerm> weightedPodAffinityTerms = Lists.newArrayList();
        for (K8SDeploymentPodAffinityRule podAffinityRule : podAffinityRules) {
            weightedPodAffinityTerms.add(podAffinityRule.weightedPodAffinityTerm());
        }

        podAffinityBuilder.withPreferredDuringSchedulingIgnoredDuringExecution(weightedPodAffinityTerms);
        return podAffinityBuilder.build();
    }

    public static PodAntiAffinity podAntiAffinity(List<K8SDeploymentPodAffinityRule> podAntiAffinityRules) {
        PodAntiAffinityBuilder podAntiAffinityBuilder = new PodAntiAffinityBuilder();
        List<WeightedPodAffinityTerm> weightedPodAffinityTerms = Lists.newArrayList();
        for (K8SDeploymentPodAffinityRule podAntiAffinityRule : podAntiAffinityRules) {
            weightedPodAffinityTerms.add(podAntiAffinityRule.weightedPodAffinityTerm());
        }
        podAntiAffinityBuilder.withPreferredDuringSchedulingIgnoredDuringExecution(weightedPodAffinityTerms);
        return podAntiAffinityBuilder.build();
    }
}
