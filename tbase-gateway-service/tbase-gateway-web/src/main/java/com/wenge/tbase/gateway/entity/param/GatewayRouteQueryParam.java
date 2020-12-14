package com.wenge.tbase.gateway.entity.param;

import com.wenge.tbase.gateway.entity.base.BaseParam;
import com.wenge.tbase.gateway.entity.po.GatewayRoute;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GatewayRouteQueryParam extends BaseParam<GatewayRoute> {
    private String uri;
}
