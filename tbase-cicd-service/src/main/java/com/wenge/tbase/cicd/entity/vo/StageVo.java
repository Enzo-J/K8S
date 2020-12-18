package com.wenge.tbase.cicd.entity.vo;

import lombok.Data;

/**
 * @ClassName: StageVo
 * @Description: StageVo
 * @Author: Wang XingPeng
 * @Date: 2020/12/17 11:20
 */
@Data
public class StageVo {

    private Long id;

    private String name;

    private String status;

    private Long startTimeMillis;

    private Long durationMillis;

    private Long pauseDurationMillis;
}
