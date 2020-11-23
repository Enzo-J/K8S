package com.wenge.tbase.nacos.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 描述：
 *
 * @author Depp
 * @date 2019/9/18.
 */
public class HttpUtils {
    private static Logger log = LoggerFactory.getLogger(HttpUtils.class);

    /**
     * post发送json数据
     *
     * @param url
     * @param param
     * @return
     */
    public static String doPost(String url, JSONObject param) {
        HttpPost httpPost = null;
        String result = null;
        try {
            HttpClient client = new DefaultHttpClient();
            httpPost = new HttpPost(url);
            if (param != null) {
                StringEntity se = new StringEntity(param.toString(), "utf-8");
                httpPost.setEntity(se); // post方法中，加入json数据
                httpPost.setHeader("Content-Type", "application/json");
            }

            HttpResponse response = client.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, "utf-8");
                    log.info("调用采编接口返回信息: {}", result);
                }
            }

        } catch (Exception ex) {
            log.error("发送到接口出错", ex);
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("fileId", "5c188b70");
        jsonObject.put("createdBy", "5c81ca730cf2390a968c2fff");
        jsonObject.put("url", "http://img0.bdstatic.com/static/searchresult/img/logo-2X_32a8193.png");
        jsonObject.put("groupId", "5c7e3ddc0cf2b3ebdde3e5ef");
        jsonObject.put("parentId", "1");
        jsonObject.put("name", "aa.png");
        jsonObject.put("thumbnail", "http://img0.bdstatic.com/static/searchresult/img/logo-2X_32a8193.png");
        jsonObject.put("type", 1);
        String result = new HttpUtils().doPost("http://hongqi.wengetech.com:9080:port/sprint/rest/api/workflow/stories/media/0/createFile", jsonObject);
        System.out.println(result);
    }
}
