package com.wenge.tbase.k8s.bean.vo.deployment;

import io.fabric8.kubernetes.api.model.ContainerPort;
import io.fabric8.kubernetes.api.model.ContainerPortBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "Deployment容器Port配置")
public class K8SDeploymentContainerPort {
    @ApiModelProperty(value = "容器Port名称")
    private String name;
    @ApiModelProperty(value = "容器port")
    private int containerPort;
    @ApiModelProperty(value = "容器协议,TCP或者UDP")
    private String protocol;


    public ContainerPort containerPort() {
        return new ContainerPortBuilder().withName(name).withContainerPort(containerPort).withProtocol(protocol).build();
    }
}
