package com.wenge.tbase.k8s.service.impl;

import com.wenge.tbase.k8s.bean.vo.*;
import com.wenge.tbase.k8s.constant.K8SConstant;
import com.wenge.tbase.k8s.service.K8SService;
import com.wenge.tbase.k8s.utils.CommonUtils;
import com.wenge.tbase.k8s.utils.DateUtil;
import com.wenge.tbase.k8s.utils.MyClient;
import io.fabric8.kubernetes.api.model.*;
import io.fabric8.kubernetes.api.model.autoscaling.v1.CrossVersionObjectReference;
import io.fabric8.kubernetes.api.model.autoscaling.v1.HorizontalPodAutoscaler;
import io.fabric8.kubernetes.api.model.autoscaling.v1.HorizontalPodAutoscalerSpec;
import io.fabric8.kubernetes.api.model.extensions.Deployment;
import io.fabric8.kubernetes.api.model.extensions.DeploymentSpec;
import io.fabric8.kubernetes.api.model.metrics.v1beta1.NodeMetrics;
import io.fabric8.kubernetes.api.model.storage.DoneableStorageClass;
import io.fabric8.kubernetes.api.model.storage.StorageClass;
import io.fabric8.kubernetes.api.model.storage.StorageClassList;
import io.fabric8.kubernetes.client.KubernetesClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

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
     * TODO:这个这样写不知道有没有问题
     * @param k8SConfigMap
     * @return
     */
    @Override
    public ConfigMap createConfigMap(K8SConfigMap k8SConfigMap) {
        ConfigMap configMap=new ConfigMap();
        //配置文件基本信息
        ObjectMeta objectMeta=new ObjectMeta();
        //所属命名空间
        objectMeta.setNamespace(k8SConfigMap.getNamespace());
        //ConfigMap名称
        objectMeta.setName(k8SConfigMap.getName());
        //label
        objectMeta.setLabels(k8SConfigMap.getLabels());
        //保存用户配置信息
        configMap.setData(k8SConfigMap.getData());
        //保存配置文件基本信息
        configMap.setMetadata(objectMeta);
        ConfigMap result=kClient.configMaps().create(configMap);
        return result;
    }

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

    public void createSecret(){
        Secret secret=new Secret();

    }



    @Override
    public Deployment createDeployment(K8SDeployment k8SDeployment) {
        log.info("****************开始创建Deployment**************");

        Deployment deployment = new Deployment();
        // 设置Deployment的metadata
        ObjectMeta meta = new ObjectMeta();
        meta.setName(k8SDeployment.getName());
        meta.setNamespace(k8SDeployment.getNamespace());
        Map<String, String> labelMap = new HashMap<>(16);
        labelMap.put(K8SConstant.KEY, k8SDeployment.getServiceName());
        meta.setLabels(labelMap);
        deployment.setMetadata(meta);

        // 设置Deployment的replicas和selector
        DeploymentSpec deploymentSpec = new DeploymentSpec();
        deploymentSpec.setReplicas(K8SConstant.DEFAULT_REPLICAS);
        LabelSelector labelSelector = new LabelSelector();
        labelSelector.setMatchLabels(labelMap);
        deploymentSpec.setSelector(labelSelector);

        // 设置pod模板下面的meta
        PodTemplateSpec podTemplateSpec = new PodTemplateSpec();
        ObjectMeta objectMeta = new ObjectMeta();
        objectMeta.setLabels(labelMap);
        podTemplateSpec.setMetadata(objectMeta);

        // 设置pod模板下面的spec
        PodSpec podSpec = new PodSpec();
        Container container = new Container();
        container.setName(k8SDeployment.getServiceName());
        container.setImagePullPolicy("Always");
        //  镜像地址
        container.setImage(k8SDeployment.getImageUrl());

        // 设置容器端口信息
        ContainerPort containerPort = new ContainerPort();
        containerPort.setContainerPort(k8SDeployment.getPort());
        List<ContainerPort> containerPorts = new ArrayList<>();
        containerPorts.add(containerPort);
        container.setPorts(containerPorts);

        // 申请资源
        ResourceRequirements resourceRequireents = new ResourceRequirements();
        Map<String, Quantity> requestSourceMap = new HashMap<>(16);
        // 格式化参数
        String cpu = String.valueOf(k8SDeployment.getCpu());
        String memory =k8SDeployment.getMemory() + "Gi";

        requestSourceMap.put("cpu", new Quantity(cpu));
        requestSourceMap.put("memory", new Quantity(memory));
        resourceRequireents.setRequests(requestSourceMap);
        resourceRequireents.setLimits(requestSourceMap);
        container.setResources(resourceRequireents);

        List<Container> containers = new ArrayList<>();
        containers.add(container);
        podSpec.setContainers(containers);

        podTemplateSpec.setSpec(podSpec);
        deploymentSpec.setTemplate(podTemplateSpec);
        deployment.setSpec(deploymentSpec);

        // 创建实例
        Deployment result=kClient.extensions().deployments().create(deployment);
        log.info("*******************Deployment创建完成****************");
        return result;
    }

    /**
     * 创建Service, 供外部访问使用
     */
    public void createService(K8SDeployment k8SDeployment) {
        log.info("*******开始创建服务*****, ServiceName={}", k8SDeployment.getServiceName());

        io.fabric8.kubernetes.api.model.Service service = new io.fabric8.kubernetes.api.model.Service();
        // 设置Service的meta
        ObjectMeta objectMeta = new ObjectMeta();
        objectMeta.setName(k8SDeployment.getServiceName());
        objectMeta.setNamespace(k8SDeployment.getNamespace());
        Map<String, String> labelMap = new HashMap<>(16);
        labelMap.put(K8SConstant.KEY, k8SDeployment.getServiceName());
        objectMeta.setLabels(labelMap);
        service.setMetadata(objectMeta);

        // 设置Service的spec
        ServiceSpec serviceSpec = new ServiceSpec();
        ServicePort servicePort = new ServicePort();

        servicePort.setPort(k8SDeployment.getPort());
        servicePort.setTargetPort(new IntOrString(k8SDeployment.getPort()));
        servicePort.setNodePort(k8SDeployment.getNodePort());

        List<ServicePort> ports = new ArrayList<>();
        ports.add(servicePort);
        serviceSpec.setPorts(ports);
        serviceSpec.setType(K8SConstant.NODE_PORT);
        serviceSpec.setSelector(labelMap);
        service.setSpec(serviceSpec);

        // 创建服务
        kClient.services().create(service);

        log.info("*******************创建服务完成******************");
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

    //TODO 是否要写成websocket实时推送
    private String getLogWithNamespaceAndPod(String namespace,String podName) {
        // 校验该podName是否存在
        Pod pod = kClient.pods().inNamespace(namespace).withName(podName).get();
        if (pod == null) {
            return "该pod不存在";
        }
        // 查询指定namespace和pod的日志信息
        String podLogs = kClient.pods().inNamespace(namespace).withName(podName).getLog();
        // 把换行符替换成页面的换行符
        if(!podLogs.equals("")) {
            return podLogs.replaceAll("\n", "<br>");
        }
        return null;
    }

    //删除service
    private void deleteService(String namespace,String serviceName) {
        try {
            kClient.extensions().deployments().inNamespace(namespace).withName(serviceName).delete();
//            kClient.customResourceDefinitions().create();
//            kClient.load().createOrReplace();
        } catch (KubernetesClientException e) {
            log.info("delete deployment failed, serviceName={}", serviceName);
        }
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


    public void findNamespaceDetailInfo(String namespace){
        Namespace namespaceInfo = kClient.namespaces().withName(namespace).get();
        System.out.println(namespaceInfo);
    }
}
