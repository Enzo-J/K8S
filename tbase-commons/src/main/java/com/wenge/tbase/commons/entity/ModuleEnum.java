package com.wenge.tbase.commons.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ModuleEnum {
    MODULE_CICD("/tbase-cicd-service", "cicd","172.16.0.6:31372/am_cicd"),
    MODULE_K8S("/tbase-k8s-service", "k8s","172.16.0.6:31372/am_k8s"),
    MODULE_COMMONS("/tbase-commons", "commons","172.16.0.6:31372/app-manage-platform"),
    MODULE_MICRO("/tbase-microservice-service", "nacos","172.16.0.6:31372/am_microservice");

    private String modulePath;

    private String moduleName;

    private String databaseUrl;


}
