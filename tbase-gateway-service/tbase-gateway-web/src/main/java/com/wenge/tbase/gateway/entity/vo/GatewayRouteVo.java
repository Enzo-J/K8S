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
@ApiModel("����·�ɶ���")
public class GatewayRouteVo extends BaseVo {
	@ApiModelProperty(value = "ΨһID")
	private String id;
	@ApiModelProperty(value = "�⻧id")
	private String tenantId;
	@ApiModelProperty(value = "·������")
	private String routeId;
	@ApiModelProperty(value = "·������")
	private String description;
	@ApiModelProperty(value = "·��״̬ Y������ N:����")
	private String status;
	@ApiModelProperty(value = "·�ɵĵ�ַ")
	private String uri;
	@ApiModelProperty(value = "·�ɰ󶨷���id")
	private String  serverId;
	@ApiModelProperty(value = "����")
	private Integer orders;
	@ApiModelProperty(value = "������")
	private String createdBy;
	@ApiModelProperty(value = "����ʱ��")
	private String createdTime;
	@ApiModelProperty(value = "������")
	private String updatedBy;
	@ApiModelProperty(value = "����ʱ��")
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
			log.error("����·�ɶ���ת��ʧ��", e);
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
