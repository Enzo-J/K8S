package com.wenge.tbase.k8s.bean.vo.deployment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "Deployment亲和性、反亲和性")
public class K8SDeploymentAffinityRule {
    @ApiModelProperty(value = "1 Required, 0 Preferred")
    protected int type;
    @ApiModelProperty(value = "亲和性、反亲和性 key")
    protected String key;
    @ApiModelProperty(value = "亲和性、反亲和性操作符")
    protected String operator;
    @ApiModelProperty(value = "values")
    private List<String> values;
}
