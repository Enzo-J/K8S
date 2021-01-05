package com.wenge.tbase.k8s.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Map;


@Data
@ApiModel(value = "配置文件参数")
public class K8SConfigMap {

    //@NotBlank(message = "配置数据不能为空")
    @ApiModelProperty(value = "配置数据")
    private Map<String, String> data;

    @NotBlank(message = "所处命名空间不能为空")
    @ApiModelProperty(value = "所处命名空间")
    private String namespace;

    @NotBlank(message = "配置文件名称不能为空")
    @ApiModelProperty(value = "配置文件名称")
    private String name;

    @ApiModelProperty(value = "标签集合")
    private Map<String, String> labels;

}
