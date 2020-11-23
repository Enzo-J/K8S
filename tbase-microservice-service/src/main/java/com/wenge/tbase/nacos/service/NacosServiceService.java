package com.wenge.tbase.nacos.service;

import java.util.HashMap;

import com.wenge.tbase.nacos.result.RestResult;

public interface NacosServiceService {

	RestResult<?> createService(HashMap<String, String> instanceMap);

	RestResult<?> deleteService(HashMap<String, String> instanceMap);

	RestResult<?> updateService(HashMap<String, String> instanceMap);

	RestResult<?> getService(HashMap<String, String> serviceMap);

	RestResult<?> getServiceList(HashMap<String, String> serviceMap);

}
