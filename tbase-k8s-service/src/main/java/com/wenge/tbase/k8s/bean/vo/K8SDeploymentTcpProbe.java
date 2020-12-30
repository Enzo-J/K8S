package com.wenge.tbase.k8s.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel(value = "容器http探测配置")
@Data
public class K8SDeploymentTcpProbe extends K8sDeploymentProbe {
    @ApiModelProperty(value = "tcp探测port")
    private int port;
}
