package com.wenge.tbase.cicd.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.http.NameValuePair;

/**
 * @ClassName: JenkinsNameValue
 * @Description: JenkinsNameValue
 * @Author: Wang XingPeng
 * @Date: 2020/11/27 11:13
 */
@Data
@AllArgsConstructor
public class JenkinsNameValue implements NameValuePair {

    private String json;

    @Override
    public String getName() {
        return "json";
    }

    @Override
    public String getValue() {
        return json;
    }
}
