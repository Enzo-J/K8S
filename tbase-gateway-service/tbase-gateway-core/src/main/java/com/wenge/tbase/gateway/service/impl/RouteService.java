package com.wenge.tbase.gateway.service.impl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import com.wenge.tbase.gateway.service.IRouteService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RouteService implements IRouteService {

	private static final String GATEWAY_ROUTES = "gateway_routes::";
	@Autowired
	private RedisTemplate<String, String> stringRedisTemplate;// lettuce

	@CreateCache(name = GATEWAY_ROUTES, cacheType = CacheType.REMOTE)
	private Cache<String, RouteDefinition> gatewayRouteCache;

	private Map<String, RouteDefinition> routeDefinitionMaps = new HashMap<>();

	@PostConstruct
	private void loadRouteDefinition() {
		log.info("loadRouteDefinition, ��ʼ��ʹ��·��");
		Set<String> gatewayKeys = stringRedisTemplate.keys(GATEWAY_ROUTES + "*");
		if (CollectionUtils.isEmpty(gatewayKeys)) {
			return;
		}
		log.info("Ԥ�Ƴ�ʹ��·��, gatewayKeys��{}", gatewayKeys);
		// ȥ��key��ǰ׺
		Set<String> gatewayKeyIds = gatewayKeys.stream().map(key -> {
			return key.replace(GATEWAY_ROUTES, StringUtils.EMPTY);
		}).collect(Collectors.toSet());
		Map<String, RouteDefinition> allRoutes = gatewayRouteCache.getAll(gatewayKeyIds);
		log.info("gatewayKeys��{}", allRoutes);
		// ���´���ԭ���ǣ�jetcache��RouteDefinition�����л���uri�����仯��δ��ʹ��������·���쳣�����´��������³�ʹ��uri
		allRoutes.values().forEach(routeDefinition -> {
			try {
				routeDefinition.setUri(new URI(routeDefinition.getUri().toASCIIString()));
			} catch (URISyntaxException e) {
				log.error("���ؼ���RouteDefinition�쳣��", e);
			}
		});
		routeDefinitionMaps.putAll(allRoutes);
		log.info("����ʹ��·����Ϣ��{}", routeDefinitionMaps.size());
	}

	@Override
	public Collection<RouteDefinition> getRouteDefinitions() {
		return routeDefinitionMaps.values();
	}

	@Override
	public boolean save(RouteDefinition routeDefinition) {
		routeDefinitionMaps.put(routeDefinition.getId(), routeDefinition);
		log.info("����·��1����{},Ŀǰ·�ɹ�{}��", routeDefinition, routeDefinitionMaps.size());
		return true;
	}

	@Override
	public boolean delete(String routeId) {
		routeDefinitionMaps.remove(routeId);
		log.info("ɾ��·��1����{},Ŀǰ·�ɹ�{}��", routeId, routeDefinitionMaps.size());
		return true;
	}
}
