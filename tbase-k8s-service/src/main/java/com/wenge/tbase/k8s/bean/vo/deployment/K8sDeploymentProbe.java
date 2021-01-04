package com.wenge.tbase.k8s.bean.vo.deployment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "容器存活探测配置")
public class K8sDeploymentProbe {
    @ApiModelProperty(value = "type,0 tcp, 1 http, 2 command")
    private int type;
    @ApiModelProperty(value = "http探测host")
    private String host;
    @ApiModelProperty(value = "http探测path")
    private String path;
    @ApiModelProperty(value = "port")
    private int port;
    @ApiModelProperty(value = "http探测headers")
    private List<K8SDeploymentHttpProbeHeader> headers;

    @ApiModelProperty(value = "健康阈值")
    protected int successThreshold;
    @ApiModelProperty(value = "故障阈值")
    protected int failureThreshold;
    @ApiModelProperty(value = "超时检测时间")
    protected int timeout;
    @ApiModelProperty(value = "初始等待时间")
    protected int initialDelay;
    @ApiModelProperty(value = "检测间隔时间")
    protected int period;

    @ApiModelProperty(value = "command")
    private List<String> command;
}
