package com.wenge.tbase.cicd.entity.vo;

import com.baomidou.mybatisplus.annotation.Version;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName: GetCredentialListVo
 * @Description: GetCredentialListVo
 * @Author: Wang XingPeng
 * @Date: 2020/11/27 16:48
 */
@Data
public class GetCredentialListVo {

    private Integer id;

    private String credentialId;

    private Integer type;

    private String username;

    private String password;

    private String privateKey;

    private String description;

    private Integer version;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtModified;
}
