package com.wenge.tbase.k8s.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "容器ConfigMap配置")
public class K8SDeploymentConfigMap {
    @ApiModelProperty(value = "configMap名称")
    private String configMapName;

    @ApiModelProperty(value = "volume名称")
    private String name;
    @ApiModelProperty(value = "挂载目录")
    private String mountPath;
    @ApiModelProperty(value = "是否只读")
    private boolean readOnly;
}
