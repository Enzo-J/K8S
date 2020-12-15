package com.wenge.tbase.nacos.service;

import com.wenge.tbase.nacos.result.RestResult;

/**
 * Author: sunlyhm
 * Date: 2020/12/8 21:24
 * FileName: MicroserviceGovernanceService
 * Description: 服务治理模块
 */
public interface MicroserviceGovernanceService {
    RestResult<?> getRegistryList();
}
