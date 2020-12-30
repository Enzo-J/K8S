package com.wenge.tbase.k8s.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@ApiModel(value = "存储卷声明参数")
public class K8SPersistentVolumeClaim {

    @NotBlank(message = "所处命名空间不能为空")
    @ApiModelProperty(value = "所处命名空间")
    private String namespace;

    @NotBlank(message = "pvc名称不能为空")
    @ApiModelProperty(value = "pvc名称")
    private String name;

    @NotBlank(message = "存储类不能为空")
    @ApiModelProperty(value = "存储类")
    private String storageType;

    @NotBlank(message = "读写模式不能为空")
    @ApiModelProperty(value = "读写模式")
    private List<String> accessmode;

    @NotBlank(message = "总量不能为空")
    @ApiModelProperty(value = "总量")
    private String total;
}
