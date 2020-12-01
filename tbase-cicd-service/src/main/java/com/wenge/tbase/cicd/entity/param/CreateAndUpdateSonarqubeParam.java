package com.wenge.tbase.cicd.entity.param;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName: CreateAndUpdateSonarqubeParam
 * @Description: CreateAndUpdateSonarqubeParam
 * @Author: Wang XingPeng
 * @Date: 2020/11/30 16:41
 */
@Data
@ApiModel(description = "添加或更新sonarqube文件")
public class CreateAndUpdateSonarqubeParam {

    @ApiModelProperty(value = "编号")
    private Long id;

    @ApiModelProperty(value = "文件名称")
    private String name;

    @ApiModelProperty(value = "文件内容")
    private String content;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "乐观锁")
    private Integer version;

}
