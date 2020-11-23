package com.wenge.tbase.commons.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ModuleEnum {
    MODULE_CICD("/tbase-cicd-service", "cicd","172.16.0.6:31372/am_cicd"),
    MODULE_K8S("/tbase-k8s-service", "k8s","172.16.0.6:31372/am_k8s");

    private String modulePath;

    private String moduleName;

    private String databaseUrl;


}
