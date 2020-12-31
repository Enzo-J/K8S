package com.wenge.tbase.gateway.service;

import java.util.List;

import com.wenge.tbase.gateway.entity.form.GatewayRouteQueryForm;
import com.wenge.tbase.gateway.entity.po.GatewayRoute;
import com.wenge.tbase.gateway.entity.vo.GatewayRouteVo;
import com.wenge.tbase.gateway.entity.vo.GatewayRouteVoPage;

public interface IGatewayRouteService {
    /**
     * 获取网关路由
     *
     * @param id
     * @return
     */
    GatewayRoute get(String id);

    /**
     * 新增网关路由
     *
     * @param gatewayRoute
     * @return
     */
    boolean add(GatewayRoute gatewayRoute);

    /**
     * 查询网关路由
     *
     * @return
     */
    GatewayRouteVoPage query(GatewayRouteQueryForm gatewayRouteQueryParam);

    /**
     * 更新网关路由信息
     *
     * @param gatewayRoute
     */
    boolean update(GatewayRoute gatewayRoute);
    
    
    boolean updateStatus(String id ,String status);

    /**
     * 根据id删除网关路由
     *
     * @param id
     */
    boolean delete(String id);

    /**
     * 重新加载网关路由配置到redis
     *
     * @return 成功返回true
     */
    boolean overload();
}
