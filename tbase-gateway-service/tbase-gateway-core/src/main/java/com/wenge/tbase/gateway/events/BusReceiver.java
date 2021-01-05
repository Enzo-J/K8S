package com.wenge.tbase.gateway.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.wenge.tbase.gateway.service.IRouteService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class BusReceiver {

    @Autowired
    private IRouteService routeService;

    public void handleMessage(JSONObject routeDefinition) {
        log.info("Received Message:<{}>", routeDefinition);
        RouteDefinition rdf= routeDefinition.getObject("ROUTES_OBJ",RouteDefinition.class);    
        String actionKey=routeDefinition.getString("ACTION_KEY");
        switch(actionKey) {
        case "UPDATE":
        	 // 实现动态更新路由
        	routeService.save(rdf);
        	break;
        case "DEL":
        	 // 实现动态del路由
        	routeService.delete(rdf.getId());
        	break;        
        }
        
    }
}
