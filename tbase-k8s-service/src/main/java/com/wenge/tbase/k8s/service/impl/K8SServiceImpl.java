package com.wenge.tbase.k8s.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wenge.tbase.commons.result.ResultVO;
import com.wenge.tbase.k8s.bean.vo.*;
import com.wenge.tbase.k8s.bean.vo.deployment.K8SDeploymentCreate;
import com.wenge.tbase.k8s.constant.K8SConstant;
import com.wenge.tbase.k8s.listener.SimpleListener;
import com.wenge.tbase.k8s.service.K8SService;
import com.wenge.tbase.k8s.utils.CommonUtils;
import com.wenge.tbase.k8s.utils.DateUtil;
import com.wenge.tbase.k8s.utils.MyClient;
import io.fabric8.kubernetes.api.model.*;
import io.fabric8.kubernetes.api.model.apps.*;
import io.fabric8.kubernetes.api.model.autoscaling.v1.CrossVersionObjectReference;
import io.fabric8.kubernetes.api.model.autoscaling.v1.HorizontalPodAutoscaler;
import io.fabric8.kubernetes.api.model.autoscaling.v1.HorizontalPodAutoscalerSpec;
import io.fabric8.kubernetes.api.model.metrics.v1beta1.NodeMetrics;
import io.fabric8.kubernetes.api.model.storage.StorageClass;
import io.fabric8.kubernetes.api.model.storage.StorageClassList;
import io.fabric8.kubernetes.client.KubernetesClientException;
import io.fabric8.kubernetes.client.Watcher;
import io.fabric8.kubernetes.client.dsl.ExecWatch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class K8SServiceImpl implements K8SService {

    @Resource
    private MyClient kClient;

    @Override
    public List getAllNameSpace() {
        //初始化连接
//        KubernetesClient kClient=K8sUtils.getKubernetesClient();
        //获取命名空间信息
        NamespaceList namespaceList = kClient.namespaces().list();
        List<Namespace> namespaces = namespaceList.getItems();
        List<K8SNameSpace> k8SNameSpaceVOS = new ArrayList<>();
        //遍历拿取对应的数据
        namespaces.forEach(namespace -> {
            K8SNameSpace ns = new K8SNameSpace();
            ns.setName(namespace.getMetadata().getName());
            ns.setStatus(namespace.getStatus().getPhase().equals("Active") ? true : false);
            k8SNameSpaceVOS.add(ns);
        });
        return k8SNameSpaceVOS;
    }

    @Override
    public Map getNodeInfo() {
        //初始化连接
//        KubernetesClient kClient=K8sUtils.getKubernetesClient();
        //节点信息
        List<Node> nodes = kClient.nodes().list().getItems();
        List<K8SNode> k8SNodes = new ArrayList<>();
        Map result = new HashMap();
        //容器,用来承载变量信息
        Double totalCPU = new Double(0);
        Double totalMemory = new Double(0);
        Double totalUsCPU = new Double(0);
        Double totalUsMemory = new Double(0);
        Integer isActive = new Integer(0);
        //遍历拿数据
        for (Node node : nodes) {
            K8SNode k8SNode = new K8SNode();
            k8SNode.setName(node.getMetadata().getName());
            Map<String, Quantity> allocatable = node.getStatus().getAllocatable();
            if (node.getStatus().getPhase() == null) {
                k8SNode.setStatus(true);
                isActive++;
            } else {
                k8SNode.setStatus(false);
            }
            //请求对应节点的metric信息
            NodeMetrics nodeMetrics = kClient.top().nodes().metrics(node.getMetadata().getName());
            Map<String, Quantity> usage = nodeMetrics.getUsage();

            //节点已使用的资源
            Double usCPU = new Double(usage.get("cpu").getAmount()) / (1000 * 1000 * 1000);
            Double usMemory = new Double(new Double(usage.get("memory").getAmount()) / (1024 * 1024));
            k8SNode.setUsCpu(CommonUtils.formatDouble2(usCPU));
            k8SNode.setUsMemory(CommonUtils.formatDouble2(usMemory));
            //节点的cpu 内存数量
            Double allCpu = new Double((allocatable.get("cpu")).getAmount()) / 1000;
            k8SNode.setAllocatableCPU(CommonUtils.formatDouble2(allCpu));
            Double allMemory = new Double(new Double(allocatable.get("memory").getAmount()) / (1024 * 1024));
            k8SNode.setAllocatableMemory(CommonUtils.formatDouble2(allMemory));
            totalCPU += CommonUtils.formatDouble2(allCpu);
            totalMemory += allMemory;
            totalUsCPU += CommonUtils.formatDouble2(usCPU);
            totalUsMemory += CommonUtils.formatDouble2(usMemory);
            k8SNodes.add(k8SNode);
        }
        result.put("nodeInfo", k8SNodes);
        result.put("totalCPU", totalCPU);
        result.put("totalMemory", new BigDecimal(totalMemory).setScale(2, RoundingMode.DOWN));
        result.put("totalUsCPU", totalUsCPU);
        result.put("totalUsMemory", totalUsMemory);
        result.put("nodeNum", nodes.size());
        result.put("isActive", isActive);
        return result;
    }

    @Override
    public ConfigMap createConfigMap(K8SConfigMap configMapInfo) {
        ConfigMap configMap = new ConfigMap();
        ObjectMeta objectMeta = new ObjectMeta();
        objectMeta.setName(configMapInfo.getName());
        objectMeta.setNamespace(configMapInfo.getNamespace());
        objectMeta.setLabels(configMapInfo.getLabels());
        configMap.setMetadata(objectMeta);
        configMap.setData(configMapInfo.getData());
        ConfigMap map = kClient.configMaps().inNamespace(configMapInfo.getNamespace()).create(configMap);
        return map;
    }

    /**
     * TODO : 未完，待续
     *
     * @param namespace
     * @return
     */
    @Override
    public List<String> findConfigMapByNamespace(String namespace) {
        ConfigMapList configMapList = kClient.configMaps().list();
        List<ConfigMap> configMaps = configMapList.getItems();
        configMaps.forEach(configMap -> {

        });
        return null;
    }

    @Override
    public ConfigMap findConfigMapDetail(String name, String namespace) {
        /*io.fabric8.kubernetes.client.dsl.Resource<ConfigMap, DoneableConfigMap> configMapResource =
                kClient.configMaps().inNamespace(namespace).withName(name);
        ConfigMap configMap = configMapResource.edit().done();*/
        ConfigMap configMap = kClient.configMaps().inNamespace(namespace).withName(name).get();
        return configMap;
    }

    @Override
    public ConfigMapList listConfigMap(String namespace) {
        ConfigMapList configMapList = kClient.configMaps().inNamespace(namespace).list();
        return configMapList;
    }

    @Override
    public ConfigMap editConfigMapDetail(K8SConfigMap configMapInfo) {
        io.fabric8.kubernetes.client.dsl.Resource<ConfigMap, DoneableConfigMap> configMapResource = kClient.configMaps()
                .inNamespace(configMapInfo.getNamespace()).withName(configMapInfo.getName());
        ObjectMeta objectMeta = configMapResource.get().getMetadata();
        //Map<String, String> oLabels = objectMeta.getLabels();
        Map<String, String> nLabels = configMapInfo.getLabels();
        /*if (oLabels == null && nLabels != null) {
            oLabels = nLabels;
        }
        if (oLabels != null && nLabels != null) {
            oLabels.putAll(nLabels);
        }*/
        objectMeta.setLabels(nLabels);
        //修改详细信息
        ConfigMap configMap =
                configMapResource.edit().withMetadata(objectMeta).withData(configMapInfo.getData()).done();//.addToData(configMapInfo.getData())
        //打印更新信息
        /*if (configMapInfo.getData() != null && configMapInfo.getData().size() > 0) {
            log.info("Upserted ConfigMap at data " + configMap.getData());
        }
        if (nLabels != null && nLabels.size() > 0) {
            log.info("Upserted ConfigMap at labels " + configMap.getMetadata().getLabels());
        }*/
        return configMap;
    }

    @Override
    public boolean delConfigMap(String name, String namespace) {
        Boolean delete = kClient.configMaps().inNamespace(namespace).withName(name).delete();
        return delete;
    }

    @Override
    public boolean delConfigMaps(List<String> names, String namespace) {
        boolean deleted = true;
        try {
            for (int i = 0; i < names.size(); i++) {
                delConfigMap(names.get(i), namespace);
            }
        } catch (Exception e) {
            deleted = false;
        }
        return deleted;
    }

    @Override
    public boolean delConfigMapsAll(String namespace) {
        Boolean delete = kClient.configMaps().inNamespace(namespace).delete();
        return delete;
    }

    /**
     * TODO ：未调试
     *
     * @return
     */
    public Secret createHarborSecret(K8SSecret harborSecret) {
        try {
            String auth = harborSecret.getDockerUsername() + ":" + harborSecret.getDockerSecret();
            //Base64编码
            String baseAuth = Base64.getEncoder().encodeToString(auth.getBytes("utf-8"));
            //拼接秘钥
            JSONObject mainObject = new JSONObject();
            Map tmpMap = new HashMap();
            tmpMap.put("username", harborSecret.getDockerUsername());
            tmpMap.put("password", harborSecret.getDockerSecret());
            tmpMap.put("auth", baseAuth);
            mainObject.put(harborSecret.getDockerServer(), tmpMap);
            JSONObject authObject = new JSONObject();
            authObject.put("auths", mainObject);
            //生成最终的秘钥
            String finalAuth = Base64.getEncoder().encodeToString(authObject.toJSONString(0).getBytes("utf-8"));
            Secret secret = new Secret();
            Map data = new HashMap();
            data.put(".dockerconfigjson", ">-" + finalAuth);
            secret.setData(data);
            ObjectMeta objectMeta = new ObjectMeta();
            objectMeta.setCreationTimestamp(new Date().toString());
            objectMeta.setName(harborSecret.getName());
            objectMeta.setNamespace(harborSecret.getNamespace());
            List<ManagedFieldsEntry> managedFieldsEntryList = new ArrayList<>();
            ManagedFieldsEntry managedFieldsEntry = new ManagedFieldsEntry();
            managedFieldsEntry.setApiVersion("v1");
            managedFieldsEntry.setManager("Mozilla");
            managedFieldsEntry.setOperation("Update");
            managedFieldsEntry.setFieldsType("FieldsV1");
            managedFieldsEntry.setTime(new Date().toString());
            managedFieldsEntryList.add(managedFieldsEntry);
            objectMeta.setManagedFields(managedFieldsEntryList);
            secret.setMetadata(objectMeta);
            secret.setType("kubernetes.io/dockerconfigjson");
            Secret result = kClient.secrets().inNamespace(harborSecret.getNamespace()).create(secret);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Secret createSecret(K8SSecret secretInfo) {
        Secret secret = new Secret();
        String secretType = secretInfo.getType();
        //{"auths":{"DOCKER_REGISTRY_SERVER":{"username":"DOCKER_USER","password":"DOCKER_PASSWORD",
        // "auth":"RE9DS0VSX1VTRVI6RE9DS0VSX1BBU1NXT1JE"}}}
        if (secretType.equals("kubernetes.io/dockerconfigjson")) {
            try {
                String finalAuth = CreateCipher(secretInfo);
                Map data = new HashMap<String, String>();
                data.put(".dockerconfigjson", finalAuth);
                secret.setData(data);
            } catch (UnsupportedEncodingException e) {
                log.error(e.getMessage());
            }
        } else if (secretType.equals("Opaque")) {
            try {
                if (secretInfo.getData() != null) {
                    Map<String, String> data = secretInfo.getData();
                    for (Map.Entry<String, String> entry : data.entrySet()) {
                        String key = entry.getKey();
                        String value = entry.getValue();
                        String baseVal = Base64.getEncoder().encodeToString(value.getBytes("utf-8"));
                        data.put(key, baseVal);
                    }
                    secret.setData(data);
                }
            } catch (UnsupportedEncodingException e) {
                log.error(e.getMessage());
            }
        } else if (secretType.equals("kubernetes.io/tls")) {
            try {
                if (secretInfo.getTlsCrt() != null || secretInfo.getTlsKey() != null) {
                    HashMap<String, String> data = new HashMap<>();
                    if (secretInfo.getTlsKey() != null) {
                        String baseKey = Base64.getEncoder().encodeToString(secretInfo.getTlsKey().getBytes("utf-8"));
                        data.put("tls.key", baseKey);
                    }
                    if (secretInfo.getTlsCrt() != null) {
                        String baseCrt = Base64.getEncoder().encodeToString(secretInfo.getTlsCrt().getBytes("utf-8"));
                        data.put("tls.crt", baseCrt);
                    }
                    secret.setData(data);
                }
            } catch (UnsupportedEncodingException e) {
                log.error(e.getMessage());
            }
        } else {
            log.error("密文类型不正确！");
            return null;
        }
        ObjectMeta objectMeta = new ObjectMeta();
        objectMeta.setName(secretInfo.getName());
        objectMeta.setNamespace(secretInfo.getNamespace());
        secret.setMetadata(objectMeta);
        secret.setType(secretInfo.getType());
        return kClient.secrets().inNamespace(secretInfo.getNamespace()).create(secret);
    }

    @Override
    public Secret findSecretDetail(String name, String namespace) {
        Secret secret = kClient.secrets().inNamespace(namespace).withName(name).get();
        return secret;
    }

    @Override
    public SecretList listSecret(String namespace) {
        SecretList secretList = kClient.secrets().inNamespace(namespace).list();
        return secretList;
    }

    @Override
    public Secret editSecret(K8SSecret secretInfo) {
        io.fabric8.kubernetes.client.dsl.Resource<Secret, DoneableSecret> secretDoneableSecretResource =
                kClient.secrets().inNamespace(secretInfo.getNamespace()).withName(secretInfo.getName());
        String secretType = secretDoneableSecretResource.get().getType();
        Map<String, String> data = new HashMap<String, String>();
        if (secretType.equals("kubernetes.io/dockerconfigjson")) {
            if (secretInfo.getDockerSecret() == null && secretInfo.getDockerServer() == null && secretInfo.getDockerUsername() == null) {
                return null;
            }
            try {
                String finalAuth = CreateCipher(secretInfo);
                data.put(".dockerconfigjson", finalAuth);
            } catch (UnsupportedEncodingException e) {
                log.error(e.getMessage());
            }
        } else if (secretType.equals("Opaque")) {
            try {
                if (secretInfo.getData() != null) {
                    data = secretInfo.getData();
                    for (Map.Entry<String, String> entry : data.entrySet()) {
                        String key = entry.getKey();
                        String value = entry.getValue();
                        String baseVal = Base64.getEncoder().encodeToString(value.getBytes("utf-8"));
                        data.put(key, baseVal);
                    }
                }
            } catch (UnsupportedEncodingException e) {
                log.error(e.getMessage());
            }
        } else {
            try {
                if (secretInfo.getTlsCrt() != null || secretInfo.getTlsKey() != null) {
                    if (secretInfo.getTlsKey() != null) {
                        String baseKey = Base64.getEncoder().encodeToString(secretInfo.getTlsKey().getBytes("utf-8"));
                        data.put("tls.key", baseKey);
                    }
                    if (secretInfo.getTlsCrt() != null) {
                        String baseCrt = Base64.getEncoder().encodeToString(secretInfo.getTlsCrt().getBytes("utf-8"));
                        data.put("tls.crt", baseCrt);
                    }
                }
            } catch (UnsupportedEncodingException e) {
                log.error(e.getMessage());
            }
        }
        Secret secret = secretDoneableSecretResource.edit().withData(data).done();
        return secret;
    }

    private String CreateCipher(K8SSecret secretInfo) throws UnsupportedEncodingException {
        String auth = secretInfo.getDockerUsername() + ":" + secretInfo.getDockerSecret();
        //Base64编码
        String baseAuth = Base64.getEncoder().encodeToString(auth.getBytes("utf-8"));
        //拼接秘钥
        JSONObject mainObject = new JSONObject();
        Map tmpMap = new HashMap();
        tmpMap.put("username", secretInfo.getDockerUsername());
        tmpMap.put("password", secretInfo.getDockerSecret());
        tmpMap.put("auth", baseAuth);
        //mainObject.put("DOCKER_REGISTRY_SERVER", tmpMap);
        mainObject.put(secretInfo.getDockerServer(), tmpMap);
        JSONObject authObject = new JSONObject();
        authObject.put("auths", mainObject);
        //生成最终的秘钥
        return Base64.getEncoder().encodeToString(authObject.toJSONString().getBytes("utf-8"));
    }

    @Override
    public boolean delSecret(String namespace, String secretName) {
        Boolean deleted = kClient.secrets().inNamespace(namespace).withName(secretName).delete();
        return deleted;
    }

    @Override
    public boolean delSecrets(String namespace, List<String> secretNames) {
        boolean deleted = true;
        try {
            for (int i = 0; i < secretNames.size(); i++) {
                delSecret(namespace, secretNames.get(i));
            }
        } catch (Exception e) {
            deleted = false;
        }
        return deleted;
    }

    @Override
    public boolean delSecretsAll(String namespace) {
        Boolean deleted = kClient.secrets().inNamespace(namespace).delete();
        return deleted;
    }

    @Override
    public Deployment createDeployment(K8SDeployment k8SDeployment) {
        try {
            //List<Volume> volumes = getVolumes(k8SDeployment);
            Deployment deployment = new DeploymentBuilder()
                    .withMetadata(new ObjectMetaBuilder()
                            .withNamespace(k8SDeployment.getNamespace())
                            .withName(k8SDeployment.getName())
                            .withLabels(k8SDeployment.getLabels())
                            .withAnnotations(k8SDeployment.getAnnotations()).build())

                    .withSpec(new DeploymentSpecBuilder()
                            .withReplicas(k8SDeployment.getReplicas())
                            .withSelector(new LabelSelectorBuilder()
                                    .withMatchLabels(k8SDeployment.getLabels())
                                    .build())
                            .withTemplate(new PodTemplateSpecBuilder()
                                    .withMetadata(new ObjectMetaBuilder()
                                            .withLabels(k8SDeployment.getLabels())
                                            .build())
                                    .withSpec(new PodSpecBuilder()
                                            .withContainers(new ContainerBuilder()
                                                    .withName(k8SDeployment.getServiceName())
                                                    .withImage(k8SDeployment.getImageUrl())
                                                    .withPorts(new ContainerPortBuilder()
                                                            .withContainerPort(k8SDeployment.getPort())
                                                            .withProtocol(k8SDeployment.getProtocolType())
                                                            .build())
                                                    .withEnv(k8SDeployment.getEnv())
                                                    .withEnvFrom(k8SDeployment.getEnvFrom())
                                                    .build())
                                            .withVolumes(k8SDeployment.getVolume())
                                            .build())
                                    .build())
                            .build())
                    .build();
            log.info("Created deployment");
            deployment = kClient.apps().deployments().inNamespace(k8SDeployment.getNamespace()).create(deployment);
            //checkEventInfo(k8SDeployment.getNamespace(), k8SDeployment.getName());
            return deployment;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public Deployment createDeployment(K8SDeploymentCreate k8SDeployment) {
            k8SDeployment.init();
            Deployment deployment = new DeploymentBuilder().withMetadata(k8SDeployment.metadata())
                    .withSpec(k8SDeployment.spec()).build();
            deployment = kClient.apps().deployments().inNamespace(k8SDeployment.getNamespace()).createOrReplace(deployment);
        return deployment;
    }

    @Override
    public Deployment findDeploymentDetail(String name, String namespace) {
        Deployment deployment = kClient.apps().deployments().inNamespace(namespace).withName(name).get();
        return deployment;
    }

    @Override
    public DeploymentList listDeployment(String namespace) {
        DeploymentList deploymentList = kClient.apps().deployments().inNamespace(namespace).list();
        return deploymentList;
    }

    @Override
    public boolean delDeployment(String namespace, String deploymentName) {
        Boolean deleted = kClient.apps().deployments().inNamespace(namespace).withName(deploymentName).delete();
        return deleted;
    }

    @Override
    public boolean delDeployments(String namespace, List<String> deploymentNames) {
        boolean deleted = true;
        try {
            for (int i = 0; i < deploymentNames.size(); i++) {
                delDeployment(namespace, deploymentNames.get(i));
            }
        } catch (Exception e) {
            deleted = false;
        }
        return deleted;
    }

    @Override
    public boolean delDeploymentsAll(String namespace) {
        Boolean deleted = kClient.apps().deployments().inNamespace(namespace).delete();
        return deleted;
    }

    @Override
    public Deployment deploymentYaml(String namespace, String deploymentName) {
        return kClient.apps().deployments().inNamespace(namespace).withName(deploymentName).get();
    }

    @Override
    public Deployment yamlToDeployment(String namespace, Deployment deployment) {
        return kClient.apps().deployments().inNamespace(namespace).createOrReplace(deployment);
    }

    @Override
    public StatefulSet createStatefulSet(StatefulSet statefulSetInfo) {
        StatefulSet statefulSet = kClient.apps().statefulSets().create(statefulSetInfo);
        return statefulSet;
    }

    @Override
    public StatefulSet findStatefulSetDetail(String name, String namespace) {
        StatefulSet statefulSet = kClient.apps().statefulSets().inNamespace(namespace).withName(name).get();
        return statefulSet;
    }

    @Override
    public StatefulSetList listStatefulSet(String namespace) {
        StatefulSetList statefulSetList = kClient.apps().statefulSets().inNamespace(namespace).list();
        return statefulSetList;
    }

    @Override
    public boolean delStatefulSet(String namespace, String statefulSetName) {
        Boolean deleted = kClient.apps().statefulSets().inNamespace(namespace).withName(statefulSetName).delete();
        return deleted;
    }

    @Override
    public boolean delStatefulSets(String namespace, List<String> statefulSetNames) {
        boolean deleted = true;
        try {
            for (int i = 0; i < statefulSetNames.size(); i++) {
                delStatefulSet(namespace, statefulSetNames.get(i));
            }
        } catch (Exception e) {
            deleted = false;
        }
        return deleted;
    }

    @Override
    public boolean delStatefulSetsAll(String namespace) {
        Boolean deleted = kClient.apps().statefulSets().inNamespace(namespace).delete();
        return deleted;
    }

    @Override
    public DaemonSet createDaemonSet(DaemonSet daemonSetInfo) {
        DaemonSet daemonSet = kClient.apps().daemonSets().create(daemonSetInfo);
        return daemonSet;
    }

    @Override
    public DaemonSet findDaemonSetDetail(String name, String namespace) {
        DaemonSet daemonSet = kClient.apps().daemonSets().inNamespace(namespace).withName(name).get();
        return daemonSet;
    }

    @Override
    public DaemonSetList listDaemonSet(String namespace) {
        DaemonSetList daemonSetList = kClient.apps().daemonSets().inNamespace(namespace).list();
        return daemonSetList;
    }

    @Override
    public boolean delDaemonSet(String namespace, String daemonSetName) {
        Boolean deleted = kClient.apps().daemonSets().inNamespace(namespace).withName(daemonSetName).delete();
        return deleted;
    }

    @Override
    public boolean delDaemonSetNames(String namespace, List<String> daemonSetNames) {
        boolean deleted = true;
        try {
            for (int i = 0; i < daemonSetNames.size(); i++) {
                delDaemonSet(namespace, daemonSetNames.get(i));
            }
        } catch (Exception e) {
            deleted = false;
        }
        return deleted;
    }

    @Override
    public boolean delDaemonSetsAll(String namespace) {
        Boolean deleted = kClient.apps().daemonSets().inNamespace(namespace).delete();
        return deleted;
    }

    /*private List<Volume> getVolumes(K8SDeployment k8SDeployment) {
        List<Volume> volumes = null;
        List<K8SVolumeInfo> volumeInfos = k8SDeployment.getVolume();
        if (volumeInfos != null) {
            for (K8SVolumeInfo volumeInfo : volumeInfos) {
                Volume volume = new Volume();
                String type = volumeInfo.getType();
                if (type.equals("NFS")) {
                    if (volumeInfo.getNFSPath() == null || volumeInfo.getNFSServer() == null) {
                        return null;
                    }
                    NFSVolumeSource nfsVolumeSource = new NFSVolumeSource();
                    nfsVolumeSource.setPath(volumeInfo.getNFSPath());
                    nfsVolumeSource.setServer(volumeInfo.getNFSServer());
                    volume.setNfs(nfsVolumeSource);
                    volumes.add(volume);
                } else if (type.equals("存储卷声明")) {

                } else if (type.equals("emptyDir")) {
                    EmptyDirVolumeSource emptyDirVolumeSource = new EmptyDirVolumeSource();
                    volume.setEmptyDir(emptyDirVolumeSource);
                    volumes.add(volume);
                } else if (type.equals("hostPath")) {
                    HostPathVolumeSource hostPathVolumeSource = new HostPathVolumeSource();
                    hostPathVolumeSource.setPath(volumeInfo.getPath());
                    hostPathVolumeSource.setType(volumeInfo.getHostPathType());
                    volume.setHostPath(hostPathVolumeSource);
                    volumes.add(volume);
                } else if (type.equals("configMap")) {
                    ConfigMapVolumeSource configMapVolumeSource = new ConfigMapVolumeSource();
                    configMapVolumeSource.setName(volumeInfo.getConfigMapName());
                    Map<String, String> configMapKeyToPath = volumeInfo.getConfigMapKeyToPath();
                    List<KeyToPath> keyToPaths = null;
                    if (configMapKeyToPath != null && configMapKeyToPath.size() > 0) {
                        for (Map.Entry<String, String> entry :
                                configMapKeyToPath.entrySet()) {
                            KeyToPath keyToPath = new KeyToPath();
                            keyToPath.setKey(entry.getKey());
                            keyToPath.setPath(entry.getValue());
                            keyToPaths.add(keyToPath);
                        }
                    }
                    configMapVolumeSource.setItems(keyToPaths);
                    volume.setConfigMap(configMapVolumeSource);
                    volumes.add(volume);
                } else {
                    SecretVolumeSource secretVolumeSource = new SecretVolumeSource();
                    secretVolumeSource.setSecretName(volumeInfo.getSecretName());
                    Map<String, String> secretKeyToPath = volumeInfo.getSecretKeyToPath();
                    List<KeyToPath> keyToPaths = null;
                    if (secretKeyToPath != null && secretKeyToPath.size() > 0) {
                        for (Map.Entry<String, String> entry :
                                secretKeyToPath.entrySet()) {
                            KeyToPath keyToPath = new KeyToPath();
                            keyToPath.setKey(entry.getKey());
                            keyToPath.setPath(entry.getValue());
                            keyToPaths.add(keyToPath);
                        }
                    }
                    secretVolumeSource.setItems(keyToPaths);
                    volume.setSecret(secretVolumeSource);
                    volumes.add(volume);
                }
            }
        }
        return volumes;
    }*/

    /*private List<EnvVar> getEnvVar(K8SDeployment deploymentInfo) {
        List<EnvVar> envVars = null;
        List<K8SEnvVar> envVarsInfo = deploymentInfo.getEnv();
        if (envVarsInfo != null) {
            for (K8SEnvVar envVarInfo : envVarsInfo) {
                EnvVar envVar = new EnvVarBuilder()
                        .withName(envVarInfo.getName())
                        .withValue(envVarInfo.getName())
                        .withValueFrom(envVarInfo.getValueFrom())
                        .build();
                envVars.add(envVar);
            }
        }
        return envVars;
    }*/

    @Override
    public PersistentVolumeClaim createPVC(K8SPersistentVolumeClaim pvcInfo) {
        PersistentVolumeClaim pvc = new PersistentVolumeClaim();
        ObjectMeta objectMeta = new ObjectMeta();
        objectMeta.setName(pvcInfo.getName());
        objectMeta.setNamespace(pvcInfo.getNamespace());
        // objectMeta.setLabels(metadata.getObject(("labels"), Map.class));
        Map<String, String> map = new HashMap<>();
        map.put("k8s.kuboard.cn/pvcType", "Dynamic");
        objectMeta.setAnnotations(map);
        PersistentVolumeClaimSpec pvcs = new PersistentVolumeClaimSpec();
        pvcs.setStorageClassName(pvcInfo.getStorageType());
        List<String> accessModes = (List<String>) pvcInfo.getAccessmode();
        List<String> accessModesFin = new ArrayList<>();
        for (String accessMode : accessModes) {
            if (accessMode.equals("只能被单点读写")) {
                accessModesFin.add("ReadWriteOnce");
            } else if (accessMode.equals("可被多节点只读")) {
                accessModesFin.add("ReadOnlyMany");
            } else {
                accessModesFin.add("ReadWriteMany");
            }
        }
        pvcs.setAccessModes(accessModesFin);
        ResourceRequirements resourceRequirements = new ResourceRequirements();
        Map<String, Quantity> requestsMap = new HashMap<>();
        String amountStr = pvcInfo.getTotal();
        Quantity quantity = new Quantity(amountStr);
        requestsMap.put("storage", quantity);
        resourceRequirements.setRequests(requestsMap);
        pvcs.setResources(resourceRequirements);
        pvc.setMetadata(objectMeta);
        pvc.setSpec(pvcs);
        PersistentVolumeClaim persistentVolumeClaim =
                kClient.persistentVolumeClaims().inNamespace(pvcInfo.getNamespace()).create(pvc);
        return persistentVolumeClaim;
    }

    @Override
    public PersistentVolumeClaim findPVCDetail(String name, String namespace) {
        PersistentVolumeClaim pvc =
                kClient.persistentVolumeClaims().inNamespace(namespace).withName(name).get();
        return pvc;
    }

    @Override
    public PersistentVolumeClaimList listPVC(String namespace) {
        PersistentVolumeClaimList persistentVolumeClaimList = kClient.persistentVolumeClaims().inNamespace(namespace).list();
        return persistentVolumeClaimList;
    }

    @Override
    public boolean delPVC(String namespace, String pvcName) {
        Boolean deleted = kClient.persistentVolumeClaims().inNamespace(namespace).withName(pvcName).delete();
        return deleted;
    }

    @Override
    public boolean delPVCs(String namespace, List<String> pvcNames) {
        boolean deleted = true;
        try {
            for (int i = 0; i < pvcNames.size(); i++) {
                delPVC(namespace, pvcNames.get(i));
            }
        } catch (Exception e) {
            deleted = false;
        }
        return deleted;
    }

    @Override
    public boolean delPVCsAll(String namespace) {
        Boolean deleted = kClient.persistentVolumeClaims().inNamespace(namespace).delete();
        return deleted;
    }

    /**
     * 创建Service, 供外部访问使用
     */
    public String createService(K8SDeployment k8SDeployment) {
        io.fabric8.kubernetes.api.model.Service service = new ServiceBuilder()
                .withNewMetadata()
                .withName(k8SDeployment.getName())
                .withNamespace(k8SDeployment.getNamespace())
                .endMetadata()
                .withNewSpec()
                .withSelector(Collections.singletonMap("app", k8SDeployment.getName()))
                .addNewPort()
                .withName(k8SDeployment.getName())
                .withProtocol("TCP")
                .withPort(k8SDeployment.getPort())
                .withTargetPort(new IntOrString(k8SDeployment.getPort()))
                .endPort()
                .withType("NodePort")
                .endSpec()
                .withNewStatus()
                .withNewLoadBalancer()
                .addNewIngress()
                .endIngress()
                .endLoadBalancer()
                .endStatus()
                .build();

        service = kClient.services().inNamespace(kClient.getNamespace()).create(service);
        log.info("Created service with name ", service.getMetadata().getName());
        String serviceURL =
                kClient.services().inNamespace(k8SDeployment.getNamespace()).withName(service.getMetadata().getName()).getURL(k8SDeployment.getName());
        return serviceURL;
    }

    /**
     * 创建弹性伸缩
     */
    public void createOrReplaceHorizontalPodAutoscaler(K8SDeployment k8SDeployment) {
        log.info("*******开始创建弹性伸缩*****");
        HorizontalPodAutoscaler horizontalPodAutoscaler =
                horizontalPodAutoscalerVoToHorizontalPodAutoscaler(k8SDeployment);
        kClient.autoscaling().v1().horizontalPodAutoscalers().createOrReplace(horizontalPodAutoscaler);
        log.info("*******************弹性伸缩创建完毕******************");
    }


    private HorizontalPodAutoscaler horizontalPodAutoscalerVoToHorizontalPodAutoscaler(K8SDeployment k8SDeployment) {
        HorizontalPodAutoscaler horizontalPodAutoscaler = new HorizontalPodAutoscaler();

        // 设置apiVersion和kind
        horizontalPodAutoscaler.setApiVersion(K8SConstant.HPA_APIVERSION);
        horizontalPodAutoscaler.setKind(K8SConstant.HPA_KIND);

        // 设置metadata
        ObjectMeta metadata = new ObjectMeta();
        metadata.setName(k8SDeployment.getServiceName());
        metadata.setNamespace(k8SDeployment.getNamespace());

        // 设置spec
        HorizontalPodAutoscalerSpec spec = new HorizontalPodAutoscalerSpec();
        spec.setMaxReplicas(k8SDeployment.getMaxReplicas());
        spec.setMinReplicas(k8SDeployment.getMinReplicas());
        CrossVersionObjectReference scaleRef = new CrossVersionObjectReference();
        scaleRef.setName(k8SDeployment.getServiceName());
        scaleRef.setApiVersion(K8SConstant.API_VERSION);
        scaleRef.setKind(k8SDeployment.getDeployMode());
        spec.setScaleTargetRef(scaleRef);
        spec.setTargetCPUUtilizationPercentage(k8SDeployment.getTargetCPUUtilizationPercentage());

        horizontalPodAutoscaler.setMetadata(metadata);
        horizontalPodAutoscaler.setSpec(spec);

        return horizontalPodAutoscaler;
    }


    /**
     * 获取存储资源
     * tip:
     * 存储卷声明   metadata--  namespace+name    创建时间   now-creationTimestamp
     * 容量/状态    status--  capacity  phase
     * 名称         spec  -- volumnName
     */
    public Map findStorageInfo() {
        Map<String, List<K8SStorage>> result = new HashMap<>();
        StorageClassList storageClassList = kClient.storage().storageClasses().list();
        List<String> nameList = new ArrayList<>();
        storageClassList.getItems().forEach(f -> {
            //获取名称
            nameList.add(f.getMetadata().getName());
        });
        PersistentVolumeClaimList persistentVolumeClaimList = kClient.persistentVolumeClaims().list();

        nameList.forEach(n -> {
            List<K8SStorage> tmp = new ArrayList<>();
            persistentVolumeClaimList.getItems().forEach(p -> {
                if (n.equals(p.getSpec().getStorageClassName())) {
                    K8SStorage k8SStorage = new K8SStorage();
                    k8SStorage.setPvClaim(p.getMetadata().getNamespace() + "/" + p.getMetadata().getName());
                    k8SStorage.setCreateTime(DateUtil.timeStampFormat(p.getMetadata().getCreationTimestamp()));
                    k8SStorage.setStatus(p.getStatus().getPhase());
                    if (k8SStorage.getStatus().equals("Pending")) {
                        k8SStorage.setCapacity("0Gi");
                    } else {
                        k8SStorage.setCapacity(p.getStatus().getCapacity().get("storage").getAmount() + p.getStatus().getCapacity().get("storage").getFormat());
                    }
                    k8SStorage.setName(p.getSpec().getVolumeName());
                    tmp.add(k8SStorage);
                }
            });
            result.put(n, tmp);
        });
        return result;

    }

    public String findStorageDescribeInfo(String name) {
        StorageClass storageClass = kClient.storage().storageClasses().withName(name).get();
        Yaml yaml = new Yaml();
        return yaml.dump(storageClass);
    }


    public void checkEventInfo(String namespace, String name) {
        kClient.events().inNamespace(namespace).withName(name).watch(new Watcher<Event>() {
            @Override
            public void eventReceived(Action action, Event resource) {
                log.info(resource.getMessage());
            }

            @Override
            public void onClose(KubernetesClientException cause) {
            }
        }).close();
    }

    public void findEventInfoByNamespace(String namespace) {
        EventList event = kClient.events().inNamespace("default").list();
    }

    @Override
    public void rollImagesVersion(String namespace, String name) {
    }


    @Override
    public String executeCommandOnPod(String podName, String namespace, String... cmd) {
        Pod pod = kClient.pods().inNamespace(namespace).withName(podName).get();
        log.info("pod connect success");
        CompletableFuture<String> data = new CompletableFuture<>();
        try (ExecWatch execWatch = execCmd(pod, data, cmd)) {
            return data.get(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Namespace createNamespace(String name) {
        //创建命名空间对象
        Namespace namespace = new Namespace();
        //命名空间对象进行赋值
        ObjectMeta objectMeta = new ObjectMeta();
        objectMeta.setName(name);
        namespace.setMetadata(objectMeta);
        Namespace result = kClient.namespaces().create(namespace);
        return result;
    }

    @Override
    public Boolean deleteNamespace(String name) {
        //创建命名空间对象
        Namespace namespace = new Namespace();
        //命名空间对象进行赋值
        ObjectMeta objectMeta = new ObjectMeta();
        objectMeta.setName(name);
        namespace.setMetadata(objectMeta);
        Boolean delete = kClient.namespaces().delete(namespace);
        return delete;
    }

    private ExecWatch execCmd(Pod pod, CompletableFuture<String> data, String... command) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        return kClient.pods()
                .inNamespace(pod.getMetadata().getNamespace())
                .withName(pod.getMetadata().getName())
                .writingOutput(baos)
                .writingError(baos)
                .usingListener(new SimpleListener(data, baos))
                .exec(command);
    }

}
