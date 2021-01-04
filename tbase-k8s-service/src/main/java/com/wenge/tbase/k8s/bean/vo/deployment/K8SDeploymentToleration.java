package com.wenge.tbase.k8s.bean.vo.deployment;

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
    private String value = "";
    @ApiModelProperty(value = "effect为NoSchedule、PreferNoSchedule、NoExecute")
    private String effect;
}
