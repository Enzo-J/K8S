package com.wenge.tbase.k8s.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "Deployment Pod亲和性、反亲和性")
public class K8SDeploymentPodAffinityRule {
    @ApiModelProperty(value = "weight, 1-100")
    protected int weight = 100;
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
}
