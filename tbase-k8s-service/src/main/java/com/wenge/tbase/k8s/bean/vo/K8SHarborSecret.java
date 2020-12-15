package com.wenge.tbase.k8s.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "Harbor密码")
public class K8SHarborSecret {

    @ApiModelProperty(value = "docker地址")
    private String dockerServer;

    @ApiModelProperty(value = "docker用户名")
    private String dockerUsername;

    @ApiModelProperty(value = "docker密码")
    private String dockerSecret;

    @ApiModelProperty(value = "命名空间")
    private String namespace;

    @ApiModelProperty(value = "Harbor密码名称")
    private String secretName;

}
