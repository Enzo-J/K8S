package com.wenge.tbase.k8s.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;



@Data
@ApiModel(value = "配置文件参数")
public class K8SConfigMap {

    @ApiModelProperty(value = "配置参数")
    private Map<String,String> data;

    @ApiModelProperty(value = "所处命名空间")
    private String namespace;

    @ApiModelProperty(value = "配置文件名称")
    private String name;

    @ApiModelProperty(value = "标签集合")
    private Map<String,String> labels;

}
