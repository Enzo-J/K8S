package com.wenge.tbase.k8s.constant;

public class K8SConstant {
    public static  final String API_VERSION="v1";
    public static final String DEPLOYMENT = "Deployment";
    public static final String DEFAULT_NAMESPACE = "default";
    public static final String NODE_PORT = "NodePort";
    public static final String IF_NOT_PRESENT = "IfNotPresent";
    public static final String KEY = "name";
    public static final int DEFAULT_REPLICAS = 1;
    public static final String SERVICE = "Service";
    public static final String HPA_APIVERSION = "autoscaling/v1";
    public static final String HPA_KIND = "HorizontalPodAutoscaler";
}
