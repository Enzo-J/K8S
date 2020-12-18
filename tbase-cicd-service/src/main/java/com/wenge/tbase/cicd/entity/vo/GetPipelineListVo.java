package com.wenge.tbase.cicd.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: GetPipelineListVo
 * @Description: GetPipelineListVo
 * @Author: Wang XingPeng
 * @Date: 2020/12/15 15:30
 */
@Data
public class GetPipelineListVo {
    @ApiModelProperty(value = "编号")
    private Long id;

    @ApiModelProperty(value = "流水线名称")
    private String name;

    @ApiModelProperty(value = "流水线描述")
    private String description;

    @ApiModelProperty(value = "执行结果 0.执行失败 1.执行成功 2.未执行 默认2")
    private Integer execResult;

    @ApiModelProperty(value = "0.已终止 1.运行中")
    private Integer runningStatus;

    @ApiModelProperty(value = "阶段状态")
    private List<StageVo> stageVoList;

    @ApiModelProperty(value = "禁用标志 0.未禁用 1.已禁用")
    private Integer disabled;

    @ApiModelProperty(value = "乐观锁")
    private Integer version;

    @ApiModelProperty(value = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtModified;
}
