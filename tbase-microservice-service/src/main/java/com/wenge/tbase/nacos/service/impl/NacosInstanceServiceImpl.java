package com.wenge.tbase.nacos.service.impl;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.wenge.tbase.nacos.config.ConstantConfig;
import com.wenge.tbase.nacos.result.RestResult;
import com.wenge.tbase.nacos.result.WengeStatusEnum;
import com.wenge.tbase.nacos.service.NacosInstanceService;
import com.wenge.tbase.nacos.utils.JsoupUtils;
@Service
public class NacosInstanceServiceImpl implements NacosInstanceService {

	@Override
	public RestResult<?> registeredInstance(HashMap<String, String> instanceMap) {
		String result;
		try {
			result = JsoupUtils.post(ConstantConfig.nacosInstanceAddress, instanceMap);
			return RestResult.ok(result);
		} catch (Exception e) {
			return RestResult.error(WengeStatusEnum.NOT_FIND_RESOURCE.getMsg());
		}
	}

	@Override
	public RestResult<?> deleteInstance(HashMap<String, String> instanceMap) {
		String result;
		try {
			result = JsoupUtils.delete(ConstantConfig.nacosInstanceAddress, instanceMap);
			return RestResult.ok(result);
		} catch (Exception e) {
			return RestResult.error(WengeStatusEnum.NOT_FIND_RESOURCE.getMsg());
		}
	}

	@Override
	public RestResult<?> getInstanceList(HashMap<String, String> instanceMap) {
		String result;
		try {
			result = JsoupUtils.get(ConstantConfig.nacosInstanceAddress+"/list", instanceMap);
			return RestResult.ok(result);
		} catch (Exception e) {
			return RestResult.error(WengeStatusEnum.NOT_FIND_RESOURCE.getMsg());
		}
	}

	@Override
	public RestResult<?> getInstance(HashMap<String, String> instanceMap) {
		String result;
		try {
			result = JsoupUtils.get(ConstantConfig.nacosInstanceAddress, instanceMap);
			return RestResult.ok(result);
		} catch (Exception e) {
			return RestResult.error(WengeStatusEnum.NOT_FIND_RESOURCE.getMsg());
		}
	}

	@Override
	public RestResult<?> putInstanceBeat(HashMap<String, String> instanceMap) {
		String result;
		try {
			result = JsoupUtils.put(ConstantConfig.nacosInstanceAddress + "/beat", instanceMap);
			return RestResult.ok(result);
		} catch (Exception e) {
			return RestResult.error(WengeStatusEnum.NOT_FIND_RESOURCE.getMsg());
		}

	}

	@Override
	public RestResult<?> putBatchMetadata(HashMap<String, String> instanceMap) {
		JSONObject result;
		try {
			result = JSONObject
					.parseObject(JsoupUtils.put(ConstantConfig.nacosInstanceAddress + "/metadata/batch", instanceMap));
			return RestResult.ok(result);
		} catch (Exception e) {
			return RestResult.error(WengeStatusEnum.NOT_FIND_RESOURCE.getMsg());
		}
	}
	@Override
	public RestResult<?> deleteBatchMetadata(HashMap<String, String> instanceMap) {
		JSONObject result;
		try {
			result = JSONObject.parseObject(
					JsoupUtils.delete(ConstantConfig.nacosInstanceAddress + "/metadata/batch", instanceMap));
			return RestResult.ok(result);
		} catch (Exception e) {
			return RestResult.error(WengeStatusEnum.NOT_FIND_RESOURCE.getMsg());
		}
	}
}
