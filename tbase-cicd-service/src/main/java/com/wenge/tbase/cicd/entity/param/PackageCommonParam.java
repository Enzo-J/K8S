package com.wenge.tbase.cicd.entity.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: PackageCommonParam
 * @Description: PackageCommonParam
 * @Author: Wang XingPeng
 * @Date: 2020/12/9 11:13
 */
@ApiModel(description = "打包公共子工程参数类")
@Data
public class PackageCommonParam {

    @ApiModelProperty(value = "阶段名称")
    private String stageName;

    @ApiModelProperty(value = "项目名称")
    private String projectName;
}
