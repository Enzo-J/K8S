package com.wenge.tbase.cicd.entity.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: UpdatePipelineStatus
 * @Description: UpdatePipelineStatus
 * @Author: Wang XingPeng
 * @Date: 2020/12/17 17:29
 */
@ApiModel(description = "修改流水线状态参数类")
@Data
public class UpdatePipelineStatusParam {
    @ApiModelProperty(value = "流水线编号")
    private Long id;

    @ApiModelProperty(value = "执行结果 0.执行失败 1.执行成功 2.未执行 默认2")
    private Integer execResult;

    @ApiModelProperty(value = "0.已终止 1.运行中")
    private Integer runningStatus;
}
