package com.wenge.tbase.cicd.entity.vo;

import com.wenge.tbase.cicd.entity.param.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: GetPipelineStageVo
 * @Description: GetPipelineStageVo
 * @Author: Wang XingPeng
 * @Date: 2020/12/23 10:04
 */
@Data
public class GetPipelineStageVo {

    @ApiModelProperty(value = "代码检出参数类")
    private CodePullParam codePullParam;

    @ApiModelProperty(value = "代码检测参数类")
    private CodeCheckParam codeCheckParam;

    @ApiModelProperty(value = "编译打包公共工程参数类")
    private PackageCommonParam packageCommonParam;

    @ApiModelProperty(value = "编译打包参数类")
    private PackageParam packageParam;

    @ApiModelProperty(value = "镜像构建参数类")
    private ImageBuildParam imageBuildParam;

    @ApiModelProperty(value = "镜像上传参数类")
    private ImageUploadParam imageUploadParam;
}
