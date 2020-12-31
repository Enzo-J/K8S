package com.wenge.tbase.gateway.entity.form;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wenge.tbase.gateway.entity.base.BaseForm;
import com.wenge.tbase.gateway.entity.po.FilterDefinition;
import com.wenge.tbase.gateway.entity.po.GatewayRoute;
import com.wenge.tbase.gateway.entity.po.PredicateDefinition;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@EqualsAndHashCode(callSuper = true)
@ApiModel
@Data
@Slf4j
public class GatewayRouteForm extends BaseForm<GatewayRoute> {

    @NotEmpty(message = "网关断言不能为空")
    @ApiModelProperty(value = "网关断言")
    private List<PredicateDefinition> predicates = new ArrayList<>();
    
    @ApiModelProperty(value = "网关过滤器信息")
    private List<FilterDefinition> filters = new ArrayList<>();
    
    @NotBlank(message = "uri不能为空")
    @ApiModelProperty(value = "网关uri")
    private String uri;    

    @NotBlank(message = "路由绑定服务名不能为空")
    @ApiModelProperty(value = "路由绑定服务名")
    private String  serverName;    

    @NotBlank(message = "路由id不能为空")
    @ApiModelProperty(value = "网关路由id")
    private String routeId;
    
    @ApiModelProperty(value = "排序")
    private Integer orders;

    @ApiModelProperty(value = "网关路由描述信息")
    private String description;   
    
    @ApiModelProperty(value = "Y : 发布状态 N：未发布状态")
	private String status;
    

    @Override
    public GatewayRoute toPo(Class<GatewayRoute> clazz) {    
        GatewayRoute gatewayRoute = new GatewayRoute();
        BeanUtils.copyProperties(this, gatewayRoute);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            gatewayRoute.setFilters(objectMapper.writeValueAsString(this.getFilters()));
            gatewayRoute.setPredicates(objectMapper.writeValueAsString(this.getPredicates()));
        } catch (JsonProcessingException e) {
            log.error("网关filter或predicates配置转换异常", e);
        }
        return gatewayRoute;
    }
}
