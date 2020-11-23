package com.wenge.tbase.nacos.service.impl;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.wenge.tbase.nacos.config.ConstantConfig;
import com.wenge.tbase.nacos.result.RestResult;
import com.wenge.tbase.nacos.result.WengeStatusEnum;
import com.wenge.tbase.nacos.service.NacosNsService;
import com.wenge.tbase.nacos.utils.JsoupUtils;
@Service
public class NacosNsServiceImpl implements NacosNsService{
	@Override
	public RestResult<?> getLeader() {
		JSONObject result;
		try {
			result = JsoupUtils.get(ConstantConfig.nacosNsAddress+"/raft/leader");
			return RestResult.ok(result);
		} catch (Exception e) {
			return RestResult.error(WengeStatusEnum.NOT_FIND_RESOURCE.getMsg());
		}
	}

	@Override
	public RestResult<?> updateHealthInstance(HashMap<String, String> instanceMap) {
		String result;
		try {
			result = JsoupUtils.put(ConstantConfig.nacosNsAddress+"/health/instance",instanceMap);
			return RestResult.ok(result);
		} catch (Exception e) {
			return RestResult.error(WengeStatusEnum.NOT_FIND_RESOURCE.getMsg());
		}
	}

}
