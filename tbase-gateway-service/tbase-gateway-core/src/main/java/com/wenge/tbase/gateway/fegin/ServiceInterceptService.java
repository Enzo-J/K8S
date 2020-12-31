package com.wenge.tbase.gateway.fegin;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wenge.tbase.gateway.entity.ServiceIntercept;


@Component
@FeignClient(name="tbase-gateway-web")
public interface ServiceInterceptService {	
	@RequestMapping(value = "/gateway/routes/serviceIntercept", method = RequestMethod.GET)
    List<ServiceIntercept> getAllReleaseUrl();    
}
