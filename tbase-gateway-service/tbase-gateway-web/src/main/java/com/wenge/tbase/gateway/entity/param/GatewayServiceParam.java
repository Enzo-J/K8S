package com.wenge.tbase.gateway.entity.param;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.BeanUtils;

import com.wenge.tbase.gateway.entity.po.GatewayService;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class GatewayServiceParam {

	@NotBlank(message = "服务名称不能为空")
	@ApiModelProperty(value = "服务名称")
    private String serviceName;

    @ApiModelProperty(value = "服务标识名称")
    private String tagName;    
 
    @ApiModelProperty(value = "服务描述")
    private String description;   

//    @ApiModelProperty(value = "创建时间")
//    @TableField(fill = FieldFill.INSERT)
//    private Date createdTime;
//
//    @ApiModelProperty(value = "更新时间")
//    @TableField(fill = FieldFill.INSERT_UPDATE)
//    private Date updatedTime;
    
    @NotBlank(message = "服务类型不能为空")
    @ApiModelProperty(value = "服务类型 http、https")
    private String serverType;
    
   
    @ApiModelProperty(value = "服务实例swagger文档地址")
    private String serviceInstanceApiUrl;
    
    @NotEmpty(message = "服务url不能为空")
    @ApiModelProperty(value = "服务url")
    private String serviceUri;

    
    public GatewayService toPo(){
    	GatewayService gatewayService =new GatewayService();    	
    	BeanUtils.copyProperties(this, gatewayService);
    	gatewayService.setStatus("Y");
    	gatewayService.setSynchronize("N");
    	return gatewayService;
    }

}
