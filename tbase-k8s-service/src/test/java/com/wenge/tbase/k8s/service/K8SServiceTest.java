package com.wenge.tbase.k8s.service;


import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class K8SServiceTest {
    @Resource
    private K8SService k8SService;

    @org.junit.jupiter.api.Test
    public void getAllNameSpace() {
        System.out.println(k8SService.getAllNameSpace());
    }

    @org.junit.jupiter.api.Test
    public void getMachineInfo(){
        k8SService.getNodeInfo();
    }

    @org.junit.jupiter.api.Test
    public void getConfigMap(){
        k8SService.findConfigMapDetail("jenkins","components");
    }
}