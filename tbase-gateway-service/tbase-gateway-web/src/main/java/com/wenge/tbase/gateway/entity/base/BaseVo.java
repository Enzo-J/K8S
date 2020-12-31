package com.wenge.tbase.gateway.entity.base;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BaseVo<T extends BasePo> implements Serializable {
	private String id;
}
