package com.wenge.tbase.nacos.service;

import com.wenge.tbase.nacos.result.RestResult;

import java.util.HashMap;

public interface NacosConfigService {
	RestResult<?> obtainConfig(HashMap<String, String> configMap);
	RestResult<?> listenConfigs(String listeningConfigs);
	RestResult<?> deleteConfigs(HashMap<String, String> configMap);
	RestResult<?> releaseConfigs(HashMap<String, String> configMap);
	RestResult<?> obtainConfigLists(HashMap<String, String> configMap);
}
