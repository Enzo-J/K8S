package com.wenge.tbase.nacos.service;

import java.util.HashMap;
import com.wenge.tbase.nacos.result.RestResult;
public interface NacosInstanceService {
	RestResult<?> registeredInstance(HashMap<String,String> instanceMap);
	RestResult<?> deleteInstance(HashMap<String, String> instanceMap);
	RestResult<?> getInstanceList(HashMap<String, String> instanceMap);
	RestResult<?> getInstance(HashMap<String, String> instanceMap);
	RestResult<?> putInstanceBeat(HashMap<String, String> instanceMap);
	RestResult<?> putBatchMetadata(HashMap<String, String> instanceMap);
	RestResult<?> deleteBatchMetadata(HashMap<String, String> instanceMap);

}
