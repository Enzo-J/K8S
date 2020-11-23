package com.wenge.tbase.cicd.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName: Jenkins
 * @Description: Jenkins
 * @Author: Wang XingPeng
 * @Date: 2020/11/21 10:46
 */
@Data
@Component
@ConfigurationProperties(prefix = "project.jenkins")
public class Jenkins {
    /**
     * jenkins uri
     */
    private String uri;

    /**
     * jenkins 账号
     */
    private String username;
    /**
     * jenkins 密码
     */
    private String password;

    /**
     * jenkins 远程构建token
     */
    private String token;
}
