package com.wenge.tbase.k8s.bean.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "环境变量配置")
public class K8SDeploymentEnvironmentVar {
    private String name;
    private String val;
}
