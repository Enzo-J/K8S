package com.wenge.tbase.k8s.bean.vo.deployment;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "Deployment更新策略")
public class K8SDeploymentRollingUpdateStrategy {
    private String maxUnavailable;
    private String maxSurge;
}
