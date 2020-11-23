package com.wenge.tbase.cicd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OverviewVo {

    /**
     * 执行成功
     */
    private Integer execSuccessCount;

    /**
     * 执行失败
     */
    private Integer execFailureCount;

    /**
     * 未执行
     */
    private Integer unExecCount;

    /**
     * 近期执行流水线列表
     */
    private List<LargeScreenPipelineVo> pipelineList;

}
