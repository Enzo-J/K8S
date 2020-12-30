package com.wenge.tbase.k8s.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel(value = "容器http探测配置")
@Data
public class K8SDeploymentHttpProbe extends K8sDeploymentProbe {
    @ApiModelProperty(value = "http探测host")
    private String host;
    @ApiModelProperty(value = "http探测path")
    private String path;
    @ApiModelProperty(value = "http探测port")
    private int port;
    @ApiModelProperty(value = "http探测headers")
    private List<K8SDeploymentHttpProbeHeader> headers;
}
