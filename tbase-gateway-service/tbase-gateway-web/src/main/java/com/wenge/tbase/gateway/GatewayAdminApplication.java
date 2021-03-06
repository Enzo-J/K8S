package com.wenge.tbase.gateway;

import java.time.LocalDateTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.config.GatewayClassPathWarningAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication(exclude = GatewayClassPathWarningAutoConfiguration.class)
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableMethodCache(basePackages = "com.wenge.tbase.gateway")
@EnableCreateCacheAnnotation
@ComponentScan(basePackages={"com.alicp.jetcache.autoconfigure","com.wenge.tbase.gateway"})
@EnableFeignClients
@Slf4j
public class GatewayAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayAdminApplication.class, args);        
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
                "      w:::::w:::::w   w:::::w:::::w   e::::::eeeeeeeeeee    n::::n    n::::ng:::::g     g:::::g e::::::eeeeeeeeeee  \n" +
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
