package com.wenge.tbase.k8s.service;

import com.wenge.tbase.k8s.bean.vo.K8SConfigMap;
import com.wenge.tbase.k8s.bean.vo.K8SDeployment;
import com.wenge.tbase.k8s.bean.vo.K8SPersistentVolumeClaim;
import com.wenge.tbase.k8s.bean.vo.K8SSecret;
import com.wenge.tbase.k8s.bean.vo.deployment.K8SDeploymentCreate;
import io.fabric8.kubernetes.api.model.*;
import io.fabric8.kubernetes.api.model.apps.*;

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
     *
     * @param configMapInfo 配置信息
     * @return ConfigMap
     */
    ConfigMap createConfigMap(K8SConfigMap configMapInfo);

    /**
     * 查询命名空间下的configMap
     */
    List<String> findConfigMapByNamespace(String namespace);

    /**
     * 查看对应configMap下的详细信息
     * @param name 字典名称
     * @param namespace 名称空间
     * @return 检索字典信息
     */
    ConfigMap findConfigMapDetail(String name, String namespace);

    /**
     *查询当前名称空间下的所有字典
     * @param namespace 名称空间
     * @return 字典集
     */
    ConfigMapList listConfigMap(String namespace);

    /**
     * 修改对应configMap下的详细信息
     *
     * @param configMapInfo 更新信息
     * @return ConfigMap
     */
    ConfigMap editConfigMapDetail(K8SConfigMap configMapInfo);

    /**
     * 删除单个ConfigMap
     *
     * @param name
     * @param namespace
     * @return 删除结果
     */
    boolean delConfigMap(String name, String namespace);

    /**
     * 批量删除ConfigMap
     *
     * @param names
     * @param namespace
     * @return 删除结果
     */
    boolean delConfigMaps(List<String> names, String namespace);

    /**
     * 删除该命名空间下的所有字典
     *
     * @param namespace 命名空间
     * @return 删除结果
     */
    boolean delConfigMapsAll(String namespace);

    /**
     * 生成密文
     *
     * @param secretInfo 密文配置信息
     * @return 密文
     */
    Secret createSecret(K8SSecret secretInfo);

    /**
     * 查看对应密文的详细信息
     * @param name 密文名称
     * @param namespace 命名空间名称
     * @return 密文详细信息
     */
    Secret findSecretDetail(String name, String namespace);

    /**
     * 查询当前名称空间下所有密文
     * @param namespace
     * @return
     */
    SecretList listSecret(String namespace);

    /**
     * 修改密文
     *
     * @param secretInfo 密文配置信息
     * @return 密文
     */
    Secret editSecret(K8SSecret secretInfo);

    /**
     * 删除单个secret
     *
     * @param namespace  命名空间
     * @param secretName secret名
     * @return 删除结果
     */
    boolean delSecret(String namespace, String secretName);

    /**
     * 批量删除secret
     *
     * @param namespace   命名空间
     * @param secretNames secret名
     * @return 删除结果
     */
    boolean delSecrets(String namespace, List<String> secretNames);

    /**
     * 删除该命名空间下的所有密文
     *
     * @param namespace 命名空间
     * @return
     */
    boolean delSecretsAll(String namespace);

    /**
     * 创建存储卷声明
     *
     * @param pvcInfo 存储卷声明配置信息
     * @return pvc
     */
    PersistentVolumeClaim createPVC(K8SPersistentVolumeClaim pvcInfo);

    /**
     * 查看存储卷声明详细信息
     * @param name 存储卷声明名称
     * @param namespace 名称空间
     * @return  存储卷声明信息
     */
    PersistentVolumeClaim findPVCDetail(String name, String namespace);

    /**
     * 查询当前名称空间下所有存储卷
     * @param namespace 名称空间
     * @return 存储卷集
     */
    PersistentVolumeClaimList listPVC(String namespace);

    /**
     * 删除单个存储卷声明
     *
     * @param namespace 命名空间
     * @param pvcName   存储卷声明名称
     * @return 删除结果
     */
    boolean delPVC(String namespace, String pvcName);

    /**
     * 删除单个存储卷声明
     *
     * @param namespace 命名空间
     * @param pvcNames  存储卷声明名称集合
     * @return 删除结果
     */
    boolean delPVCs(String namespace, List<String> pvcNames);

    /**
     * 删除当前命名空间下所有存储卷声明
     *
     * @param namespace 命名空间
     * @return 删除结果
     */
    boolean delPVCsAll(String namespace);

    /**
     * 创建docker仓库密码
     */
    Secret createHarborSecret(K8SSecret secret);

    /**
     * Deployment服务部署
     * @param k8SDeployment 配置信息
     * @return deployment对象
     */
    Deployment createDeployment(K8SDeployment k8SDeployment);

    Deployment createDeployment(K8SDeploymentCreate k8SDeployment);

    /**
     * 查看Deployment详细信息
     * @param name Deployment名称
     * @param namespace 名称空间
     * @return  deployment对象
     */
    Deployment findDeploymentDetail(String name, String namespace);

    /**
     * 查询名称空间下的所有Deployment
     * @param namespace 名称空间
     * @return Deployment集
     */
    DeploymentList listDeployment(String namespace);

    /**
     * 删除单个
     * @param namespace 命名空间
     * @param deploymentName
     * @return 删除结果
     */
    boolean delDeployment(String namespace, String deploymentName);

    /**
     * 删除多个
     * @param namespace 命名空间
     * @param deploymentNames
     * @return 删除结果
     */
    boolean delDeployments(String namespace, List<String> deploymentNames);

    /**
     * 删除所有
     * @param namespace 命名空间
     * @return 删除结果
     */
    boolean delDeploymentsAll(String namespace);

    /**
     * StatefulSet服务部署
     * @param statefulSet 配置信息
     * @return statefulSet对象
     */
    StatefulSet createStatefulSet(StatefulSet statefulSet);

    /**
     * 查看StatefulSet的详细信息
     * @param name statefulSet名称
     * @param namespace 名称空间
     * @return statefulSet对象
     */
    StatefulSet findStatefulSetDetail(String name, String namespace);

    /**
     * 查询名称空间下的所有statefulSet
     * @param namespace 名称空间
     * @return statefulSet集
     */
    StatefulSetList listStatefulSet(String namespace);

    /**
     * 删除单个
     * @param namespace 命名空间
     * @param statefulSetName
     * @return 删除结果
     */
    boolean delStatefulSet(String namespace, String statefulSetName);

    /**
     * 删除多个
     * @param namespace 命名空间
     * @param statefulSetNames
     * @return 删除结果
     */
    boolean delStatefulSets(String namespace, List<String> statefulSetNames);

    /**
     * 删除所有
     * @param namespace 命名空间
     * @return 删除结果
     */
    boolean delStatefulSetsAll(String namespace);

    /**
     * DaemonSet服务部署
     * @param daemonSet 配置信息
     * @return daemonSet对象
     */
    DaemonSet createDaemonSet(DaemonSet daemonSet);

    /**
     * 查看DaemonSet详细信息
     * @param name DaemonSet名称
     * @param namespace 名称空间
     * @return DaemonSet对象
     */
    DaemonSet findDaemonSetDetail(String name, String namespace);

    /**
     * 查询名称空间下的所有DaemonSet
     * @param namespace 名称空间
     * @return DaemonSet集
     */
    DaemonSetList listDaemonSet(String namespace);

    /**
     * 删除单个
     * @param namespace 命名空间
     * @param daemonSetName
     * @return 删除结果
     */
    boolean delDaemonSet(String namespace, String daemonSetName);

    /**
     * 删除多个
     * @param namespace 命名空间
     * @param daemonSetNames
     * @return 删除结果
     */
    boolean delDaemonSetNames(String namespace, List<String> daemonSetNames);

    /**
     * 删除所有
     * @param namespace 命名空间
     * @return 删除结果
     */
    boolean delDaemonSetsAll(String namespace);

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
     *
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
    void rollImagesVersion(String namespace, String name);


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
