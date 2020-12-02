package com.wenge.tbase.cicd.entity.dto;

import lombok.Data;

/**
 * @ClassName: JenkinsTemplateDTO
 * @Description: JenkinsTemplateDTO
 * @Author: Wang XingPeng
 * @Date: 2020/12/2 10:57
 */
@Data
public class JenkinsTemplateDTO {

    /**
     * pipeline jenkinsfile脚本
     */
    private String script;

    /**
     * 远程构建token
     */
    private String token;
}
