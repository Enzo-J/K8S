package com.wenge.tbase.k8s.bean.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "存储返参")
public class K8SStorage {

    @ApiModelProperty(value = "存储卷声明")
    private String pvClaim;

    @ApiModelProperty(value = "容量")
    private String capacity;

    @ApiModelProperty(value = "状态")
    private String status;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "名称")
    private String name;

}
