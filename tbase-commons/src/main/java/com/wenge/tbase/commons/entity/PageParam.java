package com.wenge.tbase.commons.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: PageParam
 * @Description: PageParam
 * @Author: Wang XingPeng
 * @Date: 2020/8/26 10:07
 */
@Data
@ApiModel("分页查询请求参数")
public class PageParam {

    @ApiModelProperty(value = "分页开始页")
    private Integer current;

    @ApiModelProperty(value = "分页数据量")
    private Integer size;
}
