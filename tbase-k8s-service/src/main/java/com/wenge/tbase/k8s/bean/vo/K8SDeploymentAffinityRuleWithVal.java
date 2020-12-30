package com.wenge.tbase.k8s.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "Deployment亲和性、反亲和性（非 Exist、NotExist）")
public class K8SDeploymentAffinityRuleWithVal {
    @ApiModelProperty(value = "values")
    private List<String> values;
}
