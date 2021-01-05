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

    @ApiModelProperty(value = "璺敱鍙戝竷鐘舵��")
    private String status;
    
    @ApiModelProperty(value = "璺敱id")
    private String routeId;
    
    @ApiModelProperty(value = "璺敱鎻忚堪")
    private String description;
    
    @ApiModelProperty(value = "璺敱缁戝畾鏈嶅姟鍚�")
    private String  serverId;
   
    
}
