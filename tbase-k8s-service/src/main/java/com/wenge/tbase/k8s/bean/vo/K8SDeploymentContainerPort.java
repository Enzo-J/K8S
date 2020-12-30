package com.wenge.tbase.k8s.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "Deployment容器Port配置")
public class K8SDeploymentContainerPort {
    @ApiModelProperty(value = "容器Port名称")
    private String name;
    @ApiModelProperty(value = "容器port")
    private int containerPort;
    @ApiModelProperty(value = "容器协议,TCP或者HTTP")
    private String protocol;
}
