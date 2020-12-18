package com.wenge.tbase.cicd.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName: BuildHistoryVo
 * @Description: BuildHistoryVo
 * @Author: Wang XingPeng
 * @Date: 2020/12/16 15:32
 */
@Data
public class BuildHistoryVo {

    /**
     * 构建序号
     */
    private Integer buildNumber;

    /**
     * 构建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date buildDate;

    /**
     * 构建状态
     */
    private Integer buildStatus;
}
