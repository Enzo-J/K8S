package com.wenge.tbase.cicd.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName: PipelineStageTypeEnum
 * @Description: PipelineStageTypeEnum
 * @Author: Wang XingPeng
 * @Date: 2020/12/3 14:21
 */
@Getter
@AllArgsConstructor
public enum PipelineStageTypeEnum {

    GLOBAL_VARIABLE(1, "全局配置"),
    CODE_PULL(2, "代码检出"),
    CODE_CHECK(3, "代码检查"),
    PACKAGE_COMMON(4, "编译打包公共工程"),
    PACKAGE(5, "编译打包"),
    IMAGE_BUILD(6, "镜像构建");

    private Integer type;

    private String name;
}
