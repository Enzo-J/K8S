package com.wenge.tbase.k8s.service;

import com.wenge.tbase.k8s.bean.vo.K8SConfigMap;
import com.wenge.tbase.k8s.bean.vo.K8SDeployment;
import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.api.model.extensions.Deployment;

import java.util.List;
import java.util.Map;

public interface K8SService {


    /**
     * 获取全部命名空间
     */
    List getAllNameSpace();

    /**
     * 获取服务器节点信息
     */
    Map getNodeInfo();


    /**
     * 配置configMap
     */
    ConfigMap createConfigMap(K8SConfigMap k8SConfigMap);


    /**
     * 查询命名空间下的configMap
     */
    List<String> findConfigMapByNamespace(String namespace);

    /**
     * 查询对应configMap下的详细信息
     */
    ConfigMap findConfigMapDetail(String name,String namespace);


    /**
     * 修改对应configMap下的详细信息
     */
    ConfigMap editConfigMapDetail(String name,String namespace,Map data);


    /**
     * 创建secret
     */


    /**
     * 查询secret
     */




    /**
     * 服务部署
     */
    Deployment createDeployment(K8SDeployment k8SDeployment);


    /**
     * 查看K8S存储
     */
    Map findStorageInfo();

    /**
     * 查看K8S存储的yaml配置文件
     * @param name 存储名称
     * @return 存储配置文件
     */
    String findStorageDescribeInfo(String name);

    /**
     * 查看命名空间下的详细信息
     * @param namespace
     */
    void findNamespaceDetailInfo(String namespace);
}
