package com.wenge.tbase.k8s.utils;

import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class MyClient extends DefaultKubernetesClient {
    private static Config config = null;
    static {
        if(config==null){
            try {
                InputStream inputStream = K8sUtils.class.getClassLoader().getResourceAsStream("k8s/admin.conf");
                String adminConfData = IOUtils.toString(inputStream);
                config = Config.fromKubeconfig(adminConfData);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public MyClient() {
        super(config);
    }
}
