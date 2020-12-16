package com.wenge.tbase.cicd.feignService;

import com.wenge.tbase.cicd.entity.vo.K8SDeployment;
import com.wenge.tbase.commons.result.ResultVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName: FeignK8SService
 * @Description: FeignK8SService
 * @Author: Wang XingPeng
 * @Date: 2020/12/15 14:20
 */
@Component
@FeignClient(name = "tbase-k8s-service")
public interface FeignK8SService {

    @PostMapping(value = "/Screen/createDeployment")
    ResultVO createDeployment(@RequestBody K8SDeployment k8SDeployment);

    @PostMapping("/Screen/createService")
    ResultVO createService(@RequestBody K8SDeployment k8SDeployment);
}
