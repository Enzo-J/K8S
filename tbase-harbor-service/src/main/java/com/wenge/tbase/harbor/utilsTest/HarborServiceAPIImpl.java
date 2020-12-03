package com.wenge.tbase.harbor.utilsTest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class HarborServiceAPIImpl{

    public static void main(String[] args) {
        HttpHeaders headers = new HttpHeaders();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("principal", "admin");
        params.add("password", "Szwg%2020");
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = restTemplate.postForEntity("http://172.16.0.11/c/login",requestEntity, String.class).getHeaders();
//        HttpHeaders httpHeaders = SSLRestTemplateUtil.getInstance().postForEntity("https://192.168.101.90/login", requestEntity, String.class).getHeaders();
    }
}
