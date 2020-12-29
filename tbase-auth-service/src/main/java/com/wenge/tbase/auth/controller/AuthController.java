package com.wenge.tbase.auth.controller;

import cn.hutool.core.codec.Base64;
import cn.hutool.json.JSONObject;
import com.wenge.tbase.auth.entity.Result;
import com.wenge.tbase.auth.entity.XxlUser;
import com.wenge.tbase.auth.feign.AuthServiceFeign;
import com.wenge.tbase.commons.result.ResultCode;
import com.wenge.tbase.commons.result.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "系统接口")
@RestController
@RequestMapping("/auth")
@Validated
@Slf4j
public class AuthController {
    @Value("${sso.client-id}")
    private String clientId;

    @Value("${sso.client-secret}")
    private String clientSecret;

    @Value("${sso.redirect_uri}")
    private String redirect;
    @Resource
    private AuthServiceFeign authServiceFeign;

    @GetMapping("/token")
    @ApiOperation("获取token")
    public ResultVO tokenInfo(@RequestParam String code) throws Exception {
        //获取token
        byte[] authorization = (clientId + ":" + clientSecret).getBytes("UTF-8");
        String base64Auth = Base64.encode(authorization);
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", MediaType.MULTIPART_FORM_DATA_VALUE);
        headers.put("Authorization", "Basic " + base64Auth);
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("grant_type", "authorization_code");
        param.add("code", code);
        param.add("scope", "all");
        param.add("redirect_uri", redirect);
        JSONObject tokenMap = authServiceFeign.getTokenByCode(headers, param);
        String accessToken = tokenMap.getStr("access_token");
        log.info("============>access_token:{}", accessToken);
        if (accessToken==null) {
            return new ResultVO(ResultCode.FAILED,"获取token失败");
        }
        Map result = new HashMap(2);
        result.put("token", accessToken);
        result.put("info", getUserInfo(accessToken));
        return new ResultVO(result);
    }

    /**
     * 获取用户信息
     */
    @ApiOperation("获取用户信息")
    @GetMapping("/user")
    public ResultVO getUserInfo(String accessToken) {
        Result<XxlUser> result = authServiceFeign.getUserInfoByRedis(accessToken);
        XxlUser xxlUser = result.getData();
        return  new ResultVO(xxlUser);
    }
}
