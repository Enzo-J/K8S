package com.wenge.tbase.k8s.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "容器存活探测配置")
public class K8sDeploymentProbe {
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
}
