package com.wenge.tbase.k8s.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Map;

@Data
@ApiModel(value = "数据卷信息")
public class K8SVolumeInfo {
    @NotBlank(message = "数据卷名不能为空")
    @ApiModelProperty(value = "数据卷名")
    private String name;

    @NotBlank(message = "数据卷类型不能为空")
    @ApiModelProperty(value = "数据卷类型")
    private String type;

    @ApiModelProperty(value = "NFS服务器地址")
    private String NFSServer;

    @ApiModelProperty(value = "NFS服务器内的路径")
    private String NFSPath;

    @ApiModelProperty(value = "存储卷声明名字")
    private String pvcName;

    @ApiModelProperty(value = "host path节点上的路径")
    private String path;

    @ApiModelProperty(value = "host path类型")
    private String hostPathType;

    @ApiModelProperty(value = "configMap名字")
    private String configMapName;

    @ApiModelProperty(value = "configMap key -> 映射目标路径")
    private Map<String, String> configMapKeyToPath;

    @ApiModelProperty(value = "secret名字")
    private String secretName;

    @ApiModelProperty(value = "secret key -> 映射目标路径")
    private Map<String, String> secretKeyToPath;
}
