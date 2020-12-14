package com.wenge.tbase.k8s.service;


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
    public void  findNamespaceDetailInfo(){
        k8SService.findNamespaceDetailInfo("components");
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
}