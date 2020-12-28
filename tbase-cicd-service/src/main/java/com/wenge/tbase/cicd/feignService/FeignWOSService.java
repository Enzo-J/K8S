package com.wenge.tbase.cicd.feignService;

import cn.hutool.json.JSONObject;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName: FeignWOSService
 * @Description: FeignWOSService
 * @Author: Wang XingPeng
 * @Date: 2020/12/24 17:12
 */
@Component
@FeignClient(name = "tbase-wos-service")
public interface FeignWOSService {

    @PostMapping(value = "/wos/api/v1/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    JSONObject upload(@RequestPart("file") MultipartFile file, @RequestHeader("STORE_BUCKET_KEY") String bucketName);
}
