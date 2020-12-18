package com.wenge.tbase.cicd.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "Deployment部署参数")
public class K8SDeployment {
    @ApiModelProperty(value = "deployment名称")
    private String name;
    @ApiModelProperty(value = "所属空间")
    private String namespace;
    @ApiModelProperty(value = "容器名称")
    private String serviceName;
    @ApiModelProperty(value = "端口号")
    private Integer port;
    @ApiModelProperty(value = "分配的cpu量")
    private Double cpu;
    @ApiModelProperty(value = "分配的内存量")
    private Double memory;
    @ApiModelProperty(value = "镜像地址")
    private String imageUrl;
    @ApiModelProperty(value = "对外映射的端口")
    private Integer nodePort;
    @ApiModelProperty(value = "最大副本数")
    private Integer maxReplicas;
    @ApiModelProperty(value = "最小副本数")
    private Integer minReplicas;
    @ApiModelProperty(value = "部署模型")
    private String deployMode;
    @ApiModelProperty(value = "扩容CPU临界线")
    private Integer targetCPUUtilizationPercentage;
}
