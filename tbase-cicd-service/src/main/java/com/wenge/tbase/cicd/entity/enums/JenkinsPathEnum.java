package com.wenge.tbase.cicd.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * @ClassName: JenkinsPathEnum
 * @Description: JenkinsPathEnum
 * @Author: Wang XingPeng
 * @Date: 2020/11/23 15:03
 */
@Getter
@AllArgsConstructor
public enum JenkinsPathEnum {

    CREDENTIAL_PATH("/credentials/store/system/domain/_/createCredentials","凭证地址");

    private String path;

    private String description;

}
