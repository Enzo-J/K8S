package com.wenge.tbase.gateway.entity.param;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.BeanUtils;

import com.wenge.tbase.gateway.entity.form.GatewayRouteForm;
import com.wenge.tbase.gateway.entity.po.FilterDefinition;
import com.wenge.tbase.gateway.entity.po.PredicateDefinition;
import com.wenge.tbase.gateway.entity.po.RuleDef;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@ApiModel
@Data
@Slf4j
public class GatewayRouteParam {
	@NotEmpty(message = "网关判断规则不能为空")
	@ApiModelProperty(value = "网关判断规则")
	private List<RuleDef> ruleDefs = new ArrayList<>();

	@NotBlank(message = "uri不能为空")
	@ApiModelProperty(value = "网关uri")
	private String uri;

	@NotBlank(message = "路由id不能为空")
	@ApiModelProperty(value = "网关路由id")
	private String routeId;

	@ApiModelProperty(value = "排序")
	private Integer orders;
	
	@NotBlank(message = "路由绑定服务名不能为空")
	@ApiModelProperty(value = "路由绑定服务名")
	private String  serverName;
	
	@ApiModelProperty(value = "Y : 发布状态 N：未发布状态")
	private String status;

	@ApiModelProperty(value = "网关路由描述信息")
	private String description;

	@ApiModelProperty(value = "租户id")
	private String tenantId;

	public GatewayRouteForm toForm() {
		GatewayRouteForm gatewayRouteForm = new GatewayRouteForm();
		List<FilterDefinition> filters = gatewayRouteForm.getFilters();
		/* 处理规则 */
		List<PredicateDefinition> predicates = gatewayRouteForm.getPredicates();
		/* 默认过滤 start */
		FilterDefinition filter = new FilterDefinition();
		filter.setName("StripPrefix");
		Map<String, String> map = filter.getArgs();
		map.put("parts", "1");
		filter.setArgs(map);
		filters.add(filter);
		/* 默认过滤 end */
		BeanUtils.copyProperties(this, gatewayRouteForm);
		for (RuleDef ruleDef : ruleDefs) {
			PredicateDefinition pdf = new PredicateDefinition();
			int type = ruleDef.getType();
			int matchType = ruleDef.getMatchType();
			String name = convertType(type);
			Map<String, String> pdfMap = pdf.getArgs();
			pdf.setName(name);
			// 是否模糊或者精确
			if (matchType == 0) {
				// Path
				if (type == 1) {
					int index = 0;
					for (String matching : ruleDef.getMatching()) {
						pdfMap.put("pattern" + index, "/" + matching + "/**");
						index++;
					}
				} else
				// Header
				if (ruleDef.getType() == 4) {
					pdfMap.put("header", ruleDef.getParamKey());
					StringBuilder str = new StringBuilder();
					for (String matching : ruleDef.getMatching()) {
						str.append(matching + "|");
					}
					str.replace(str.lastIndexOf("|"), str.lastIndexOf("|") + 1, "");
					pdfMap.put("regexp", str.toString());
				} else
				// Query
				if (ruleDef.getType() == 5) {
					pdfMap.put("param", ruleDef.getParamKey());
					StringBuilder str = new StringBuilder();
					for (String matching : ruleDef.getMatching()) {
						str.append(matching + "|");
					}
					str.replace(str.lastIndexOf("|"), str.lastIndexOf("|") + 1, "");
					pdfMap.put("regexp", str.toString());
				} else {
					// Method Host
					int index = 0;
					for (String matching : ruleDef.getMatching()) {
						pdfMap.put("pattern" + index, matching);
						index++;
					}
				}
				// 模糊
			} else if (matchType == 1) {
				
				// Path
				if (type == 1) {
					int index = 0;
					for (String matching : ruleDef.getMatching()) {
						pdfMap.put("pattern" + index, "/**" + matching + "**/**");
						index++;
					}
				} else
				// Header
				if (ruleDef.getType() == 4) {
					pdfMap.put("header", ruleDef.getParamKey());
					StringBuilder str = new StringBuilder();
					for (String matching : ruleDef.getMatching()) {
						str.append("**"+matching + "**|");
					}
					str.replace(str.lastIndexOf("|"), str.lastIndexOf("|") + 1, "");
					pdfMap.put("regexp", str.toString());
				} else
				// Query
				if (ruleDef.getType() == 5) {
					pdfMap.put("param", ruleDef.getParamKey());
					StringBuilder str = new StringBuilder();
					for (String matching : ruleDef.getMatching()) {
						str.append("**"+matching + "**|");
					}
					str.replace(str.lastIndexOf("|"), str.lastIndexOf("|") + 1, "");
					pdfMap.put("regexp", str.toString());
				} else {
					// Method Host
					int index = 0;
					for (String matching : ruleDef.getMatching()) {
						pdfMap.put("pattern" + index, "**"+matching+"**");
						index++;
					}
				}
				

			}
			predicates.add(pdf);

		}
		gatewayRouteForm.setPredicates(predicates);
		gatewayRouteForm.setFilters(filters);
		return gatewayRouteForm;
	}

	private String convertType(Integer type) {
		switch (type) {
		case 1:
			return "Path";

		case 2:
			return "Host";

		case 3:
			return "Method";

		case 4:
			return "Header";
		case 5:
			return "Query";

		default:
			return "Path";
		}
	}

}
