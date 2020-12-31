package com.wenge.tbase.k8s.service;


import com.wenge.tbase.k8s.bean.vo.K8SConfigMap;
import com.wenge.tbase.k8s.bean.vo.K8SDeployment;
import com.wenge.tbase.k8s.bean.vo.K8SPersistentVolumeClaim;
import com.wenge.tbase.k8s.bean.vo.K8SSecret;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest
public class K8SServiceTest {
    @Resource
    private K8SService k8SService;

    @org.junit.jupiter.api.Test
    public void getAllNameSpace() {
        System.out.println(k8SService.getAllNameSpace());
    }

    @org.junit.jupiter.api.Test
    public void getMachineInfo() {
        k8SService.getNodeInfo();
    }

    @org.junit.jupiter.api.Test
    public void getConfigMap() {
        k8SService.findConfigMapDetail("test", "test");
    }

    @org.junit.jupiter.api.Test
    public void findStorageInfo() {
        k8SService.findStorageInfo();
    }

    @org.junit.jupiter.api.Test
    public void findStorageDescribeInfo() {
        System.out.println(k8SService.findStorageDescribeInfo("nfs-storage"));
    }


    @org.junit.jupiter.api.Test
    public void tranformTimeStamp() {
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
    public void findEventInfoByNamespace() {
        k8SService.findEventInfoByNamespace("default");
    }


    @org.junit.jupiter.api.Test
    public void createDeployment() {
        K8SDeployment k8SDeployment = new K8SDeployment();
        k8SDeployment.setNamespace("test");
        k8SDeployment.setName("test-deployment");
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("select-name","test-deployment");
        k8SDeployment.setLabels(hashMap);
        k8SDeployment.setReplicas(1);
        k8SDeployment.setServiceName("test4");
        k8SDeployment.setImageUrl("test4");
        k8SService.createDeployment(k8SDeployment);
    }

    @org.junit.jupiter.api.Test
    public void delDeployment() {
        k8SService.delDeployment("test", "cloud-test3");
    }

    @org.junit.jupiter.api.Test
    public void createService() {
        K8SDeployment k8SDeployment = new K8SDeployment();
        k8SDeployment.setNamespace("dp");
        k8SDeployment.setName("k8s-service");
        k8SDeployment.setPort(10020);
        k8SService.createService(k8SDeployment);
    }

    @org.junit.jupiter.api.Test
    public void createNamespace() {
        k8SService.createNamespace("test");
    }

    @org.junit.jupiter.api.Test
    public void deleteNamespace() {
        k8SService.deleteNamespace("test2");
    }

    @org.junit.jupiter.api.Test
    public void editConfigMapDetail() {
        K8SConfigMap k8SConfigMapInfo = new K8SConfigMap();
        k8SConfigMapInfo.setName("test");
        k8SConfigMapInfo.setNamespace("test");
        Map<String, String> map = new HashMap<>();
        map.put("ceshi", "ceshI");
        k8SConfigMapInfo.setData(map);
        k8SService.editConfigMapDetail(k8SConfigMapInfo);
    }

    @org.junit.jupiter.api.Test
    public void createConfigMap() {
        K8SConfigMap k8SConfigMapInfo = new K8SConfigMap();
        k8SConfigMapInfo.setName("test2");
        k8SConfigMapInfo.setNamespace("test");
        Map<String, String> map = new HashMap<>();
        map.put("ceshi", "ceshi");
        k8SConfigMapInfo.setData(map);
        k8SService.createConfigMap(k8SConfigMapInfo);
    }

    @org.junit.jupiter.api.Test
    public void delConfigMap() {
        k8SService.delConfigMap("test2", "test");
    }

    @org.junit.jupiter.api.Test
    public void delConfigMaps() {
        List<String> list = new ArrayList<>();
        list.add("test2");
        list.add("test3");
        k8SService.delConfigMaps(list, "test");
    }

    @org.junit.jupiter.api.Test
    public void delConfigMapsAll() {
        k8SService.delConfigMapsAll("test");
    }

    @org.junit.jupiter.api.Test
    public void createSecret() {
        K8SSecret k8SSecret = new K8SSecret();
        k8SSecret.setNamespace("test");
        k8SSecret.setSecretName("test8");
        k8SSecret.setType("kubernetes.io/dockerconfigjson");
        k8SSecret.setDockerServer("https://172.16.0.11");
        k8SSecret.setDockerUsername("admin");
        k8SSecret.setDockerServer("Szwg%2020");
        k8SService.createSecret(k8SSecret);
    }

    @org.junit.jupiter.api.Test
    public void editSecret() {
        K8SSecret k8SSecret = new K8SSecret();
        k8SSecret.setNamespace("test");
        k8SSecret.setSecretName("test2");
        k8SSecret.setDockerServer("https://172.16.0.11");
        k8SSecret.setDockerUsername("wg");
        k8SSecret.setDockerServer("Szwg%2020");
        k8SService.editSecret(k8SSecret);
    }

    @org.junit.jupiter.api.Test
    public void delSecret() {
        k8SService.delSecret("test", "test");
    }

    @org.junit.jupiter.api.Test
    public void delSecrets() {
        List<String> list = new ArrayList<>();
        list.add("test2");
        list.add("test3");
        k8SService.delSecrets("test", list);
    }

    @org.junit.jupiter.api.Test
    public void delSecretsAll() {
        k8SService.delSecretsAll("test");
    }

    @org.junit.jupiter.api.Test
    public void createPVC() {
        K8SPersistentVolumeClaim persistentVolumeClaim = new K8SPersistentVolumeClaim();
        persistentVolumeClaim.setName("test2");
        persistentVolumeClaim.setNamespace("test");
        List<String> modes = new ArrayList<>();
        modes.add("ReadOnlyMany");
        persistentVolumeClaim.setAccessmode(modes);
        persistentVolumeClaim.setStorageType("chartmuseum");
        persistentVolumeClaim.setTotal("5Mi");
        k8SService.createPVC(persistentVolumeClaim);
    }

    @org.junit.jupiter.api.Test
    public void delPVC() {
        k8SService.delPVC("test", "test");
    }

    @org.junit.jupiter.api.Test
    public void delPVCs() {
        List<String> list = new ArrayList<>();
        list.add("test2");
        list.add("test3");
        k8SService.delPVCs("test", list);
    }

    @org.junit.jupiter.api.Test
    public void delPVCsAll() {
        k8SService.delPVCsAll("test");
    }
}