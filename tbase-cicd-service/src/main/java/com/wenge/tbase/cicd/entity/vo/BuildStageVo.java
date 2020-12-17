package com.wenge.tbase.cicd.entity.vo;

import lombok.Data;

import java.util.List;

/**
 * @ClassName: JenkinsBuildStageDTO
 * @Description: JenkinsBuildStageDTO
 * @Author: Wang XingPeng
 * @Date: 2020/12/17 11:05
 */
@Data
public class BuildStageVo {

    private Long id;

    private String name;

    private String status;

    private Long startTimeMillis;

    private Long endTimeMillis;

    private Long durationMillis;

    private List<StageVo> stages;

}
