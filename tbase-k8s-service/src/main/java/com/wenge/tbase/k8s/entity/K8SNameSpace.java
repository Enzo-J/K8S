package com.wenge.tbase.k8s.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "K8S命名空间返参")
public class K8SNameSpace {

    @ApiModelProperty(value = "命名空间名称")
    private String name;

    @ApiModelProperty(value = "命名空间状态   true正常 false 异常")
    private boolean status;

}
