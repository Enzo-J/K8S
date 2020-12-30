package com.wenge.tbase.k8s.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "容器PVC配置")
public class K8SDeploymentPVC {
    @ApiModelProperty(value = "pvc名称")
    private String claimName;

    @ApiModelProperty(value = "volume名称")
    private String name;
    @ApiModelProperty(value = "挂载目录")
    private String mountPath;
    @ApiModelProperty(value = "pvc子目录")
    private String subPath;
}
