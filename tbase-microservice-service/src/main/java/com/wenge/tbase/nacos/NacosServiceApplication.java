package com.wenge.tbase.nacos;

import java.time.LocalDateTime;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import lombok.extern.slf4j.Slf4j;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Slf4j
@MapperScan("com.wenge.tbase.nacos.mapper")
@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2
public class NacosServiceApplication {
    //spring通过value注解读取配置信息的
    public static void main(String[] args) {
        SpringApplication.run(NacosServiceApplication.class, args);
        //spring通过value注解读取配置信息的
        log.info("                                                                                                             \n" +
                "                                                                                                                    \n" +
                "                                                                                                                    \n" +
                "                                                                                                                    \n" +
                "                                                                                                                    \n" +
                "                                                                                                                    \n" +
                "wwwwwww           wwwww           wwwwwww eeeeeeeeeeee    nnnn  nnnnnnnn       ggggggggg   ggggg    eeeeeeeeeeee    \n" +
                " w:::::w         w:::::w         w:::::wee::::::::::::ee  n:::nn::::::::nn    g:::::::::ggg::::g  ee::::::::::::ee  \n" +
                "  w:::::w       w:::::::w       w:::::we::::::eeeee:::::een::::::::::::::nn  g:::::::::::::::::g e::::::eeeee:::::ee\n" +
                "   w:::::w     w:::::::::w     w:::::we::::::e     e:::::enn:::::::::::::::ng::::::ggggg::::::gge::::::e     e:::::e\n" +
                "    w:::::w   w:::::w:::::w   w:::::w e:::::::eeeee::::::e  n:::::nnnn:::::ng:::::g     g:::::g e:::::::eeeee::::::e\n" +
                "     w:::::w w:::::w w:::::w w:::::w  e:::::::::::::::::e   n::::n    n::::ng:::::g     g:::::g e:::::::::::::::::e \n" +
                "      w:::::w:::::w   w:::::w:::::w   e::::::eeeeeeeeeee    n::::n    n::::ng:::::g     g	:::::g e::::::eeeeeeeeeee  \n" +
                "       w:::::::::w     w:::::::::w    e:::::::e             n::::n    n::::ng::::::g    g:::::g e:::::::e           \n" +
                "        w:::::::w       w:::::::w     e::::::::e            n::::n    n::::ng:::::::ggggg:::::g e::::::::e          \n" +
                "         w:::::w         w:::::w       e::::::::eeeeeeee    n::::n    n::::n g::::::::::::::::g  e::::::::eeeeeeee  \n" +
                "          w:::w           w:::w         ee:::::::::::::e    n::::n    n::::n  gg::::::::::::::g   ee:::::::::::::e  \n" +
                "           www             www            eeeeeeeeeeeeee    nnnnnn    nnnnnn    gggggggg::::::g     eeeeeeeeeeeeee  \n" +
                "                                                                                        g:::::g                     \n" +
                "                                                                            gggggg      g:::::g                     \n" +
                "                                                                            g:::::gg   gg:::::g                     \n" +
                "                                                                             g::::::ggg:::::::g                     \n" +
                "                                                                              gg:::::::::::::g                      \n" +
                "                                                                                ggg::::::ggg                        \n" +
                "                                                                                   gggggg                           ");

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                log.info("======================================================");
                log.error("检测到程序异常停止======" + LocalDateTime.now().toString());
                log.info("======================================================");
            }
        });
    }

}
