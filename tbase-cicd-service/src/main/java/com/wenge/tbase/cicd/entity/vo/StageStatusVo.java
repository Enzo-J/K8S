package com.wenge.tbase.cicd.entity.vo;

import lombok.Data;

/**
 * @ClassName: StageStatusVo
 * @Description: StageStatusVo
 * @Author: Wang XingPeng
 * @Date: 2020/12/15 15:40
 */
@Data
public class StageStatusVo {

    private Long stageId;

    private String stageName;

    /**
     * 阶段状态  0.未执行 1.执行成功 2.执行失败
     */
    private Integer stageStatus;

    private Integer stageType;
}
