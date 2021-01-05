package com.wenge.tbase.gateway.filter;

import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;

import com.wenge.tbase.gateway.service.IRouteService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Configuration
@Slf4j
public class LocationFilter implements GlobalFilter {

	@Autowired
	IRouteService routeService;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		String httpReferer = exchange.getRequest().getHeaders().getFirst("Referer");
		log.info("当前访问的Referer:{}",httpReferer);
		Pattern patternLocation = Pattern
				.compile("^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+$");
		if (StringUtils.isNotBlank(httpReferer)) {
			if (patternLocation.matcher(httpReferer).matches()) {
				String routePath = "";
				if (httpReferer.contains("//")) {
					if (httpReferer.split("//")[1].split("/").length >= 3) {
						routePath = httpReferer.split("//")[1].split("/")[2];
					}
				} else {
					if (httpReferer.split("/").length >= 3) {
						routePath = httpReferer.split("/")[2];
					}
				}
				Collection<RouteDefinition> routeDefinitions = routeService.getRouteDefinitions();
				if (!CollectionUtils.isEmpty(routeDefinitions)) {
										
					for (RouteDefinition routeDefinition : routeDefinitions) {
						boolean flag = false;
						String status=routeDefinition.getMetadata().get("status").toString();										
						for (PredicateDefinition predicates : routeDefinition.getPredicates()) {
							if (flag)
								break;
							if ("Path".equals(predicates.getName())) {
								for (Map.Entry<String, String> entry : predicates.getArgs().entrySet()) {
									String value=entry.getValue().substring(entry.getValue().indexOf("/") + 1,
											entry.getValue().lastIndexOf("/")).replace("*", "");																
									if (routePath.equals(value)) {
										flag = true;
										break;
									}
								}
							}
						}						
						if (flag && status.equals("N"))							
							return forbidden(exchange,status);		
					}
				}
			}
		}
		return chain.filter(exchange);
	}	
		
	/**
	 * 网关拒绝，返回403
	 *
	 * @param
	 */
	private Mono<Void> forbidden(ServerWebExchange serverWebExchange,String status) {
		serverWebExchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
		DataBuffer buffer = serverWebExchange.getResponse().bufferFactory()
				.wrap(HttpStatus.FORBIDDEN.getReasonPhrase().getBytes());
		log.warn("请求被拒绝当迁Referer:{},status:{}",serverWebExchange.getRequest().getHeaders().getFirst("Referer"),status);
		System.err.println("***%%%%%%%%%%:"+status);		
		return serverWebExchange.getResponse().writeWith(Flux.just(buffer));
	}
	

}
