package com.wenge.tbase.gateway.schedule;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.alibaba.fastjson.JSON;
import com.wenge.tbase.gateway.entity.ServiceIntercept;
import com.wenge.tbase.gateway.fegin.ServiceInterceptService;

import lombok.extern.slf4j.Slf4j;


/**
 * Ӧ�÷���URL��ʱ����
 */
@Slf4j
@Configuration
@EnableScheduling
public class ServiceInterceptScheduleTask {
	
	private static final String SSO_INTERCEPT_REDIS_PREFIX = "intercept:";	
	@Autowired
    private RedisTemplate<String, String> redisTemplate;//lettuce
	
    @Autowired
    ServiceInterceptService serviceInterceptService;
    
    @Scheduled(cron = "0 */1 * * * ?")
    private void sendData() {
        log.info("��ʼ����ˢ��Ӧ�÷���URL����");
        //�����з��еĵ����ݲ�������reid
        List<ServiceIntercept> list  =  serviceInterceptService.getAllReleaseUrl();
        if (null != list && list.size() > 0) {
            // ����Ӧ��ID���з���
            Map<String, List<ServiceIntercept>> serviceInterceptMap = list.stream().collect(Collectors.groupingBy(ServiceIntercept::getAppId));
            for (Map.Entry<String, List<ServiceIntercept>> entry : serviceInterceptMap.entrySet()) {
                String appId = entry.getKey();
                List<ServiceIntercept> serviceInterceptList = entry.getValue();
                if (null != serviceInterceptList && serviceInterceptList.size() > 0) {
                    // ���ݷ��й�����з���
                    Map<Integer, List<ServiceIntercept>> matchingRulesMap = serviceInterceptList.stream().collect(Collectors.groupingBy(ServiceIntercept::getMatchingRules));
                    for (int i = 0; i < 2; i++) {
                        List<ServiceIntercept> serviceIntercepts = matchingRulesMap.get(i);
                        if (null == serviceIntercepts || serviceIntercepts.size() == 0) {
                        	redisTemplate.delete(SSO_INTERCEPT_REDIS_PREFIX + appId + ":" + i);
                            continue;
                        }
                        List<String> matchingRulesList = serviceIntercepts.stream().map(ServiceIntercept::getUrl).collect(Collectors.toList());
                        redisTemplate.opsForValue().set(SSO_INTERCEPT_REDIS_PREFIX + appId + ":" + i, JSON.toJSONString(matchingRulesList),1800l);
                    }
                }
            }
        }
        log.info("��������ˢ��Ӧ�÷���URL����");
    }
}
