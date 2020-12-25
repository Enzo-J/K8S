package com.wenge.tbase.kubeoperator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
//@EnableDiscoveryClient
//@EnableFeignClients
public class KubeoperatorServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(KubeoperatorServiceApplication.class, args);
	}

}
