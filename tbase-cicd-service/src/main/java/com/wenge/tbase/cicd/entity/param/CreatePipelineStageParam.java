package com.wenge.tbase.cicd.entity.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

/**
 * @ClassName: CreatePipelineStageParam
 * @Description: CreatePipelineStageParam
 * @Author: Wang XingPeng
 * @Date: 2020/12/2 15:34
 */
@ApiModel(description = "创建流水线参数类")
@Data
public class CreatePipelineStageParam {

    @ApiModelProperty(value = "流水线编号")
    private Long pipelineId;

//    @ApiModelProperty(value = "全局变量")
//    private Map<String, String> globalVariable;

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
