package com.wenge.tbase.nacos.service;

import com.wenge.tbase.nacos.result.RestResult;

/**
 * Author: sunlyhm
 * Date: 2020/12/8 16:41
 * FileName: RegistryInfoService
 * Description: 注册中心信息的展示服务
 */
public interface RegistryInfoService {

    RestResult<?> getRegistry();
}
