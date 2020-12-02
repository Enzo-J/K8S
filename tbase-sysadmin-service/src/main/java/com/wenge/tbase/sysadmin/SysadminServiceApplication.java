package com.wenge.tbase.sysadmin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Author: sunlyhm
 * Date: 2020/11/27 16:33
 * FileName: SysadminServiceApplication
 * Description: 应用管理后台启动类
 */
@SpringBootApplication
@Slf4j
@EnableDiscoveryClient
@EnableFeignClients
@EnableSwagger2
public class SysadminServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(SysadminServiceApplication.class, args);
    }
}

