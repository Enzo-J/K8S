package com.wenge.tbase.auth.feign;

import cn.hutool.json.JSONObject;
import com.wenge.tbase.auth.entity.Result;
import com.wenge.tbase.auth.entity.XxlUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "uaa-server", url = "${sso.sso_uri}")
public interface AuthServiceFeign {
    /**
     * 根据code获取token
     *
     * @param body
     * @return
     */
    @PostMapping(value = "/oauth/token")
    JSONObject getTokenByCode(@RequestHeader("header") Map<String, String> header, @RequestBody MultiValueMap<String, String> body);

    /**
     * 获取用户信息
     *
     * @return
     */
    @GetMapping(value = "/user/getLoginUserInfo")
    Result<XxlUser> getUserInfoByRedis(@RequestParam("access_token") String token);
}
