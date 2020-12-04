package com.wenge.tbase.nacos.service.impl;

import java.util.HashMap;

import com.wenge.tbase.commons.result.ResultCode;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONObject;
import com.wenge.tbase.nacos.config.ConstantConfig;
import com.wenge.tbase.nacos.result.RestResult;
import com.wenge.tbase.nacos.result.WengeStatusEnum;
import com.wenge.tbase.nacos.service.NacosCountService;
import com.wenge.tbase.nacos.utils.JsoupUtils;
@Service
public class NacosCountServiceImpl implements NacosCountService {
	@Override
	public RestResult<?> getCounts() {
		//包含4个参数：项目总数，服务总数，实例总数，实例下线数量
		JSONObject countObject = new JSONObject();
		//项目总数固定为8
		try {
			String projectNum=ConstantConfig.projectNum;
			int serverCount=0;
			int InstanceTotalSize=0;
			int InstanceOfflineSize=0;
			countObject.put("projectNum", projectNum);
			String pageNo=ConstantConfig.pageNo;
			String pageSize=ConstantConfig.pageSize;
			//服务总数
			HashMap<String, String> serviceMap = new HashMap<String, String>();
			serviceMap.put("pageNo", pageNo);
			serviceMap.put("pageSize", pageSize);
			JSONObject serviceList =JSONObject.parseObject(JsoupUtils.get(ConstantConfig.nacosServiceAddress+"/list", serviceMap));
			serverCount = serviceList.getInteger("count");
			for (Object serverName : serviceList.getJSONArray("doms")) {
				HashMap<String, String> serverNameMap = new HashMap<String, String>();
				serverNameMap.put("serviceName", String.valueOf(serverName));
				JSONObject serverNameJson = JSONObject.parseObject(JsoupUtils.get(ConstantConfig.nacosInstanceAddress+"/list", serverNameMap));
				serverNameMap.put("enabled", String.valueOf(false));
				JSONObject serverHealthyJson = JSONObject.parseObject(JsoupUtils.get(ConstantConfig.nacosInstanceAddress+"/list", serverNameMap));

				InstanceTotalSize += serverNameJson.getJSONArray("hosts").size();
				InstanceOfflineSize += serverHealthyJson.getJSONArray("hosts").size();
			}
			if(serverCount>=Integer.parseInt(pageSize)) {
				serviceMap.put("pageNo", pageNo);
				serviceMap.put("pageSize", String.valueOf(serverCount-Integer.getInteger(pageSize)));
				JSONObject service2List =JSONObject.parseObject(JsoupUtils.get(ConstantConfig.nacosServiceAddress, serviceMap));
				serverCount = service2List.getInteger("count");
				for (Object serverName : service2List.getJSONArray("doms")) {
					HashMap<String, String> serverNameMap = new HashMap<String, String>();
					serverNameMap.put("serviceName", String.valueOf(serverName));
					JSONObject serverNameJson = JSONObject.parseObject(JsoupUtils.get(ConstantConfig.nacosInstanceAddress+"/list", serverNameMap));
					InstanceTotalSize += serverNameJson.getJSONArray("hosts").size();
					serverNameMap.put("enabled", String.valueOf(false));
					JSONObject serverHealthyJson = JSONObject.parseObject(JsoupUtils.get(ConstantConfig.nacosInstanceAddress+"/list", serverNameMap));
					InstanceOfflineSize += serverHealthyJson.getJSONArray("hosts").size();
					InstanceTotalSize += serverNameJson.getJSONArray("hosts").size();
				}
				serverCount+= service2List.getInteger("count");
			}
			countObject.put("projectNum", projectNum);
			countObject.put("serverCount",serverCount);
			countObject.put("instanceTotalCount",InstanceTotalSize);
			countObject.put("instanceOfflineCount",InstanceOfflineSize);
		return RestResult.ok(countObject);
	}	
	catch (Exception e) {
		return RestResult.error(ResultCode.NOT_FIND_RESOURCE.getMsg());
	}
	}
}
