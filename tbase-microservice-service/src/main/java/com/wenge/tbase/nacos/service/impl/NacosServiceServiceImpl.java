package com.wenge.tbase.nacos.service.impl;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.wenge.tbase.nacos.config.ConstantConfig;
import com.wenge.tbase.nacos.result.RestResult;
import com.wenge.tbase.nacos.result.WengeStatusEnum;
import com.wenge.tbase.nacos.service.NacosServiceService;
import com.wenge.tbase.nacos.utils.JsoupUtils;
@Service
public class NacosServiceServiceImpl implements NacosServiceService{

	@Override
	public RestResult<?> createService(HashMap<String, String> serviceMap) {
		  String result;
	      try {
				result =JsoupUtils.post(ConstantConfig.nacosServiceAddress, serviceMap);
				return RestResult.ok(result);
			} catch (Exception e) {
				return RestResult.error(WengeStatusEnum.NOT_FIND_RESOURCE.getMsg());
		}
	}

	@Override
	public RestResult<?> deleteService(HashMap<String, String> serviceMap) {
		  String result;
	      try {
				result =JsoupUtils.delete(ConstantConfig.nacosServiceAddress, serviceMap);
				return RestResult.ok(result);
			} catch (Exception e) {
				return RestResult.error(WengeStatusEnum.NOT_FIND_RESOURCE.getMsg());
		}
	}

	@Override
	public RestResult<?> updateService(HashMap<String, String> serviceMap) {
		 String result;
	      try {
				result =JsoupUtils.put(ConstantConfig.nacosServiceAddress, serviceMap);
				return RestResult.ok(result);
			} catch (Exception e) {
				return RestResult.error(WengeStatusEnum.NOT_FIND_RESOURCE.getMsg());
		}
	}

	@Override
	public RestResult<?> getService(HashMap<String, String> serviceMap) {
		String result;
	      try {
				result =JsoupUtils.get(ConstantConfig.nacosServiceAddress, serviceMap);
				return RestResult.ok(result);
			} catch (Exception e) {
				return RestResult.error(WengeStatusEnum.NOT_FIND_RESOURCE.getMsg());
		}
	}

	@Override
	public RestResult<?> getServiceList(HashMap<String, String> serviceMap) {
		String result;
	      try {
				result =JsoupUtils.get(ConstantConfig.nacosServiceAddress, serviceMap);
				return RestResult.ok(result);
			} catch (Exception e) {
				return RestResult.error(WengeStatusEnum.NOT_FIND_RESOURCE.getMsg());
		}
	}

}
