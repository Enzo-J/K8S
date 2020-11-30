package com.wenge.tbase.cicd.entity.vo;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName: GetDockerfileListVo
 * @Description: GetDockerfileListVo
 * @Author: Wang XingPeng
 * @Date: 2020/11/30 11:06
 */
@Data
public class GetDockerfileListVo {
    @ApiModelProperty(value = "编号")
    private Long id;

    @ApiModelProperty(value = "文件名称")
    private String name;

    @ApiModelProperty(value = "文件内容")
    private String content;

    @ApiModelProperty(value = "乐观锁")
    private Integer version;

    @ApiModelProperty(value = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtModified;
}
