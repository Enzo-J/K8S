package com.wenge.tbase.k8s.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class K8SServiceTest {
    @Resource
    private K8SService k8SService;
    @Test
    public void getAllNameSpace() {
        System.out.println(k8SService.getAllNameSpace());
    }

    @Test
    public void getMachineInfo(){
        k8SService.getNodeInfo();
    }

    @Test
    public void getConfigMap(){
        k8SService.findConfigMapDetail("jenkins","components");
    }
}