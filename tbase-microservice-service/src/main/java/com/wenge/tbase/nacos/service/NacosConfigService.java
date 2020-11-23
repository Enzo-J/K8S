package com.wenge.tbase.nacos.service;

import com.wenge.tbase.nacos.result.RestResult;

public interface NacosConfigService {
	RestResult<?> getConfigs(String tenant, String dataId, String group);
	RestResult<?> listenConfigs(String listeningConfigs);
	RestResult<?> releaseConfigs(String tenant, String dataId, String content, String group,String type);
	RestResult<?> deleteConfigs(String tenant, String dataId, String group);
}
