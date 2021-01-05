package com.wenge.tbase.gateway.entity.vo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wenge.tbase.gateway.entity.base.BaseVo;
import com.wenge.tbase.gateway.entity.po.FilterDefinition;
import com.wenge.tbase.gateway.entity.po.GatewayRoute;
import com.wenge.tbase.gateway.entity.po.PredicateDefinition;
import com.wenge.tbase.gateway.entity.po.RuleDef;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
@ApiModel("返回路由对象")
public class GatewayRouteVo extends BaseVo {
	@ApiModelProperty(value = "唯一ID")
	private String id;
	@ApiModelProperty(value = "租户id")
	private String tenantId;
	@ApiModelProperty(value = "路由名称")
	private String routeId;
	@ApiModelProperty(value = "路由描述")
	private String description;
	@ApiModelProperty(value = "路由状态 Y：可用 N:禁用")
	private String status;
	@ApiModelProperty(value = "路由的地址")
	private String uri;
	@ApiModelProperty(value = "路由绑定服务id")
	private String  serverId;
	@ApiModelProperty(value = "排序")
	private Integer orders;
	@ApiModelProperty(value = "创建人")
	private String createdBy;
	@ApiModelProperty(value = "创建时间")
	private String createdTime;
	@ApiModelProperty(value = "更新人")
	private String updatedBy;
	@ApiModelProperty(value = "更新时间")
	private String updatedTime;
	private List<RuleDef> ruleDefs = new ArrayList<>();
	public GatewayRouteVo() {}
	public GatewayRouteVo(GatewayRoute gatewayRoute) {
		this.id = gatewayRoute.getId();
		this.routeId = gatewayRoute.getRouteId();
		this.uri = gatewayRoute.getUri();
		this.description = gatewayRoute.getDescription();
		this.status = gatewayRoute.getStatus();
		this.orders = gatewayRoute.getOrders();
		this.createdBy = gatewayRoute.getCreatedBy();		
		this.createdTime = DateFormatUtils.format(gatewayRoute.getCreatedTime(),"yyyy-MM-dd HH:mm:ss");
		this.updatedBy = gatewayRoute.getUpdatedBy();
		this.updatedTime = DateFormatUtils.format(gatewayRoute.getUpdatedTime(),"yyyy-MM-dd HH:mm:ss");
		this.tenantId = gatewayRoute.getTenantId();
		this.serverId = gatewayRoute.getServerId();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			List<FilterDefinition> filters = objectMapper.readValue(gatewayRoute.getFilters(),
					new TypeReference<List<FilterDefinition>>() {
					});
			List<PredicateDefinition> predicates = objectMapper.readValue(gatewayRoute.getPredicates(),
					new TypeReference<List<PredicateDefinition>>() {
					});
			if (predicates != null && predicates.size() > 0) {
				for (PredicateDefinition pdf : predicates) {
					int type = convertType(pdf.getName());
					int matchType = 0;
					boolean flag = false;
					String paramKey = "";
					List<String> matchingList = new LinkedList<>();
					if (type == 1 || type == 2 || type == 3) {
						for (Map.Entry<String, String> entry : pdf.getArgs().entrySet()) {
							String k = entry.getKey();
							String v = entry.getValue();							
							if (type == 1) {
								v=v.substring(v.indexOf("/") + 1, v.lastIndexOf("/"));
								if (v.contains("**") && !flag) {
									matchType = 1;
									flag = true;
								}
								if (flag) {
									matchingList
											.add(v.replace("**", ""));
								} else {
									matchingList.add(v);
								}
							} else {
								if (v.contains("**") && !flag) {
									matchType = 1;
									flag = true;
								}
								if (flag) {
									matchingList.add(v.replace("**", ""));
								} else {
									matchingList.add(v);
								}
							}
						}
					} else {
						Map<String, String> ruleMap = pdf.getArgs();
						if (type == 4) {
							paramKey = ruleMap.get("header");
						} else if (type == 5) {
							paramKey = ruleMap.get("param");
						}
						for (String v : ruleMap.get("regexp").split("\\|")) {
							if (v.contains("**") && !flag) {
								matchType = 1;
								flag = true;
							}
							if (flag) {
								matchingList.add(v.replace("**", ""));
							} else {
								matchingList.add(v);
							}
						}
					}
					String[] macthing = new String[matchingList.size()];
					matchingList.toArray(macthing);
					RuleDef rdf = new RuleDef(type, matchType, paramKey, macthing);
					ruleDefs.add(rdf);
				}
			}
		} catch (IOException e) {
			log.error("网关路由对象转换失败", e);
		}

	}

	private int convertType(String type) {
		switch (type) {
		case "Path":
			return 1;
		case "Host":
			return 2;
		case "Method":
			return 3;
		case "Header":
			return 4;
		case "Query":
			return 5;
		default:
			return 1;
		}
	}

}
