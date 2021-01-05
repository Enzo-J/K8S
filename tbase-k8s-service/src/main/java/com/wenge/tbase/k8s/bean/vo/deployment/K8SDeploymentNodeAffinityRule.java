package com.wenge.tbase.k8s.bean.vo.deployment;

import com.google.common.collect.Lists;
import io.fabric8.kubernetes.api.model.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "Deployment亲和性、反亲和性")
public class K8SDeploymentNodeAffinityRule {
//    @ApiModelProperty(value = "1 Required, 0 Preferred")
//    protected int type;
    @ApiModelProperty(value = "亲和性key")
    protected String key;
    @ApiModelProperty(value = "亲和性 In,DoesNotExist,NotIn,Exists,Gt,Lt")
    protected String operator;
    @ApiModelProperty(value = "values")
    private List<String> values;


    private PreferredSchedulingTerm preferredSchedulingTerm() {
        NodeSelectorRequirementBuilder nodeSelectorRequirementBuilder = new NodeSelectorRequirementBuilder().withKey(key).withOperator(operator);
        if (!("Exists".equals(operator) || "DoesNotExist".equals(operator))) {
            nodeSelectorRequirementBuilder.withValues(values);
        }
        NodeSelectorTerm nodeSelectorTerm = new NodeSelectorTermBuilder().withMatchExpressions(nodeSelectorRequirementBuilder.build()).build();
        PreferredSchedulingTerm preferredSchedulingTerm = new PreferredSchedulingTermBuilder().withPreference(nodeSelectorTerm).build();
        return preferredSchedulingTerm;
    }

    public static NodeAffinity nodeAffinity(List<K8SDeploymentNodeAffinityRule> affinityRules) {
        NodeAffinityBuilder nodeAffinityBuilder = new NodeAffinityBuilder();
        List<PreferredSchedulingTerm> preferredSchedulingTerms = Lists.newArrayList();
        for (K8SDeploymentNodeAffinityRule affinityRule : affinityRules) {
            preferredSchedulingTerms.add(affinityRule.preferredSchedulingTerm());
        }
        return nodeAffinityBuilder.withPreferredDuringSchedulingIgnoredDuringExecution(preferredSchedulingTerms).build();
    }


}
