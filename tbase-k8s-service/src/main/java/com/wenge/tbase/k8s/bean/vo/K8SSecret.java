package com.wenge.tbase.k8s.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Map;

@Data
@ApiModel(value = "密文")
public class K8SSecret {

    @ApiModelProperty(value = "docker地址")
    private String dockerServer;

    @ApiModelProperty(value = "docker用户名")
    private String dockerUsername;

    @ApiModelProperty(value = "docker密码")
    private String dockerSecret;

    @NotBlank
    @ApiModelProperty(value = "命名空间")
    private String namespace;

    @NotBlank
    @ApiModelProperty(value = "密文名称")
    private String secretName;

    @NotBlank
    @ApiModelProperty(value = "密文类型 'kubernetes.io/dockerconfigjson','Opaque','TLS'")
    private String type;

    @ApiModelProperty(value = "tls.crt")
    private String tlsCrt;

    @ApiModelProperty(value = "tls.key")
    private String tlsKey;

    @ApiModelProperty(value = "密文配置")
    private Map<String,String> data;
}
