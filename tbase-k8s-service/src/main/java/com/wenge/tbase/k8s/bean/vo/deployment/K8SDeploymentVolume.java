package com.wenge.tbase.k8s.bean.vo.deployment;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.fabric8.kubernetes.api.model.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
@ApiModel(value = "Deployment Volume配置")
public class K8SDeploymentVolume {
    @ApiModelProperty(value = "数据卷类型,默认为0,0 PVC, 1 ConfigMap, 2 Secret,3 EmptyDir, 4 HostPath")
    private int type;
    @ApiModelProperty(value = "volume挂载名称")
    private String volumeMountName;
    @ApiModelProperty(value = "volume名称")
    private String volumeName;
    @ApiModelProperty(value = "container挂载目录")
    private String mountPath;
    @ApiModelProperty(value = "volume子目录")
    private String subPath;
    @ApiModelProperty(value = "是否只读")
    private boolean readOnly;
    @ApiModelProperty(value = "hostPath->Path")
    private String hostPathPath;
    @ApiModelProperty(value = "hostPath->Type")
    private String hostPathType;
    @ApiModelProperty(value = "emptyDirSizeLimit")
    private String emptyDirSizeLimit;

    public VolumeMount volumeMount() {
        VolumeMountBuilder volumeMountBuilder = new VolumeMountBuilder().withName(volumeMountName);
        volumeMountBuilder.withMountPath(mountPath);
        if (StringUtils.isNotBlank(subPath)) {
            volumeMountBuilder.withSubPath(subPath);
        }
        volumeMountBuilder.withNewReadOnly(readOnly);

        return volumeMountBuilder.build();
    }

    private EmptyDirVolumeSource emptyDirVolumeSource() {
        EmptyDirVolumeSourceBuilder emptyDirVolumeSourceBuilder = new EmptyDirVolumeSourceBuilder().withSizeLimit(Quantity.parse(emptyDirSizeLimit));
        return emptyDirVolumeSourceBuilder.build();
    }

    public HostPathVolumeSource hostPathVolumeSource() {
        return new HostPathVolumeSourceBuilder().withPath(hostPathPath).withType(hostPathType).build();
    }

    private PersistentVolumeClaimVolumeSource persistentVolumeClaimVolumeSource() {
        PersistentVolumeClaimVolumeSource persistentVolumeClaimVolumeSource = new PersistentVolumeClaimVolumeSource();
        persistentVolumeClaimVolumeSource.setClaimName(volumeName);
        persistentVolumeClaimVolumeSource.setReadOnly(readOnly);
        return persistentVolumeClaimVolumeSource;
    }

    private ConfigMapVolumeSource configMapVolumeSource() {
        ConfigMapVolumeSourceBuilder configMapVolumeSourceBuilder = new ConfigMapVolumeSourceBuilder();
        configMapVolumeSourceBuilder.withName(volumeName);
        return configMapVolumeSourceBuilder.build();
    }

    private SecretVolumeSource secretVolumeSource() {
        SecretVolumeSourceBuilder secretVolumeSourceBuilder = new SecretVolumeSourceBuilder();
        secretVolumeSourceBuilder.withSecretName(volumeName);
        return secretVolumeSourceBuilder.build();
    }


    public Volume volume() {
        VolumeBuilder volumeBuilder = new VolumeBuilder();
        switch (type) {
            case 0:
                volumeBuilder.withName(volumeMountName);
                volumeBuilder.withPersistentVolumeClaim(persistentVolumeClaimVolumeSource());
                break;
            case 1:
                volumeBuilder.withName(volumeMountName);
                volumeBuilder.withConfigMap(configMapVolumeSource());
                break;
            case 2:
                volumeBuilder.withName(volumeMountName);
                volumeBuilder.withSecret(secretVolumeSource());
                break;
            case 3:
                volumeBuilder.withName(volumeMountName);
                volumeBuilder.withEmptyDir(emptyDirVolumeSource());
                break;
            case 4:
                volumeBuilder.withName(volumeMountName);
                volumeBuilder.withHostPath(hostPathVolumeSource());
                break;
            default:
        }
        return volumeBuilder.build();
    }
}
