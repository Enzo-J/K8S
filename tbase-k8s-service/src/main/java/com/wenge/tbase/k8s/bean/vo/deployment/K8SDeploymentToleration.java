package com.wenge.tbase.k8s.bean.vo.deployment;

import io.fabric8.kubernetes.api.model.Toleration;
import io.fabric8.kubernetes.api.model.TolerationBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "Deployment 容忍性")
public class K8SDeploymentToleration {
    @ApiModelProperty(value = "key")
    private String key;
    @ApiModelProperty(value = "Operator 为 Exists、Equal")
    private String operator;
    @ApiModelProperty(value = "Operator为Equal才有值")
    private String value;
    @ApiModelProperty(value = "effect为NoSchedule、PreferNoSchedule、NoExecute")
    private String effect;



    public Toleration toleration() {
        TolerationBuilder tolerationBuilder = new TolerationBuilder();
        tolerationBuilder.withKey(key);
        if (!("Exists".equals(operator) || "DoesNotExist".equals(operator))) {
            tolerationBuilder.withValue(value);
        }
        tolerationBuilder.withEffect(effect);
        return tolerationBuilder.build();
    }
}
