package com.wenge.tbase.gateway.entity.form;

import com.wenge.tbase.gateway.entity.base.BaseQueryForm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@ApiModel
@Data
public class GatewayRouteQueryForm extends BaseQueryForm {

    @ApiModelProperty(value = "路由发布状态")
    private String status;
    
    @ApiModelProperty(value = "路由id")
    private String routeId;
    
    @ApiModelProperty(value = "路由描述")
    private String description;
    
    @ApiModelProperty(value = "路由绑定服务名")
    private String  serverName;
   
    
}
