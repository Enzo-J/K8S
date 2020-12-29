package com.wenge.tbase.kubeoperator.config;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

import com.alibaba.dubbo.common.utils.ConfigUtils;

public class ConstantConfig {
	private static InputStream profilepathOutJar = ConfigUtils.class.getClassLoader()
            .getResourceAsStream("config.properties");
    private static Properties props = new Properties();
    public static  String kubeAuthAddr;
    public static  String kubeClustersAddr;
    public static  String kubeHostsAddr;
    public static  String kubeProjectsAddr;
    public static  String kubeUsersAddr;
    
    static {
        try {
            props.load(profilepathOutJar);
            kubeAuthAddr = props.getProperty("kubeAuthAddr");
            kubeClustersAddr = props.getProperty("kubeClustersAddr");
            kubeHostsAddr = props.getProperty("kubeHostsAddr");
            kubeProjectsAddr = props.getProperty("kubeProjectsAddr");
            kubeUsersAddr = props.getProperty("kubeUsersAddr");
        } catch (FileNotFoundException e) {
            System.out.println("config.properties load failed");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
