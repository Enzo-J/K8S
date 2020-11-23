package com.wenge.tbase.k8s.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "K8S节点信息返参")
public class K8SNode {

    @ApiModelProperty(value = "节点名称")
    private String name;

    @ApiModelProperty(value = "已使用的cpu量")
    private Double usCpu;

    @ApiModelProperty(value = "已使用的内存量")
    private Double usMemory;


    @ApiModelProperty(value = "可用的CPU量")
    private Double allocatableCPU;


    @ApiModelProperty(value = "可用的内存量")
    private Double allocatableMemory;

    @ApiModelProperty(value = "节点状态   true 正常   false 异常")
    private Boolean status;
}
