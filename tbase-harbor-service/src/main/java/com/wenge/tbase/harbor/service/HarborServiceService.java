package com.wenge.tbase.harbor.service;

import com.wenge.tbase.harbor.result.RestResult;

import java.util.HashMap;

public interface HarborServiceService {

	RestResult<?> testHarbor(HashMap<String, String> instanceMap);


	RestResult<?> getImageByNamespaceAppId(HashMap<String, Object> serviceMap);

	RestResult<?> getImageByNamespaceAppName(HashMap<String, Object> serviceMap);
}
