package com.wenge.tbase.cicd.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class LargeScreenPipelineVo {
    /**
     * 流水线名称
     */
    private String name;

    /**
     * 流水线状态
     */
    private Integer runningStatus;
    /**
     * 流水线时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtModified;

}
