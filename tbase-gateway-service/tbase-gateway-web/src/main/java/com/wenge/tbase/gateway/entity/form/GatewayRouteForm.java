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

    @NotEmpty(message = "缃戝叧鏂█涓嶈兘涓虹┖")
    @ApiModelProperty(value = "缃戝叧鏂█")
    private List<PredicateDefinition> predicates = new ArrayList<>();
    
    @ApiModelProperty(value = "缃戝叧杩囨护鍣ㄤ俊鎭�")
    private List<FilterDefinition> filters = new ArrayList<>();
    
    @NotBlank(message = "uri涓嶈兘涓虹┖")
    @ApiModelProperty(value = "缃戝叧uri")
    private String uri;    

    @NotBlank(message = "璺敱缁戝畾鏈嶅姟鍚嶄笉鑳戒负绌�")
    @ApiModelProperty(value = "璺敱缁戝畾鏈嶅姟鍚�")
    private String  serverId;    

    @NotBlank(message = "璺敱id涓嶈兘涓虹┖")
    @ApiModelProperty(value = "缃戝叧璺敱id")
    private String routeId;
    
    @ApiModelProperty(value = "鎺掑簭")
    private Integer orders;

    @ApiModelProperty(value = "缃戝叧璺敱鎻忚堪淇℃伅")
    private String description;   
    
    @ApiModelProperty(value = "Y : 鍙戝竷鐘舵�� N锛氭湭鍙戝竷鐘舵��")
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
            log.error("缃戝叧filter鎴杙redicates閰嶇疆杞崲寮傚父", e);
        }
        return gatewayRoute;
    }
}
