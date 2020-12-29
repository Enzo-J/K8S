package com.wenge.tbase.cicd.entity.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: CodeCheckParam
 * @Description: CodeCheckParam
 * @Author: Wang XingPeng
 * @Date: 2020/12/3 11:27
 */
@ApiModel(description = "代码检测参数类")
@Data
public class CodeCheckParam {

    @ApiModelProperty(value = "编号")
    private Long id;

    @ApiModelProperty(value = "阶段名称")
    private String stageName;

    @ApiModelProperty(value = "sonar配置文件来源 1.代码仓库 2.选择sonar文件")
    private Integer sonarFileSource;

    @ApiModelProperty(value = "文件地址")
    private String sonarFileAddress;

    @ApiModelProperty(value = "sonar文件编号")
    private Integer sonarId;

}
