package com.wenge.tbase.cicd;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Build;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @ClassName: Test
 * @Description: Test
 * @Author: Wang XingPeng
 * @Date: 2020/11/21 10:57
 */
@SpringBootTest
public class Test {

    @Value("${server.port}")
    public String port;

    @Resource
    private JenkinsServer jenkinsServer;

    @org.junit.jupiter.api.Test
    public void test() {
        System.out.println("aaaa");
    }
}
