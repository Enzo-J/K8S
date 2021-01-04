package com.wenge.tbase.k8s.bean.vo.deployment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "K8S kv键值对，用于label,annotation,environment")
public class K8SKV {
    @ApiModelProperty(value = "类型,0 值,1 configmap,2 secert")
    private int type;
    @ApiModelProperty(value = "key")
    private String key;
    @ApiModelProperty(value = "value")
    private String value;
    @ApiModelProperty(value = "configmap 或者 secert key ref, ")
    private String ref;
}
