package com.wenge.tbase.k8s.bean.vo;

import io.fabric8.kubernetes.api.model.EnvVarSource;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "K8SEnvVar")
public class K8SEnvVar {
    @ApiModelProperty(value = "name")
    private String name;
    @ApiModelProperty(value = "value")
    private String value;
    @ApiModelProperty(value = "valueFrom")
    private EnvVarSource valueFrom;
}
