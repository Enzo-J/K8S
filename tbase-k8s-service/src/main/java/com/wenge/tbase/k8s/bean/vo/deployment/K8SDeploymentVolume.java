package com.wenge.tbase.k8s.bean.vo.deployment;

import io.fabric8.kubernetes.api.model.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
@ApiModel(value = "Deployment Volume配置")
public class K8SDeploymentVolume {
    @ApiModelProperty(value = "数据卷类型,默认为0,0 EmptyDir,1 PVC, 2 ConfigMap, 3 Secret, 4 EmptyDir, 5 HostPath")
    private int type;
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
        VolumeMountBuilder volumeMountBuilder = new VolumeMountBuilder().withName(volumeName);
        volumeMountBuilder.withMountPath(mountPath);
        if (StringUtils.isNotBlank(subPath)) {
            volumeMountBuilder.withSubPath(subPath);
        }
        volumeMountBuilder.withNewReadOnly(readOnly);

        return volumeMountBuilder.build();
    }

    public EmptyDirVolumeSource emptyDirVolumeSource() {
//        EmptyDirVolumeSourceBuilder emptyDirVolumeSourceBuilder = new EmptyDirVolumeSourceBuilder().withSizeLimit(Quantity.parse(emptyDirSizeLimit));
//        Volume volume = new VolumeBuilder().withEmptyDir(emptyDirVolumeSourceBuilder.build())；
        return null;
    }

    public HostPathVolumeSource hostPathVolumeSource() {
        return new HostPathVolumeSourceBuilder().withPath(hostPathPath).withType(hostPathType).build();
    }
}
