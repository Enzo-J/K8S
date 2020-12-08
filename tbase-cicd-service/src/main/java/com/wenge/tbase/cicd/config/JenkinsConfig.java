package com.wenge.tbase.cicd.config;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.client.JenkinsHttpClient;
import com.wenge.tbase.cicd.entity.Jenkins;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @ClassName: JenkinsConfig
 * @Description: JenkinsConfig
 * @Author: Wang XingPeng
 * @Date: 2020/11/21 10:39
 */
@Configuration
public class JenkinsConfig {

    /**
     * 注入jenkinsHttpClient对象
     */
    @Bean(name = "jenkinsHttpClient")
    public JenkinsHttpClient getJenkinsHttpClient(Jenkins jenkins) throws URISyntaxException {
        return new JenkinsHttpClient(
                new URI(jenkins.getUri()),
                jenkins.getUsername(),
                jenkins.getPassword());
    }

    /**
     * 注入jenkinsServer对象
     */
    @Bean(name = "jenkinsServer")
    public JenkinsServer getJenkinsServer(JenkinsHttpClient jenkinsHttpClient) {
        return new JenkinsServer(jenkinsHttpClient);
    }
}
