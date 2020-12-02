package com.wenge.tbase.nacos.service.impl;

import com.wenge.tbase.nacos.result.RestResult;
import com.wenge.tbase.nacos.result.WengeStatusEnum;
import com.wenge.tbase.nacos.config.ConstantConfig;
import com.wenge.tbase.nacos.service.NacosServiceClusterService;
import com.wenge.tbase.nacos.utils.JsoupUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * Author: sunlyhm
 * Date: 2020/11/29 15:18
 * FileName: NacosServiceClusterServiceImpl
 * Description: nacos服务集群配置管理服务实现
 */
@Service
public class NacosServiceClusterServiceImpl implements NacosServiceClusterService {
    @Override
    public RestResult<?> putClusterService(HashMap<String, String> clusterMap) {
        String result;
        try {
            result = JsoupUtils.put(ConstantConfig.nacosClusterAddress, clusterMap);
            return RestResult.ok(result);
        } catch (Exception e) {
            return RestResult.error(WengeStatusEnum.NOT_FIND_RESOURCE.getMsg());
        }
    }
}
