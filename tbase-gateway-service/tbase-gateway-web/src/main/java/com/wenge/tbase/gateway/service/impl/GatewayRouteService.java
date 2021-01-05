package com.wenge.tbase.gateway.service.impl;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wenge.tbase.commons.entity.ErrorType;
import com.wenge.tbase.gateway.config.BusConfig;
import com.wenge.tbase.gateway.dao.GatewayRouteMapper;
import com.wenge.tbase.gateway.entity.form.GatewayRouteQueryForm;
import com.wenge.tbase.gateway.entity.po.GatewayRoute;
import com.wenge.tbase.gateway.entity.vo.GatewayRouteVo;
import com.wenge.tbase.gateway.entity.vo.GatewayRouteVoPage;
import com.wenge.tbase.gateway.events.EventSender;
import com.wenge.tbase.gateway.exception.WengeException;
import com.wenge.tbase.gateway.service.IGatewayRouteService;

import io.lettuce.core.cluster.RedisClusterClient;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GatewayRouteService extends ServiceImpl<GatewayRouteMapper, GatewayRoute> implements IGatewayRouteService {

	private static final String GATEWAY_ROUTES = "gateway_routes::";

	@CreateCache(name = GATEWAY_ROUTES, cacheType = CacheType.REMOTE)
	private Cache<String, RouteDefinition> gatewayRouteCache;

	@Autowired
	private EventSender eventSender;
	@Autowired
	private GatewayRouteMapper gatewayRouteMapper;

	@Override
	@Transactional
	public boolean add(GatewayRoute gatewayRoute) {
		String uri = gatewayRoute.getUri();
		gatewayRoute.setUri(convertUri(uri));			
		if(!charge(gatewayRoute))
			throw new WengeException(ErrorType.ROUTE_DUPLICATE);
		String status = gatewayRoute.getStatus();
		if(org.apache.commons.lang3.StringUtils.isBlank(gatewayRoute.getStatus())) {
			status="N";
			gatewayRoute.setStatus(status);
		}			
		boolean isSuccess = this.save(gatewayRoute);
		// 转化为gateway需要的类型，缓存到redis, 并事件通知各网关应用
		if(isSuccess) {
			RouteDefinition routeDefinition = gatewayRouteToRouteDefinition(gatewayRoute);
			gatewayRouteCache.put(gatewayRoute.getRouteId(), routeDefinition);
			JSONObject sendObj = new JSONObject();
			sendObj.put("ACTION_KEY", "UPDATE");
			sendObj.put("ROUTES_OBJ", routeDefinition);
			eventSender.send(BusConfig.ROUTING_KEY, sendObj);	
		}			
		return isSuccess;
	}
	
	private boolean charge(GatewayRoute gatewayRoute) {	
		boolean flag=true;		
		List<GatewayRoute> grs=gatewayRouteMapper.selectList(new QueryWrapper<GatewayRoute>().eq("server_name", gatewayRoute.getServerId()));
		if(StringUtils.isNotBlank(gatewayRoute.getId())){
			grs=grs.stream().filter(item -> !item.getId().equals(gatewayRoute.getId())).collect(Collectors.toList());
		}
		List<String> pathList=new ArrayList<String>();
		List<String> pathList1=new ArrayList<String>();
		if(!CollectionUtils.isEmpty(grs)) {			
			ObjectMapper objectMapper = new ObjectMapper();
			try {			
				for (GatewayRoute gr : grs) {
					List<PredicateDefinition> predicates = objectMapper.readValue(gr.getPredicates(),
							new TypeReference<List<PredicateDefinition>>() {
							});
					List<PredicateDefinition> predicates1 = objectMapper.readValue(gatewayRoute.getPredicates(),
							new TypeReference<List<PredicateDefinition>>() {
							});
					for (PredicateDefinition predicateDefinition : predicates) {
						if("Path".equals(predicateDefinition.getName())) {
							pathList.addAll(predicateDefinition.getArgs().values().stream().collect(Collectors.toList()));
							break;					
						}					
					}				
					for (PredicateDefinition predicateDefinition : predicates1) {
						if("Path".equals(predicateDefinition.getName())) {
							pathList1=predicateDefinition.getArgs().values().stream().collect(Collectors.toList());
							break;					
						}					
					}
				}
				
				List<String> pathList2=pathList1;
				List<String>  intersection =pathList.stream().filter(item -> find(item.toString(), pathList2) > -1).collect(Collectors.toList());
				if(!CollectionUtils.isEmpty(intersection))
					flag=false;
			} catch (IOException e) {
				e.printStackTrace();
				flag=false;
				throw new WengeException();
			}			
		}
		return flag;
	}

	@Override
	public boolean delete(String id) {
		GatewayRoute gatewayRoute = this.getById(id);
	
		boolean isSuccess=this.removeById(id);
		if(isSuccess) {
			// 删除redis缓存, 并事件通知各网关应用
			gatewayRouteCache.remove(gatewayRoute.getRouteId());
			JSONObject sendObj = new JSONObject();
			sendObj.put("ACTION_KEY", "DEL");
			sendObj.put("ROUTES_OBJ", gatewayRouteToRouteDefinition(gatewayRoute));
			eventSender.send(BusConfig.ROUTING_KEY, sendObj);
		}
		return isSuccess;
	}

	@Override
	public boolean update(GatewayRoute gatewayRoute) {
		if(!charge(gatewayRoute))
			throw new WengeException(ErrorType.ROUTE_DUPLICATE);
		String uri = gatewayRoute.getUri();
		gatewayRoute.setUri(convertUri(uri));
		boolean isSuccess = this.updateById(gatewayRoute);
		// 转化为gateway需要的类型，缓存到redis, 并事件通知各网关应用
		if(isSuccess) {
			RouteDefinition routeDefinition = gatewayRouteToRouteDefinition(gatewayRoute);
			gatewayRouteCache.put(gatewayRoute.getRouteId(), routeDefinition);
			JSONObject sendObj = new JSONObject();
			sendObj.put("ACTION_KEY", "UPDATE");
			sendObj.put("ROUTES_OBJ", routeDefinition);
			eventSender.send(BusConfig.ROUTING_KEY, sendObj);
		}		
		return isSuccess;
	}
	
	@Override
	public boolean updateStatus(String id, String status) {
		GatewayRoute gatewayRoute=gatewayRouteMapper.selectById(id);
		if(null == gatewayRoute)
			throw new WengeException(ErrorType.GATEWAY_NOT_FOUND_SERVICE);
		gatewayRoute.setStatus(status);
		boolean isSuccess = this.updateById(gatewayRoute);
		if(isSuccess) {
			RouteDefinition routeDefinition = gatewayRouteToRouteDefinition(gatewayRoute);
			gatewayRouteCache.put(gatewayRoute.getRouteId(), routeDefinition);
			JSONObject sendObj = new JSONObject();
			sendObj.put("ACTION_KEY", "UPDATE");
			sendObj.put("ROUTES_OBJ", routeDefinition);
			eventSender.send(BusConfig.ROUTING_KEY, sendObj);
		}	
		return isSuccess;
	}
	
	

	/**
	 * 将数据库中json对象转换为网关需要的RouteDefinition对象
	 *
	 * @param gatewayRoute
	 * @return RouteDefinition
	 */
	private RouteDefinition gatewayRouteToRouteDefinition(GatewayRoute gatewayRoute) {
		RouteDefinition routeDefinition = new RouteDefinition();
		routeDefinition.setId(gatewayRoute.getRouteId());
		routeDefinition.setOrder(gatewayRoute.getOrders());
		routeDefinition.setUri(URI.create(gatewayRoute.getUri()));
		Map<String,Object> metaMap=new Hashtable<String, Object>();
		String status =gatewayRoute.getStatus();
		if(org.apache.commons.lang3.StringUtils.isBlank(status))
			status="N";
		metaMap.put("status", status);
		routeDefinition.setMetadata(metaMap);
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			routeDefinition.setFilters(
					objectMapper.readValue(gatewayRoute.getFilters(), new TypeReference<List<FilterDefinition>>() {
					}));
			routeDefinition.setPredicates(objectMapper.readValue(gatewayRoute.getPredicates(),
					new TypeReference<List<PredicateDefinition>>() {
					}));
		} catch (IOException e) {
			log.error("网关路由对象转换失败", e);
		}
		return routeDefinition;
	}

	@Override
	public GatewayRoute get(String id) {
		return this.getById(id);
	}

	@Override
	@SuppressWarnings("all")
	public GatewayRouteVoPage query(GatewayRouteQueryForm gatewayRouteQueryParam) {
		LambdaQueryWrapper<GatewayRoute> queryWrapper = gatewayRouteQueryParam.build();
		queryWrapper.eq(StringUtils.isNotBlank(gatewayRouteQueryParam.getStatus()), GatewayRoute::getStatus,
				gatewayRouteQueryParam.getStatus());
		
		if(StringUtils.isNotBlank(gatewayRouteQueryParam.getDescription()) && StringUtils.isNotBlank(gatewayRouteQueryParam.getRouteId())) {
			
			queryWrapper.eq(StringUtils.isNotBlank(gatewayRouteQueryParam.getServerId()),GatewayRoute::getServerId,
					gatewayRouteQueryParam.getServerId()).and(i ->     	
							i
							.like(GatewayRoute::getRouteId,
									gatewayRouteQueryParam.getRouteId())
							.or().like(GatewayRoute::getDescription,
									gatewayRouteQueryParam.getDescription()));	
		}else {
			queryWrapper.eq(StringUtils.isNotBlank(gatewayRouteQueryParam.getServerId()),GatewayRoute::getServerId,
					gatewayRouteQueryParam.getServerId());
		}		
		queryWrapper.orderByDesc(GatewayRoute::getCreatedTime);
		IPage gatewayRoutes = this.page(gatewayRouteQueryParam.getPage(), queryWrapper);
		List<GatewayRoute> gatewayRouteList = gatewayRoutes.getRecords();
		List<GatewayRouteVo> gatewayRouteVoList = new LinkedList<>();
		for (GatewayRoute gatewayRoute : gatewayRouteList) {
			gatewayRouteVoList.add(new GatewayRouteVo(gatewayRoute));
		}
		GatewayRouteVoPage result = new GatewayRouteVoPage();
		result.setGatewayRouteList(gatewayRouteVoList);
		result.setCurrent(gatewayRoutes.getCurrent());
		result.setSize(gatewayRoutes.getSize());
		result.setTotal(gatewayRoutes.getTotal());
		result.setTotalPages(gatewayRoutes.getPages());
		return result;
	}
	 @Autowired
	   private RedisClusterClient stringRedisTemplate;//lettuce

	@Override
	@PostConstruct
	public boolean overload() {		
//		
		List<String> keys=  stringRedisTemplate.connect().sync().keys(GATEWAY_ROUTES + "*");
		for (String key : keys) {
			stringRedisTemplate.connect().sync().del(key);
		}
		List<GatewayRoute> gatewayRoutes = this.list(new QueryWrapper<>());
		gatewayRoutes.forEach(gatewayRoute -> gatewayRouteCache.put(gatewayRoute.getRouteId(),
				gatewayRouteToRouteDefinition(gatewayRoute)));
		log.info("全局初使化网关路由成功!");
		return true;
	}

	/**
	 * 转换uri
	 * 
	 * @return uri
	 */
	private String convertUri(String uri) {
		if (!uri.contains("lb://") && !uri.contains("http://"))
			uri = "lb://" + uri;
		return uri;
	}
	
	private static int find(String serverNmae,List<String> list) {
        int res = -1;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(serverNmae)) {
                res = i;
                break;
            }
        }
        return res;
    }

	
}
