package com.wenge.tbase.nacos.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author Wang XingPeng
 * @since 2020-12-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="MicroserviceRegistry对象", description="")
public class MicroserviceRegistry implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(value = "注册中心名称")
    private String name;

    @ApiModelProperty(value = "注册中心地址")
    private String url;

    @ApiModelProperty(value = "注册中心唯一标志")
    private String registryCode;

    @ApiModelProperty(value = "注册中心描述")
    private String registryDesc;

    @ApiModelProperty(value = "注册中心的状态：1：表示运行，-1：表示停用")
    private Integer registryStatus;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "是否删除，1：不删除，-1：删除")
    private Integer isDelete;


}
