package com.wenge.tbase.cicd.entity.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: ImageBuildParam
 * @Description: ImageBuildParam
 * @Author: Wang XingPeng
 * @Date: 2020/12/8 11:37
 */
@ApiModel(description = "镜像构建参数类")
@Data
public class ImageBuildParam {

    @ApiModelProperty(value = "阶段名称")
    private String stageName;

    @ApiModelProperty(value = "Dockerfile文件来源 1.代码库 2.来自平台配置")
    private Integer dockerfileSource;

    @ApiModelProperty(value = "Dockerfile文件地址")
    private String dockerfileAddress;

    @ApiModelProperty(value = "平台配置dockerfileId 来源为2时使用")
    private Long dockerfileId;

    @ApiModelProperty(value = "项目名称")
    private String projectName;

    @ApiModelProperty(value = "镜像名称")
    private String imageName;

    @ApiModelProperty(value = "镜像版本号")
    private String imageTag;


}
