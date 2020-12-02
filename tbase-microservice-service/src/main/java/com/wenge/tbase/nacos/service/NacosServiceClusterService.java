package com.wenge.tbase.nacos.service;

import com.wenge.tbase.nacos.result.RestResult;

import java.util.HashMap;

/**
 * Author: sunlyhm
 * Date: 2020/11/29 15:17
 * FileName: NacosServiceClusterService
 * Description: nacos服务集群配置管理服务接口
 */
public interface NacosServiceClusterService {
    RestResult<?> putClusterService(HashMap<String, String> clusterMap);
}
