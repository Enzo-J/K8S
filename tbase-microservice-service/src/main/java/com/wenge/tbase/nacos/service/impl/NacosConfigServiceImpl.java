package com.wenge.tbase.nacos.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.wenge.tbase.nacos.config.ConstantConfig;
import com.wenge.tbase.nacos.result.RestResult;
import com.wenge.tbase.nacos.result.WengeStatusEnum;
import com.wenge.tbase.nacos.service.NacosConfigService;
import com.wenge.tbase.nacos.utils.JsoupUtils;
@Service
public class NacosConfigServiceImpl<V> implements NacosConfigService {
	@Override
	public RestResult<?> getConfigs(String tenant, String dataId, String group) {
      Map<String, String> params=new HashMap<String, String>();
      params.put("dataId", dataId);
      params.put("group", group);
      if(tenant!=null) {
    	  params.put("tenant", tenant);
      }
	  String result;
	try {
		result = JsoupUtils.get(ConstantConfig.nacosConfigAddress, params);
		return RestResult.ok(result);
	} catch (Exception e) {
		return RestResult.error(WengeStatusEnum.NOT_FIND_RESOURCE.getMsg());
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
			return RestResult.error(WengeStatusEnum.NOT_FIND_RESOURCE.getMsg());
		}
	}
	@Override
	public RestResult<?> releaseConfigs(String tenant, String dataId, String content, String group, String type) {
		 Map<String, String> params=new HashMap<String, String>();
	      params.put("dataId", dataId);
	      params.put("content", content);
	      if(tenant!=null) {
	    	  params.put("tenant", tenant);
	      }
	      if(type!=null) {
	    	  params.put("type", tenant);
	      }
	      params.put("group", group);
		  String result;
		try {
			result = JsoupUtils.post(ConstantConfig.nacosConfigAddress, params);
			return RestResult.ok(result);
		} catch (Exception e) {
			return RestResult.error(WengeStatusEnum.NOT_FIND_RESOURCE.getMsg());
		}
	}

	@Override
	public RestResult<?> deleteConfigs(String tenant, String dataId, String group) {
		 Map<String, String> params=new HashMap<String, String>();
		 if(tenant!=null) {
	    	  params.put("tenant", tenant);
	      }
	      params.put("dataId", dataId);
	      params.put("group", group);
		  String result;
		try {
			result = JsoupUtils.delete(ConstantConfig.nacosConfigAddress, params);
			return RestResult.ok(result);
		} catch (Exception e) {
			return RestResult.error(WengeStatusEnum.NOT_FIND_RESOURCE.getMsg());
		}
	}

}
