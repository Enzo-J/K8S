package com.wenge.tbase.harbor.service.impl;

import com.wenge.tbase.harbor.config.ConstantConfig;
import com.wenge.tbase.harbor.result.RestResult;
import com.wenge.tbase.harbor.result.WengeStatusEnum;
import com.wenge.tbase.harbor.service.HarborServiceService;
import com.wenge.tbase.harbor.utilsTest.JsoupUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class HarborServiceServiceImpl implements HarborServiceService {

	@Override
	public RestResult<?> testHarbor(HashMap<String, String> serviceMap) {
		String[] strings = new String[10];
		  String result;
	      try {
				result = JsoupUtils.get(ConstantConfig.harborServiceAddress+"/users/current", null);
				return RestResult.ok(result);
			} catch (Exception e) {
				return RestResult.error(WengeStatusEnum.NOT_FIND_RESOURCE.getMsg());
		}
	}

	@Override
	public RestResult<?> getImageByNamespaceAppId(HashMap<String, Object> serviceMap) {
		String result;
		try {
			//调用Harbor 官方API
//			result = JsoupUtils.post("http://172.16.0.6:30000/harbor/v1/ns/service", serviceMap);


			return RestResult.ok();
		} catch (Exception e) {
			return RestResult.error(WengeStatusEnum.NOT_FIND_RESOURCE.getMsg());
		}
	}

	@Override
	public RestResult<?> getImageByNamespaceAppName(HashMap<String, Object> serviceMap) {
		String result;
		try {

			//调用Harbor 官方API
			//result = JsoupUtils.post("http://172.16.0.6:30000/harbor/v1/ns/service", serviceMap);

			return RestResult.ok();
		} catch (Exception e) {
			return RestResult.error(WengeStatusEnum.NOT_FIND_RESOURCE.getMsg());
		}
	}

}
