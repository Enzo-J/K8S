package com.wenge.tbase.gateway.entity.po;

import com.wenge.tbase.gateway.entity.base.BasePo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GatewayRoute extends BasePo {
	private static final long serialVersionUID = 1L;
	private String uri;
    private String tenantId;
    private String routeId;
    private String predicates;
    private String filters;
    private String description;
    private Integer orders;
    private String status ;
    private String  serverId;
}
