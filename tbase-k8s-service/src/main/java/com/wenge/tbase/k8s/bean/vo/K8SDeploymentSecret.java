package com.wenge.tbase.k8s.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "容器Secret配置")
public class K8SDeploymentSecret {
    @ApiModelProperty(value = "secret名称")
    private String secretName;

    @ApiModelProperty(value = "volume名称")
    private String name;
    @ApiModelProperty(value = "挂载目录")
    private String mountPath;
}
