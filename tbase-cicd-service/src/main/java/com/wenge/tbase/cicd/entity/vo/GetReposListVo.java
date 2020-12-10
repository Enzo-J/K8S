package com.wenge.tbase.cicd.entity.vo;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName: GetReposList
 * @Description: GetReposList
 * @Author: Wang XingPeng
 * @Date: 2020/12/10 15:14
 */
@Data
public class GetReposListVo {

    @ApiModelProperty(value = "编号")
    private Long id;

    @ApiModelProperty(value = "凭证编号")
    private String credentialId;

    @ApiModelProperty(value = "代码仓库名称")
    private String name;

    @ApiModelProperty(value = "项目名称")
    private String projectName;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "乐观锁")
    private Integer version;

    @ApiModelProperty(value = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtModified;
}
