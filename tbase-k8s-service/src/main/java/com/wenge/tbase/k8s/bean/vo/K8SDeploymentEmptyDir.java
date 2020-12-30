package com.wenge.tbase.k8s.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "容器EmptyDir配置")
public class K8SDeploymentEmptyDir {
    @ApiModelProperty(value = "emptyDir名称")
    private String name;

    @ApiModelProperty(value = "挂载目录")
    private String mountPath;
}
