package com.wenge.tbase.cicd.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName: BuildStatusEnum
 * @Description: BuildStatusEnum
 * @Author: Wang XingPeng
 * @Date: 2020/12/16 16:10
 */
@Getter
@AllArgsConstructor
public enum BuildStatusEnum {

    FAILURE(0, "FAILURE"),
    SUCCESS(1, "SUCCESS"),
    RUNNING(2, "null");

    private Integer buildStatus;

    private String result;

    public static Integer getBuildStatus(String result) {
        for (BuildStatusEnum b : BuildStatusEnum.values()) {
            if (result.equals(b.getResult())) {
                return b.getBuildStatus();
            }
        }
        return null;
    }

}
