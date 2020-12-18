package com.wenge.tbase.cicd.entity.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: UpdatePipelineParam
 * @Description: UpdatePipelineParam
 * @Author: Wang XingPeng
 * @Date: 2020/12/16 9:49
 */
@ApiModel(description = "修改流水线内容参数")
@Data
public class UpdatePipelineParam {

    @ApiModelProperty(value = "编号")
    private Long id;

    @ApiModelProperty(value = "流水线描述")
    private String description;

    @ApiModelProperty(value = "0.未禁用 1.已禁用")
    private Integer disabled;

    @ApiModelProperty(value = "乐观锁")
    private Integer version;

}
