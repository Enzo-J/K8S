package com.wenge.tbase.nacos.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wenge.tbase.nacos.config.ConstantConfig;
import com.wenge.tbase.nacos.result.RestResult;
import com.wenge.tbase.nacos.service.MicroserviceGovernanceService;
import com.wenge.tbase.nacos.utils.JsoupUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * Author: sunlyhm
 * Date: 2020/12/8 21:25
 * FileName: MicroserviceGovernanceServiceImpl
 * Description: 服务治理模块实现
 */
@Service
public class MicroserviceGovernanceServiceImpl implements MicroserviceGovernanceService {
    @Override
    public RestResult<?> getRegistryList() {
        //1、先进行sentinal的登录操作   http://172.16.0.8:32076/auth/login?password=sentinel&username=sentinel
        HashMap<String, String> sentinalMap = new HashMap<>();
        sentinalMap.put("username","sentinel");
        sentinalMap.put("password","sentinel");
        String put = JsoupUtils.post(ConstantConfig.sentinalServiceAddress, sentinalMap);
        JSONObject jsonObject = JSONObject.parseObject(put);
        if(jsonObject.getInteger("code")==0){
            return RestResult.ok(ConstantConfig.sentinalDashboardAddress);
        }else
        {
            return RestResult.error(put);
        }
    }
}
