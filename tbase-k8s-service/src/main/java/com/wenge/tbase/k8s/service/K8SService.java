package com.wenge.tbase.k8s.service;

import com.wenge.tbase.k8s.bean.vo.K8SConfigMap;
import com.wenge.tbase.k8s.bean.vo.K8SDeployment;
import com.wenge.tbase.k8s.bean.vo.K8SHarborSecret;
import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.api.model.Namespace;
import io.fabric8.kubernetes.api.model.Secret;
import io.fabric8.kubernetes.api.model.apps.Deployment;

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
     * 创建docker仓库密码
     */
    Secret createHarborSecret(K8SHarborSecret secret);


    /**
     * 查询secret
     */




    /**
     * 服务部署
     */
    String createDeployment(K8SDeployment k8SDeployment);


    /**
     *
     */
    String createService(K8SDeployment k8SDeployment);


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
     * 查看对应命名空间下的事件
     */
    void findEventInfoByNamespace(String namespace);


    /**
     * 创建storageClass
     */

    /**
     * 服务滚动更新
     */
    void rollImagesVersion(String namespace,String name);


    /**
     * pod下运行命令
     */
    String executeCommandOnPod(String podName, String namespace, String... cmd);


    /**
     * job 创建
     */



    /**
     * 创建命名空间
     */
    Namespace createNamespace(String name);

    /**
     * 删除命名空间
     */
    Boolean deleteNamespace(String name);

    /**
     * 容器配置挂载
     */

}
