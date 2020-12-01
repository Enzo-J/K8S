package com.wenge.tbase.cicd.entity.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: CreateAndUpdateCredentialParam
 * @Description: CreateAndUpdateCredentialParam
 * @Author: Wang XingPeng
 * @Date: 2020/11/27 10:35
 */
@Data
@ApiModel(description = "添加和更新凭证参数")
public class CreateAndUpdateCredentialParam {

    @ApiModelProperty(value = "编号 修改时使用")
    private Long id;

    @ApiModelProperty(value = "凭证编号 修改时使用")
    private String credentialId;

    @ApiModelProperty(value = "凭证类型 1.password 2.ssh")
    private Integer type;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码 仅限凭证类型为1的时候使用")
    private String password;

    @ApiModelProperty(value = "ssh秘钥 仅限凭证类型为2的时候使用")
    private String privateKey;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "乐观锁 修改时使用")
    private Integer version;
}
