package com.wenge.tbase.gateway.entity.param;

import java.util.ArrayList;
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

@ApiModel
@Data
public class GatewayRouteParam {
	@NotEmpty(message = "�����жϹ�����Ϊ��")
	@ApiModelProperty(value = "�����жϹ���")
	private List<RuleDef> ruleDefs = new ArrayList<>();

	@NotBlank(message = "uri����Ϊ��")
	@ApiModelProperty(value = "����uri")
	private String uri;

	@NotBlank(message = "·��id����Ϊ��")
	@ApiModelProperty(value = "����·��id")
	private String routeId;

	@ApiModelProperty(value = "����")
	private Integer orders;
	
	@NotBlank(message = "·�ɰ󶨷���������Ϊ��")
	@ApiModelProperty(value = "·�ɰ󶨷���ID")
	private String  serverId;
	
	@ApiModelProperty(value = "Y : ����״̬ N��δ����״̬")
	private String status;

	@ApiModelProperty(value = "����·��������Ϣ")
	private String description;

	@ApiModelProperty(value = "�⻧id")
	private String tenantId;

	public GatewayRouteForm toForm() {
		GatewayRouteForm gatewayRouteForm = new GatewayRouteForm();
		List<FilterDefinition> filters = gatewayRouteForm.getFilters();
		/* ������� */
		List<PredicateDefinition> predicates = gatewayRouteForm.getPredicates();
		/* Ĭ�Ϲ��� start */
		FilterDefinition filter = new FilterDefinition();
		filter.setName("StripPrefix");
		Map<String, String> map = filter.getArgs();
		map.put("parts", "1");
		filter.setArgs(map);
		filters.add(filter);
		/* Ĭ�Ϲ��� end */
		BeanUtils.copyProperties(this, gatewayRouteForm);
		for (RuleDef ruleDef : ruleDefs) {
			PredicateDefinition pdf = new PredicateDefinition();
			int type = ruleDef.getType();
			int matchType = ruleDef.getMatchType();
			String name = convertType(type);
			Map<String, String> pdfMap = pdf.getArgs();
			pdf.setName(name);
			// �Ƿ�ģ�����߾�ȷ
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
				// ģ��
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
