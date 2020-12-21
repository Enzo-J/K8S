package com.wenge.tbase.cicd.entity.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: CodePullParam
 * @Description: CodePullParam
 * @Author: Wang XingPeng
 * @Date: 2020/12/3 10:54
 */
@ApiModel(description = "代码检出阶段参数")
@Data
public class CodePullParam {

    @ApiModelProperty(value = "阶段名称")
    private String stageName;

    @ApiModelProperty(value = "仓库类型 1.git")
    private Integer codeType;

    @ApiModelProperty(value = "仓库地址")
    private String codeUrl;

    @ApiModelProperty(value = "凭证编号")
    private String credentialId;

    @ApiModelProperty(value = "分支")
    private String branch;
}
