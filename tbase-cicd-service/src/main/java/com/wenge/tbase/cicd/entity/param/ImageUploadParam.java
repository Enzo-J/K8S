package com.wenge.tbase.cicd.entity.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

/**
 * @ClassName: CodeUploadParam
 * @Description: CodeUploadParam
 * @Author: Wang XingPeng
 * @Date: 2020/12/10 17:09
 */
@ApiModel(description = "镜像上传参数类")
@Data
public class ImageUploadParam {

    @ApiModelProperty(value = "阶段名称")
    private String stageName;

    @ApiModelProperty(value = "代码仓库编号")
    private Long reposId;

    @ApiModelProperty(value = "镜像名称")
    private String imageName;

    @ApiModelProperty(value = "镜像版本号")
    private String imageTag;

}
