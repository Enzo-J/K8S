package com.wenge.tbase.nacos.config;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

import com.alibaba.dubbo.common.utils.ConfigUtils;

/**
 * @author: xiuzhuo.jia
 * @Date: 2019/7/3 18:33
 * @Version 1.0
 * @Description
 */
public class ConstantConfig {
	private static InputStream profilepathOutJar = ConfigUtils.class.getClassLoader()
            .getResourceAsStream("config.properties");
    private static Properties props = new Properties();
    public static String nacosConfigAddress;
    public static String nacosInstanceAddress;
    public static String nacosOperatorAddress;
    public static  String nacosServiceAddress ;
    public static  String nacosNsAddress ;
    public static  String projectNum ;
    public static  String pageNo ;
    public static  String pageSize ;
    static {
        try {
            props.load(profilepathOutJar);
            nacosConfigAddress = props.getProperty("nacosConfigAddress");
            nacosInstanceAddress = props.getProperty("nacosInstanceAddress");
            nacosOperatorAddress = props.getProperty("nacosOperatorAddress");
            nacosServiceAddress = props.getProperty("nacosServiceAddress");
            nacosNsAddress = props.getProperty("nacosNsAddress");
            projectNum = props.getProperty("projectNum");
            pageNo = props.getProperty("pageNo");
            pageSize = props.getProperty("pageSize");
        } catch (FileNotFoundException e) {
            System.out.println("config.properties load failed");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
