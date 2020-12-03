package com.wenge.tbase.harbor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
public class HarBorServiceMain {
    public static void main(String[] args) {
        SpringApplication.run(HarBorServiceMain.class, args);
    }
}
