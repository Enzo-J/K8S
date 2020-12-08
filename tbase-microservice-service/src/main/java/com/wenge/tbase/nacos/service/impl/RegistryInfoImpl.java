package com.wenge.tbase.nacos.service.impl;

import com.wenge.tbase.nacos.result.RestResult;
import com.wenge.tbase.nacos.service.RegistryInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Author: sunlyhm
 * Date: 2020/12/8 16:42
 * FileName: RegistryInfoImpl
 * Description: 注册中心信息展示服务的实现类
 */
@Service
@Slf4j
public class RegistryInfoImpl implements RegistryInfoService {

    @Override
    public RestResult<?> getRegistry() {
        //查询数据库

        return null;
    }
}
