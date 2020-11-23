package com.wenge.tbase.k8s.service.impl;

import com.wenge.tbase.k8s.entity.K8SNameSpace;
import com.wenge.tbase.k8s.entity.K8SNode;
import com.wenge.tbase.k8s.service.K8SService;
import com.wenge.tbase.k8s.utils.CommonUtils;
import com.wenge.tbase.k8s.utils.K8sUtils;
import io.fabric8.kubernetes.api.model.Namespace;
import io.fabric8.kubernetes.api.model.NamespaceList;
import io.fabric8.kubernetes.api.model.Node;
import io.fabric8.kubernetes.api.model.Quantity;
import io.fabric8.kubernetes.api.model.metrics.v1beta1.NodeMetrics;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class K8SServiceImpl implements K8SService {
    KubernetesClient kClient=K8sUtils.getKubernetesClient();
    @Override
    public List getAllNameSpace() {
        NamespaceList namespaceList= kClient.namespaces().list();
        List<Namespace> namespaces=namespaceList.getItems();
        List<K8SNameSpace> k8SNameSpaces=new ArrayList<>();
        namespaces.forEach(namespace -> {
            K8SNameSpace ns=new K8SNameSpace();
            ns.setName(namespace.getMetadata().getName());
            ns.setStatus(namespace.getStatus().getPhase().equals("Active")?true:false);
            k8SNameSpaces.add(ns);
        });
        return k8SNameSpaces;
    }

    @Override
    public Map getNodeInfo() {
        //节点信息
        List<Node>nodes=kClient.nodes().list().getItems();
        List<K8SNode> k8SNodes=new ArrayList<>();
        Map result=new HashMap();
        Double totalCPU=new Double(0);
        Double totalMemory=new Double(0);
        Double totalUsCPU=new Double(0);
        Double totalUsMemory=new Double(0);
        Integer isActive=new Integer(0);
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


}
