package com.wenge.tbase.nacos.service.impl;
import java.util.HashMap;
import java.util.Map;
import com.alibaba.fastjson.JSONObject;
import com.wenge.tbase.commons.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.wenge.tbase.nacos.config.ConstantConfig;
import com.wenge.tbase.nacos.result.RestResult;
import com.wenge.tbase.nacos.service.NacosConfigService;
import com.wenge.tbase.nacos.utils.JsoupUtils;

@Service
@Slf4j
public class NacosConfigServiceImpl<V> implements NacosConfigService {
	@Override
	public RestResult<?>  obtainConfig(HashMap<String, String> configMap) {
	  JSONObject result;
	try {
		result = JsoupUtils.getJson(ConstantConfig.nacosConfigAddress, configMap);
		return RestResult.ok(result);
	} catch (Exception e) {
		return RestResult.error(ResultCode.NOT_FIND_RESOURCE.getMsg());
	}
	}

	@Override
	public RestResult<?> listenConfigs(String listeningConfigs) {
		  Map<String, String> params=new HashMap<String, String>();
	      params.put("Listening-Configs", listeningConfigs);
		  String result;
		try {
			result = JsoupUtils.post(ConstantConfig.nacosConfigAddress+"/listener", params, "Long-Pulling-Timeout", "30000");
			return RestResult.ok(result);
		} catch (Exception e) {
			return RestResult.error(ResultCode.NOT_FIND_RESOURCE.getMsg());
		}
	}

	@Override
	public RestResult<?> deleteConfigs(HashMap<String, String> configMap) {
		String result;
		try {
			result = JsoupUtils.delete(ConstantConfig.nacosConfigAddress, configMap);
			return RestResult.ok(result);
		} catch (Exception e) {
			return RestResult.error(ResultCode.NOT_FIND_RESOURCE.getMsg());
		}
	}

	@Override
	public RestResult<?> releaseConfigs(HashMap<String, String> configMap) {
		String result;
		try {
			result = JsoupUtils.post(ConstantConfig.nacosConfigAddress, configMap);
			return RestResult.ok(result);
		} catch (Exception e) {
			return RestResult.error(ResultCode.NOT_FIND_RESOURCE.getMsg());
		}
	}

	@Override
	public RestResult<?> obtainConfigLists(HashMap<String, String> configMap) {
		JSONObject result;
		try {
			result = JsoupUtils.getJson(ConstantConfig.nacosConfigAddress, configMap);
			return RestResult.ok(result.getJSONArray("pageItems"));
		} catch (Exception e) {
			return RestResult.error(ResultCode.NOT_FIND_RESOURCE.getMsg());
		}
	}

}
