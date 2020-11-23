package com.wenge.tbase.nacos.service.impl;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.wenge.tbase.nacos.config.ConstantConfig;
import com.wenge.tbase.nacos.result.RestResult;
import com.wenge.tbase.nacos.result.WengeStatusEnum;
import com.wenge.tbase.nacos.service.NacosOperatorService;
import com.wenge.tbase.nacos.utils.JsoupUtils;
@Service
public class NacosOperatorServiceImpl implements NacosOperatorService{

	@Override
	public RestResult<?> getSwitches() {
		JSONObject result;
			try {
				result = JsoupUtils.get(ConstantConfig.nacosOperatorAddress);
				return RestResult.ok(result);
			} catch (Exception e) {
				return RestResult.error(WengeStatusEnum.NOT_FIND_RESOURCE.getMsg());
			}
	}

	@Override
	public RestResult<?> putSwitches(HashMap<String, String> serviceMap) {
		String result;
		try {
			result = JsoupUtils.put(ConstantConfig.nacosOperatorAddress,serviceMap);
			return RestResult.ok(result);
		} catch (Exception e) {
			return RestResult.error(WengeStatusEnum.NOT_FIND_RESOURCE.getMsg());
		}
	}

	@Override
	public RestResult<?> getMetrics() {
		JSONObject result;
		try {
			result = JsoupUtils.get(ConstantConfig.nacosOperatorAddress+"/metrics");
			return RestResult.ok(result);
		} catch (Exception e) {
			return RestResult.error(WengeStatusEnum.NOT_FIND_RESOURCE.getMsg());
		}
	}

	@Override
	public RestResult<?> getServices(HashMap<String, String> serviceMap) {
		JSONObject result;
		try {
			result = JSONObject.parseObject(JsoupUtils.get(ConstantConfig.nacosOperatorAddress+"/servers",serviceMap));
			return RestResult.ok(result);
		} catch (Exception e) {
			return RestResult.error(WengeStatusEnum.NOT_FIND_RESOURCE.getMsg());
		}
	}
  
}
