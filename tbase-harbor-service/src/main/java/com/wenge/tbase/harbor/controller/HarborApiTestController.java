package com.wenge.tbase.harbor.controller;

import com.wenge.tbase.harbor.bean.ResultObject;
import com.wenge.tbase.harbor.utilsTest.HttpClientUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;


@Api("Harbor服务相关测试接口")
@RestController
@RequestMapping("/harbor/v1/testApi")
public class HarborApiTestController {

    @Autowired
    private HttpClientUtils httpClientUtils;

    @GetMapping("/selectTest")
    public ResultObject selectTest() throws Exception {
        String uri = "/projects/1/scanner";  //
        return httpClientUtils.get(uri, new HashMap<>());
    }
}