package com.wenge.tbase.k8s.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "Deployment Volume配置")
public class K8SDeploymentVolume {
    @ApiModelProperty(value = "数据卷类型,默认为0,0 EmptyDir,1 PVC, 2 ConfigMap, 3 Secret, 4 EmptyDir, 5 HostPath")
    private int type;
    @ApiModelProperty(value = "volume名称")
    private String volumeName;
    @ApiModelProperty(value = "container挂载目录")
    private String mountPath;
    @ApiModelProperty(value = "volume子目录")
    private String subPath;
    @ApiModelProperty(value = "是否只读")
    private boolean readOnly;
    @ApiModelProperty(value = "hostPath->Path")
    private String hostPathPath;
    @ApiModelProperty(value = "hostPath->Type")
    private String hostPathType;
}
