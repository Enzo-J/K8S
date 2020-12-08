package com.wenge.tbase.nacos.service.impl;

import java.util.HashMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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
		JSONObject instancesObject = new JSONObject();
		JSONObject countObject = new JSONObject();
		JSONArray offLineInstances = new JSONArray();
		 JSONObject offlineObject = new JSONObject();

		//项目总数固定为8
		try {
			String projectNum=ConstantConfig.projectNum;
			int serverCount=0;
			int instanceTotalSize=0;
			int instanceOfflineSize=0;
			countObject.put("projectNum", projectNum);
			String pageNo=ConstantConfig.pageNo;
			String pageSize=ConstantConfig.pageSize;
			//服务总数
			HashMap<String, String> serviceMap = new HashMap<String, String>();
			serviceMap.put("pageNo", pageNo);
			serviceMap.put("pageSize", pageSize);
			serviceMap.put("hasIpCount", "true");
			JSONObject serviceTrueList =JSONObject.parseObject(JsoupUtils.get(ConstantConfig.nacosServiceListAddress, serviceMap));
			serverCount = serviceTrueList.getInteger("count");
			for (Object serverName : serviceTrueList.getJSONArray("serviceList")) {
				JSONObject serverNameObject = JSONObject.parseObject(serverName.toString());
				String name = String.valueOf(serverNameObject.getString("name"));
				serviceMap.put("serviceName", name);
				serviceMap.put("clusterName","DEFAULT");
				JSONObject serverHealthyJson = JSONObject.parseObject(JsoupUtils.get(ConstantConfig.nacosInstanceListAddress, serviceMap));
				JSONArray lists = serverHealthyJson.getJSONArray("list");
				instanceTotalSize += serverHealthyJson.getInteger("count");
				if (lists.size() > 0) {
				for (Object list: lists) {
					JSONObject instanceObject = JSONObject.parseObject(list.toString());
					if (instanceObject.getBoolean("enabled") == false) {
						instanceOfflineSize++;
						offlineObject.put("serviceName", name);
						offlineObject.put("instanceIp", instanceObject.getString("ip"));
						offlineObject.put("instancePort", instanceObject.getInteger("port"));
						offlineObject.put("weight", instanceObject.getInteger("weight"));
						offlineObject.put("healthy", instanceObject.getBoolean("healthy"));
						offLineInstances.add(offlineObject);
					}
				   }
				}
			}
			if(serverCount>=Integer.parseInt(pageSize)) {
				serviceMap.put("pageNo", pageNo+1);
				serviceMap.put("pageSize", String.valueOf(serverCount-Integer.getInteger(pageSize)));
				serviceMap.put("hasIpCount", "true");
				JSONObject serviceTrueList2 =JSONObject.parseObject(JsoupUtils.get(ConstantConfig.nacosServiceListAddress, serviceMap));
				for (Object serverName : serviceTrueList2.getJSONArray("serviceList")) {
					JSONObject serverNameObject = JSONObject.parseObject(serverName.toString());
					String name = serverNameObject.getString("name");
					serviceMap.put("serviceName", name);
					serviceMap.put("clusterName", "DEFAULT");
					JSONObject serverHealthyJson = JSONObject.parseObject(JsoupUtils.get(ConstantConfig.nacosInstanceListAddress, serviceMap));
					JSONArray lists = serverHealthyJson.getJSONArray("list");
					instanceTotalSize += serverHealthyJson.getInteger("count");
					if (lists.size() > 0) {
						for (Object list : lists) {
							JSONObject instanceObject = JSONObject.parseObject(list.toString());
							if (instanceObject.getBoolean("enabled") == false) {
								instanceOfflineSize++;
								offlineObject.put("serviceName",name);
								offlineObject.put("instanceIp", instanceObject.getString("ip"));
								offlineObject.put("instancePort", instanceObject.getInteger("port"));
								offlineObject.put("weight", instanceObject.getInteger("weight"));
								offlineObject.put("healthy", instanceObject.getBoolean("healthy"));
								offLineInstances.add(offlineObject);
							}
						}
					}
				}
			}
			instancesObject.put("offLineInstancesCount",instanceOfflineSize);
			instancesObject.put("offLineInstancesList",offLineInstances);
			countObject.put("projectNum", projectNum);
			countObject.put("serverCount",serverCount);
			countObject.put("instanceTotalCount",instanceTotalSize);
			countObject.put("offLineInstances",instancesObject);
		return RestResult.ok(countObject);
	}	
	catch (Exception e) {
		return RestResult.error(ResultCode.NOT_FIND_RESOURCE.getMsg());
	}
	}
}
