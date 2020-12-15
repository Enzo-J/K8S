package com.wenge.tbase.k8s.service;


import com.wenge.tbase.k8s.bean.vo.K8SDeployment;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    @org.junit.jupiter.api.Test
    public void findStorageInfo(){
        k8SService.findStorageInfo();
    }

    @org.junit.jupiter.api.Test
    public void findStorageDescribeInfo(){
        System.out.println(k8SService.findStorageDescribeInfo("nfs-storage"));
    }


    @org.junit.jupiter.api.Test
    public void tranformTimeStamp(){
        String date = "2020-07-23T16:00:00.000Z";
        date = date.replace("Z", " UTC");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
        try {
            Date d = format.parse(date);
            System.out.println(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @org.junit.jupiter.api.Test
    public void  findEventInfoByNamespace(){
        k8SService.findEventInfoByNamespace("default");
    }


    @org.junit.jupiter.api.Test
    public void createDeployment(){
        K8SDeployment k8SDeployment=new K8SDeployment();
        k8SDeployment.setNamespace("dp");
        k8SDeployment.setName("k8s-service");
        k8SDeployment.setImageUrl("172.16.0.11/app-manage-platform/tbase-k8s-service:latest");
        k8SDeployment.setPort(10020);
        k8SService.createDeployment(k8SDeployment);
    }


    @org.junit.jupiter.api.Test
    public void createService(){
        K8SDeployment k8SDeployment=new K8SDeployment();
        k8SDeployment.setNamespace("dp");
        k8SDeployment.setName("k8s-service");
        k8SDeployment.setPort(10020);
        k8SService.createService(k8SDeployment);
    }


}