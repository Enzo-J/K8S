package com.wenge.tbase.k8s.bean.vo.deployment;

import io.fabric8.kubernetes.api.model.HTTPHeader;
import io.fabric8.kubernetes.api.model.HTTPHeaderBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "容器http探测配置header")
@Data
public class K8SDeploymentHttpProbeHeader {
    @ApiModelProperty(value = "header名称")
    private String name;
    @ApiModelProperty(value = "header值")
    private String val;

    public HTTPHeader httpHeader() {
        HTTPHeaderBuilder httpHeaderBuilder = new HTTPHeaderBuilder();
        httpHeaderBuilder.withName(name);
        httpHeaderBuilder.withValue(val);
        return httpHeaderBuilder.build();
    }
}
