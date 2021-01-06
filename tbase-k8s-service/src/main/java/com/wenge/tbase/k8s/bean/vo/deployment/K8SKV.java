package com.wenge.tbase.k8s.bean.vo.deployment;

import io.fabric8.kubernetes.api.model.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
@ApiModel(value = "K8S kv键值对，用于label,annotation,environment")
public class K8SKV {
    @ApiModelProperty(value = "类型,0 值,1 configmap,2 secert,3 Field,4 Resource")
    private int type;
    @ApiModelProperty(value = "key")
    private String key;
    @ApiModelProperty(value = "value")
    private String value;
    @ApiModelProperty(value = "configmap 或者 secert key ref, ")
    private String ref;


    public EnvVar envVar() {
        return new EnvVarBuilder().withName(key).withValue(value).build();
    }


    public EnvFromSource envFromSource() {
        EnvFromSourceBuilder envFromSourceBuilder = new EnvFromSourceBuilder();
        if (type == 1) {
            ConfigMapEnvSource configMapEnvSource = new ConfigMapEnvSourceBuilder().withName(value).build();
            envFromSourceBuilder.withConfigMapRef(configMapEnvSource);
        } else {
            SecretEnvSource secretEnvSource = new SecretEnvSourceBuilder().withName(value).build();
            envFromSourceBuilder.withSecretRef(secretEnvSource);
        }
        if (StringUtils.isNotBlank(ref)) {
            envFromSourceBuilder.withPrefix(ref);
        }
        return envFromSourceBuilder.build();
    }
}
