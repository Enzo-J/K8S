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
        EnvVarBuilder envVarBuilder = new EnvVarBuilder();
        envVarBuilder.withName(key);
        if (type == 0) {
            envVarBuilder.withValue(value);
        } else {
            EnvVarSourceBuilder envVarSourceBuilder = new EnvVarSourceBuilder();
            if (type == 1) {
                ConfigMapKeySelectorBuilder configMapKeySelectorBuilder = new ConfigMapKeySelectorBuilder();
                configMapKeySelectorBuilder.withName(value);
                configMapKeySelectorBuilder.withKey(ref);
                envVarSourceBuilder.withConfigMapKeyRef(configMapKeySelectorBuilder.build());
            } else if (type == 2) {
                SecretKeySelectorBuilder secretKeySelectorBuilder = new SecretKeySelectorBuilder();
                secretKeySelectorBuilder.withName(value);
                secretKeySelectorBuilder.withKey(ref);
                envVarSourceBuilder.withSecretKeyRef(secretKeySelectorBuilder.build());
            } else if (type == 3) {
                ObjectFieldSelectorBuilder objectFieldSelectorBuilder = new ObjectFieldSelectorBuilder();
                objectFieldSelectorBuilder.withFieldPath(value);
                envVarSourceBuilder.withFieldRef(objectFieldSelectorBuilder.build());
            } else {
                ResourceFieldSelectorBuilder resourceFieldSelectorBuilder = new ResourceFieldSelectorBuilder();
                resourceFieldSelectorBuilder.withResource(ref);
                envVarSourceBuilder.withResourceFieldRef(resourceFieldSelectorBuilder.build());
            }
            envVarBuilder.withValueFrom(envVarSourceBuilder.build());
        }
        return envVarBuilder.build();
    }

    public EnvFromSource envFromSource() {
        EnvFromSourceBuilder envFromSourceBuilder = new EnvFromSourceBuilder();
        if (type == 1) {
            ConfigMapEnvSourceBuilder configMapEnvSourceBuilder = new ConfigMapEnvSourceBuilder().withName(value);
            ConfigMapEnvSource configMapEnvSource = configMapEnvSourceBuilder.build();
            envFromSourceBuilder.withConfigMapRef(configMapEnvSource);
        } else {
            SecretEnvSourceBuilder secretEnvSourceBuilder = new SecretEnvSourceBuilder().withName(value);
            SecretEnvSource secretEnvSource = secretEnvSourceBuilder.build();
            envFromSourceBuilder.withSecretRef(secretEnvSource);
        }
        if (StringUtils.isNotBlank(ref)) {
            envFromSourceBuilder.withPrefix(ref);
        }
        return envFromSourceBuilder.build();
    }
}
