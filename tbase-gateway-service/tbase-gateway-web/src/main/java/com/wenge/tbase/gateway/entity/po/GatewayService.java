package com.wenge.tbase.gateway.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author dangwei
 * @since 2020-12-17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(value="服务管理", description="网关内的服务管理")
public class GatewayService implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "服务id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "服务名称")
    private String serviceName;

    @ApiModelProperty(value = "服务标识名称")
    private String tagName;

    @ApiModelProperty(value = "服务状态：Y-已上线，N-下线")
    private String status;

    @ApiModelProperty(value = "服务描述")
    private String description;

    @ApiModelProperty(value = "服务实例swagger文档地址")
    private String serviceInstanceApiUrl;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdTime;    

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatedTime;

    @ApiModelProperty(value = "服务类型 HTTP、HTTPS")
    private String serverType;

    @ApiModelProperty(value = "服务url")
    private String serviceUri;

    @ApiModelProperty(value = "同步状态：Y-同步，N-未同步")
    private String synchronize;


}
