package com.wenge.tbase.nacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BootStrapService {
	
	public static void main(String[] args) {
        SpringApplication.run(BootStrapService.class, args);
	}
	  //spring通过value注解读取配置信息的
}
