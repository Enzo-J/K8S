package com.wenge.tbase.k8s.bean.vo;

import io.fabric8.kubernetes.api.model.EnvFromSource;
import io.fabric8.kubernetes.api.model.EnvVar;
import io.fabric8.kubernetes.api.model.Volume;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

@Data
@ApiModel(value = "K8SDeployment")
public class K8SDeployment {
    @NotBlank
    @ApiModelProperty(value = "deployment名称")
    private String name;

    @NotBlank
    @ApiModelProperty(value = "所属空间")
    private String namespace;

    /*@NotBlank
    @ApiModelProperty(value = "服务分层")
    private String serviceSlice;*/

    @ApiModelProperty(value = "注解")
    private Map<String, String> annotations;

    @NotBlank
    @ApiModelProperty(value = "标签")
    private Map<String, String> labels;

    @ApiModelProperty(value = "服务描述")
    private String description;

    @NotBlank
    @ApiModelProperty(value = "副本数量")
    private int replicas;

    @NotBlank
    @ApiModelProperty(value = "容器名称")
    private String serviceName;

    @NotBlank
    @ApiModelProperty(value = "镜像地址")
    private String imageUrl;

    @ApiModelProperty(value = "环境变量(名值对)")
    private List<EnvVar> env;

    @ApiModelProperty(value = "环境变量导入")
    private List<EnvFromSource> envFrom;

    /*@ApiModelProperty(value = "数据卷")
    private List<K8SVolumeInfo> volume;*/

    @ApiModelProperty(value = "数据卷")
    private List<Volume> volume;

    @ApiModelProperty(value = "端口号")
    private Integer port;

    @ApiModelProperty(value = "连接方式")
    private String protocolType;

    @ApiModelProperty(value = "分配的cpu量")
    private Double cpu;
    @ApiModelProperty(value = "分配的内存量")
    private Double memory;
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
