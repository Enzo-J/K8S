package com.wenge.tbase.nacos.service.impl;

import java.util.HashMap;

import com.alibaba.fastjson.JSONObject;
import com.wenge.tbase.commons.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.wenge.tbase.nacos.config.ConstantConfig;
import com.wenge.tbase.nacos.result.RestResult;
import com.wenge.tbase.nacos.result.WengeStatusEnum;
import com.wenge.tbase.nacos.service.NacosServiceService;
import com.wenge.tbase.nacos.utils.JsoupUtils;
@Service
@Slf4j
public class NacosServiceServiceImpl implements NacosServiceService{

	@Override
	public RestResult<?> createService(HashMap<String, String> serviceMap) {
		  String result;
		log.info("serviceMap"+JSONObject.toJSONString(serviceMap));

		try {
				result =JsoupUtils.post(ConstantConfig.nacosServiceAddress, serviceMap);
				return RestResult.ok(result);
			} catch (Exception e) {
				return RestResult.error(ResultCode.NOT_FIND_RESOURCE.getMsg());
		}
	}

	@Override
	public RestResult<?> deleteService(HashMap<String, String> serviceMap) {
		  String result;
		log.info("serviceMap"+JSONObject.toJSONString(serviceMap));

		try {
				result =JsoupUtils.delete(ConstantConfig.nacosServiceAddress, serviceMap);
				return RestResult.ok(result);
			} catch (Exception e) {
				return RestResult.error(ResultCode.NOT_FIND_RESOURCE.getMsg());
		}
	}

	@Override
	public RestResult<?> updateService(HashMap<String, String> serviceMap) {
		 String result;
		 log.info("serviceMap"+JSONObject.toJSONString(serviceMap));
	      try {
				result =JsoupUtils.put(ConstantConfig.nacosServiceAddress, serviceMap);
				return RestResult.ok(result);
			} catch (Exception e) {
				return RestResult.error(ResultCode.NOT_FIND_RESOURCE.getMsg());
		}
	}

	@Override
	public RestResult<?> getService(HashMap<String, String> serviceMap) {
		JSONObject result;
		log.info("serviceMap"+JSONObject.toJSONString(serviceMap));
		try {
				result =JsoupUtils.getJson(ConstantConfig.nacosServiceAddress, serviceMap);
				return RestResult.ok(result);
			} catch (Exception e) {
				return RestResult.error(ResultCode.NOT_FIND_RESOURCE.getMsg());
		}
	}

	@Override
	public RestResult<?> getServiceList(HashMap<String, String> serviceMap) {
		JSONObject result;
	      try {
				result =JsoupUtils.getJson(ConstantConfig.nacosServiceListAddress, serviceMap);
				return RestResult.ok(result);
			} catch (Exception e) {
				return RestResult.error(ResultCode.NOT_FIND_RESOURCE.getMsg());
		}
	}

}
