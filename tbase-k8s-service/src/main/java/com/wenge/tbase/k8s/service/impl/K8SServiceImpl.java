package com.wenge.tbase.k8s.service.impl;

import cn.hutool.json.JSONObject;
import com.wenge.tbase.k8s.bean.vo.*;
import com.wenge.tbase.k8s.constant.K8SConstant;
import com.wenge.tbase.k8s.listener.SimpleListener;
import com.wenge.tbase.k8s.service.K8SService;
import com.wenge.tbase.k8s.utils.CommonUtils;
import com.wenge.tbase.k8s.utils.DateUtil;
import com.wenge.tbase.k8s.utils.MyClient;
import io.fabric8.kubernetes.api.model.*;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.api.model.apps.DeploymentBuilder;
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
        NamespaceList namespaceList= kClient.namespaces().list();
        List<Namespace> namespaces=namespaceList.getItems();
        List<K8SNameSpace> k8SNameSpaceVOS =new ArrayList<>();
        //遍历拿取对应的数据
        namespaces.forEach(namespace -> {
            K8SNameSpace ns=new K8SNameSpace();
            ns.setName(namespace.getMetadata().getName());
            ns.setStatus(namespace.getStatus().getPhase().equals("Active")?true:false);
            k8SNameSpaceVOS.add(ns);
        });
        return k8SNameSpaceVOS;
    }

    @Override
    public Map getNodeInfo() {
        //初始化连接
//        KubernetesClient kClient=K8sUtils.getKubernetesClient();
        //节点信息
        List<Node>nodes=kClient.nodes().list().getItems();
        List<K8SNode> k8SNodes=new ArrayList<>();
        Map result=new HashMap();
        //容器,用来承载变量信息
        Double totalCPU=new Double(0);
        Double totalMemory=new Double(0);
        Double totalUsCPU=new Double(0);
        Double totalUsMemory=new Double(0);
        Integer isActive=new Integer(0);
        //遍历拿数据
        for (Node node : nodes) {
            K8SNode k8SNode=new K8SNode();
            k8SNode.setName(node.getMetadata().getName());
            Map<String, Quantity> allocatable=node.getStatus().getAllocatable();
            if(node.getStatus().getPhase()==null){
                k8SNode.setStatus(true);
                isActive++;
            }else {
                k8SNode.setStatus(false);
            }
            //请求对应节点的metric信息
            NodeMetrics nodeMetrics=kClient.top().nodes().metrics(node.getMetadata().getName());
            Map<String, Quantity> usage = nodeMetrics.getUsage();

            //节点已使用的资源
            Double usCPU=new Double(usage.get("cpu").getAmount())/(1000*1000*1000);
            Double usMemory=new Double(new Double(usage.get("memory").getAmount())/(1024*1024));
            k8SNode.setUsCpu(CommonUtils.formatDouble2(usCPU));
            k8SNode.setUsMemory(CommonUtils.formatDouble2(usMemory));
            //节点的cpu 内存数量
            Double allCpu=new Double((allocatable.get("cpu")).getAmount())/1000;
            k8SNode.setAllocatableCPU(CommonUtils.formatDouble2(allCpu));
            Double allMemory=new Double(new Double(allocatable.get("memory").getAmount())/(1024*1024));
            k8SNode.setAllocatableMemory(CommonUtils.formatDouble2(allMemory));
            totalCPU+=CommonUtils.formatDouble2(allCpu);
            totalMemory+=allMemory;
            totalUsCPU+=CommonUtils.formatDouble2(usCPU);
            totalUsMemory+=CommonUtils.formatDouble2(usMemory);
            k8SNodes.add(k8SNode);
        }
        result.put("nodeInfo",k8SNodes);
        result.put("totalCPU",totalCPU);
        result.put("totalMemory",new BigDecimal(totalMemory).setScale(2,RoundingMode.DOWN));
        result.put("totalUsCPU",totalUsCPU);
        result.put("totalUsMemory",totalUsMemory);
        result.put("nodeNum",nodes.size());
        result.put("isActive",isActive);
        return result;
    }


    /**
     * TODO : 未完，待续
     * @param namespace
     * @return
     */
    @Override
    public List<String> findConfigMapByNamespace(String namespace) {
        ConfigMapList configMapList=kClient.configMaps().list();
        List<ConfigMap> configMaps=configMapList.getItems();
        configMaps.forEach(configMap -> {

        });
        return null;
    }

    @Override
    public ConfigMap findConfigMapDetail(String name,String namespace) {
        io.fabric8.kubernetes.client.dsl.Resource<ConfigMap, DoneableConfigMap> configMapResource = kClient.configMaps().inNamespace(namespace).withName(name);
        ConfigMap configMap=configMapResource.edit().done();
        return configMap;
    }



    @Override
    public ConfigMap editConfigMapDetail(String name, String namespace, Map data) {
        io.fabric8.kubernetes.client.dsl.Resource<ConfigMap, DoneableConfigMap> configMapResource = kClient.configMaps().inNamespace(namespace).withName(name);
        //修改详细信息
        ConfigMap configMap=configMapResource.edit().addToData(data).done();
        //修改标签
        log.info("Upserted ConfigMap at " + configMap.getMetadata().getSelfLink() + " data " + configMap.getData());
        return configMap;
    }

    /**
     *
     * TODO ：未调试
     * @return
     */
    public Secret createHarborSecret(K8SHarborSecret harborSecret){
        try {
            String auth = harborSecret.getDockerUsername() + ":" + harborSecret.getDockerSecret();
            //Base64编码
            String baseAuth=Base64.getEncoder().encodeToString(auth.getBytes("utf-8"));
            //拼接秘钥
            JSONObject mainObject=new JSONObject();
            Map tmpMap=new HashMap();
            tmpMap.put("username",harborSecret.getDockerUsername());
            tmpMap.put("password",harborSecret.getDockerSecret());
            tmpMap.put("auth",baseAuth);
            mainObject.put(harborSecret.getDockerServer(),tmpMap);
            JSONObject authObject=new JSONObject();
            authObject.put("auths",mainObject);
            //生成最终的秘钥
            String finalAuth=Base64.getEncoder().encodeToString(authObject.toJSONString(0).getBytes("utf-8"));
            Secret secret = new Secret();
            Map data = new HashMap();
            data.put(".dockerconfigjson",">-"+finalAuth);
            secret.setData(data);
            ObjectMeta objectMeta=new ObjectMeta();
            objectMeta.setCreationTimestamp(new Date().toString());
            objectMeta.setName(harborSecret.getSecretName());
            objectMeta.setNamespace(harborSecret.getNamespace());
            List<ManagedFieldsEntry> managedFieldsEntryList=new ArrayList<>();
            ManagedFieldsEntry managedFieldsEntry=new ManagedFieldsEntry();
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
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public String createDeployment(K8SDeployment k8SDeployment) {
        try {
            Deployment deployment = new DeploymentBuilder()
                .withNewMetadata()
                .withName(k8SDeployment.getName())
                .endMetadata()
                .withNewSpec()
                .withReplicas(1)
                .withNewTemplate()
                .withNewMetadata()
                .addToLabels("app", k8SDeployment.getName())
                .endMetadata()
                .withNewSpec()
                .addNewContainer()
                .withName(k8SDeployment.getName())
                .withImage(k8SDeployment.getImageUrl())
                .addNewPort()
                .withContainerPort(k8SDeployment.getPort())
                .endPort()
                .endContainer()
                .endSpec()
                .endTemplate()
                .withNewSelector()
                .addToMatchLabels("app", k8SDeployment.getName())
                .endSelector()
                .endSpec()
                .build();
            log.info("Created deployment");
            deployment=kClient.apps().deployments().inNamespace(k8SDeployment.getNamespace()).create(deployment);
//            HasMetadata hasMetadata = kClient.resource(deployment).inNamespace(k8SDeployment.getNamespace()).waitUntilReady(30, TimeUnit.SECONDS);
            checkEventInfo(k8SDeployment.getNamespace(),k8SDeployment.getName());
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
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

        String serviceURL = kClient.services().inNamespace(k8SDeployment.getNamespace()).withName(service.getMetadata().getName()).getURL(k8SDeployment.getName());
        return serviceURL;
    }

    /**
     * 创建弹性伸缩
     */
    public void createOrReplaceHorizontalPodAutoscaler(K8SDeployment k8SDeployment) {
        log.info("*******开始创建弹性伸缩*****");
        HorizontalPodAutoscaler horizontalPodAutoscaler = horizontalPodAutoscalerVoToHorizontalPodAutoscaler(k8SDeployment);
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
     *     存储卷声明   metadata--  namespace+name    创建时间   now-creationTimestamp
     *     容量/状态    status--  capacity  phase
     *     名称         spec  -- volumnName
     */
    public Map findStorageInfo(){
         Map<String, List<K8SStorage>> result=new HashMap<>();
         StorageClassList storageClassList = kClient.storage().storageClasses().list();
         List<String> nameList= new ArrayList<>();
         storageClassList.getItems().forEach(f->{
             //获取名称
             nameList.add(f.getMetadata().getName());
         });
         PersistentVolumeClaimList persistentVolumeClaimList=kClient.persistentVolumeClaims().list();

         nameList.forEach(n->{
             List<K8SStorage> tmp=new ArrayList<>();
             persistentVolumeClaimList.getItems().forEach(p->{
                 if(n.equals(p.getSpec().getStorageClassName())){
                     K8SStorage k8SStorage=new K8SStorage();
                     k8SStorage.setPvClaim(p.getMetadata().getNamespace()+"/"+p.getMetadata().getName());
                     k8SStorage.setCreateTime(DateUtil.timeStampFormat(p.getMetadata().getCreationTimestamp()));
                     k8SStorage.setStatus(p.getStatus().getPhase());
                     if(k8SStorage.getStatus().equals("Pending")){
                         k8SStorage.setCapacity("0Gi");
                     }else {
                         k8SStorage.setCapacity(p.getStatus().getCapacity().get("storage").getAmount() + p.getStatus().getCapacity().get("storage").getFormat());
                     }
                     k8SStorage.setName(p.getSpec().getVolumeName());
                     tmp.add(k8SStorage);
                 }
             });
            result.put(n,tmp);
         });
         return result;

    }

    public String findStorageDescribeInfo(String name){
        StorageClass storageClass=kClient.storage().storageClasses().withName(name).get();
        Yaml yaml=new Yaml();
        return yaml.dump(storageClass);
    }


    public void checkEventInfo(String namespace,String name){
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

    public void findEventInfoByNamespace(String namespace){
        EventList event=kClient.events().inNamespace("default").list();
    }

    @Override
    public void rollImagesVersion(String namespace,String name) {
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
        Namespace namespace=new Namespace();
        //命名空间对象进行赋值
        ObjectMeta objectMeta=new ObjectMeta();
        objectMeta.setName(name);
        namespace.setMetadata(objectMeta);
        Namespace result = kClient.namespaces().create(namespace);
        return result;
    }

    @Override
    public Boolean deleteNamespace(String name) {
        //创建命名空间对象
        Namespace namespace=new Namespace();
        //命名空间对象进行赋值
        ObjectMeta objectMeta=new ObjectMeta();
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
