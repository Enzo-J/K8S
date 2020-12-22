package com.wenge.tbase.cicd.entity.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: PackageBuildParam
 * @Description: PackageBuildParam
 * @Author: Wang XingPeng
 * @Date: 2020/12/8 10:10
 */
@ApiModel(description = "编译打包参数类")
@Data
public class PackageParam {

    @ApiModelProperty(value = "阶段名称")
    private String stageName;

    @ApiModelProperty(value = "项目名称")
    private String projectName;

    @ApiModelProperty(value = "打包类型 1.java 2.nodeJS")
    private Integer packageType;

//    @ApiModelProperty(value = "打包工具 1.maven 2.npm")
//    private Integer packageTool;

//    @ApiModelProperty(value = "打包脚本")
//    private String packageShell;
}
