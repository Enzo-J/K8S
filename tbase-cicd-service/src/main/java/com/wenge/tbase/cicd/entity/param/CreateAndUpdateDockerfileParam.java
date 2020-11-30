package com.wenge.tbase.cicd.entity.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: CreateAndUpdateDockerfileParam
 * @Description: CreateAndUpdateDockerfileParam
 * @Author: Wang XingPeng
 * @Date: 2020/11/30 11:14
 */
@Data
@ApiModel(value = "添加和更新dockerfile参数")
public class CreateAndUpdateDockerfileParam {

    @ApiModelProperty(value = "编号")
    private Long id;

    @ApiModelProperty(value = "文件名称")
    private String name;

    @ApiModelProperty(value = "文件内容")
    private String content;

    @ApiModelProperty(value = "乐观锁")
    private Integer version;
}
