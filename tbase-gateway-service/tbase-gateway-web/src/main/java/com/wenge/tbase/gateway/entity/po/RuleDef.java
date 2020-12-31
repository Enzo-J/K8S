package com.wenge.tbase.gateway.entity.po;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RuleDef {	
	
	@NotEmpty(message = "网关判断类型不能为空")
	@ApiModelProperty(value = "网关过滤类型，{1：Path 2:Host 3:Method 4:Header 5:Query}")
	private int type;
	
	@NotEmpty(message = "匹配类型不能为空")
	@ApiModelProperty(value = "网关过滤类型，{0：精确匹配 1：模糊匹配}")
	private int matchType;
	
	@NotBlank(message = "paramKey不能为空")
	@ApiModelProperty(value = "当类型为Hearder 和Query 时，此值为参数Key,其他类型可忽略")
	private String paramKey;
	
	@NotEmpty(message = "匹配条件不能为空")
	@ApiModelProperty(value = "匹配条件数组")
	private String [] matching;
	
}
