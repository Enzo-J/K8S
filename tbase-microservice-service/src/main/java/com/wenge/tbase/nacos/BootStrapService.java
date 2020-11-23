package com.wenge.tbase.nacos;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@SpringBootApplication
public class BootStrapService {
	
	public static void main(String[] args) {
        SpringApplication.run(BootStrapService.class, args);
	}
	  //spring通过value注解读取配置信息的
}
