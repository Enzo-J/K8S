package com.wenge.tbase.nacos.service;

import java.util.HashMap;

import com.wenge.tbase.nacos.result.RestResult;

public interface NacosNsService {

	RestResult<?> getLeader();

	RestResult<?> updateHealthInstance(HashMap<String, String> instanceMap);

}
