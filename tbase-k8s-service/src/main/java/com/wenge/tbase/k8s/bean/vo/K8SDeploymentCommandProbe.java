package com.wenge.tbase.k8s.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel(value = "容器command探测配置")
@Data
public class K8SDeploymentCommandProbe extends K8sDeploymentProbe {
    @ApiModelProperty(value = "command")
    private List<String> command;
}
