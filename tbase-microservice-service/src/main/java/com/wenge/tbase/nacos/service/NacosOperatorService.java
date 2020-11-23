package com.wenge.tbase.nacos.service;

import java.util.HashMap;

import com.wenge.tbase.nacos.result.RestResult;

public interface NacosOperatorService {

	RestResult<?> getSwitches();

	RestResult<?> putSwitches(HashMap<String, String> serviceMap);

	RestResult<?> getMetrics();

	RestResult<?> getServices(HashMap<String, String> serviceMap);

}
