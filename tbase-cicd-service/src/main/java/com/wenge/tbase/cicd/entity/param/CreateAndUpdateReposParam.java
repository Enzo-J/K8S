package com.wenge.tbase.cicd.entity.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @ClassName: CreateAndUpdateReposParam
 * @Description: CreateAndUpdateReposParam
 * @Author: Wang XingPeng
 * @Date: 2020/12/10 14:29
 */
@Data
@ApiModel(description = "创建和修改代码仓库参数类")
public class CreateAndUpdateReposParam {

    @ApiModelProperty(value = "编号 修改时使用")
    private Long id;

    @ApiModelProperty(value = "凭证编号")
    private String credentialId;

    @ApiModelProperty(value = "代码仓库名称")
    private String name;

    @ApiModelProperty(value = "项目名称")
    private String projectName;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "乐观锁 修改时使用")
    private Integer version;
}
