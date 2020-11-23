package com.wenge.tbase.k8s.utils;

import io.fabric8.kubernetes.client.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import java.io.InputStream;


@Slf4j
public class K8sUtils {

    /**
     * 初始化客户端
     */
    private volatile static KubernetesClient client = null;

    public static KubernetesClient getKubernetesClient() {


        if(client==null) {
            synchronized (K8sUtils.class) {
                if(client==null) {
                    try {
                        InputStream inputStream = K8sUtils.class.getClassLoader().getResourceAsStream("k8s/admin.conf");
                        String adminConfData = IOUtils.toString(inputStream);
                        Config config = Config.fromKubeconfig(adminConfData);
                        client = new DefaultKubernetesClient(config);
                    } catch (Exception e) {
                        client = null;
                        log.error("初始化 K8sUtils 失败！", e);
                    }
                }
            }
        }
        return client;
    }


}