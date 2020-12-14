package com.wenge.tbase.harbor.service.impl;

import com.alibaba.fastjson.JSON;
import com.wenge.tbase.harbor.bean.Users;
import com.wenge.tbase.harbor.result.RestResult;
import com.wenge.tbase.harbor.result.WengeStatusEnum;
import com.wenge.tbase.harbor.service.HarborUsersService;
import com.wenge.tbase.harbor.utilscors.HttpClientUtil;
import com.wenge.tbase.harbor.utilscors.HttpUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dell
 */
@Service
public class HarborUsersServiceImpl implements HarborUsersService {

    @Value("${harbor.host}")
    private String host;
    @Value("${harbor.path}")
    private String path;
    @Value("${harbor.url}")
    private String url;
    @Value("${harbor.encoding}")
    private String encoding;
    @Value("${harbor.timeout}")
    private Long timeout;




    @Override
    public RestResult<?> addUsersService(Users users) {
        String result = null;
        /**
         * 邮箱和用户名已存在不允许新创建用户
         *
         */
        try {
            Map<String,String> mapHeader = HttpClientUtil.getBasicHearder();
            HttpResponse response = HttpUtils.doPost(host,path+"/users", mapHeader,null,JSON.toJSONString(users));
            System.out.println(EntityUtils.toString(response.getEntity()));
            return RestResult.ok(result);
        } catch (Exception e) {
            System.err.println(e);
            return RestResult.error(WengeStatusEnum.SYSTEM_ERROR.getMsg());
        }
    }

    @Override
    public RestResult<?> updateUsersService(Users users) {
        return null;
    }

    @Override
    public RestResult<?> deleteUsersService(Users users) {
        return null;
    }

    @Override
    public RestResult<?> resetPasswordService(Users users) {
        return null;
    }

    @Override
    public RestResult<?> getUsersListService(Users users) {
        try {
            Map<String,String> mapHeader = HttpClientUtil.getBasicHearder();
            String param = "page="+users.getPage()+"&page_size="+users.getPage_size();
            if(StringUtils.isNotBlank(users.getUsername())){
                param += "&username="+users.getUsername();
            }
            if(users.getRole_id()!=null){//不支持角色检索
                param += "&role_id="+users.getRole_id();
            }
            System.out.println((url+"/users?"+param));
            HttpResponse response = HttpUtils.doGetWithHeader(url+"/users?"+param, mapHeader,null);
            String string = EntityUtils.toString(response.getEntity());
            Object jsonObject = JSON.parse(string);
            return RestResult.ok(jsonObject);
        } catch (Exception e) {
            System.err.println(e);
            return RestResult.error(WengeStatusEnum.SYSTEM_ERROR.getMsg());
        }
    }

    @Override
    public RestResult<?> getUsersByIdService(Users users) {
        return null;
    }

}
