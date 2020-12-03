package com.wenge.tbase.harbor.config;

import com.alibaba.dubbo.common.utils.ConfigUtils;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;


public class ConstantConfig {
    private static InputStream profilepathOutJar = ConfigUtils.class.getClassLoader()
            .getResourceAsStream("config.properties");
    private static Properties props = new Properties();
    public static  String harborServiceAddress;
    static {
        try {
            props.load(profilepathOutJar);
            harborServiceAddress = props.getProperty("harborServiceAddress");
        } catch (FileNotFoundException e) {
            System.out.println("config.properties load failed");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
