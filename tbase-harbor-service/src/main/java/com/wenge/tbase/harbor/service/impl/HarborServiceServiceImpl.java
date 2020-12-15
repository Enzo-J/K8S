package com.wenge.tbase.harbor.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xalan.internal.xsltc.compiler.Pattern;
import com.wenge.tbase.harbor.bean.Artifacts;
import com.wenge.tbase.harbor.bean.Projects;
import com.wenge.tbase.harbor.bean.Repositories;
import com.wenge.tbase.harbor.config.ConstantConfig;
import com.wenge.tbase.harbor.result.RestResult;
import com.wenge.tbase.harbor.result.WengeStatusEnum;
import com.wenge.tbase.harbor.service.HarborServiceService;
import com.wenge.tbase.harbor.utilsTest.JsoupUtils;
import com.wenge.tbase.harbor.utilscors.HttpClientUtil;
import com.wenge.tbase.harbor.utilscors.HttpUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class HarborServiceServiceImpl implements HarborServiceService {

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
	public RestResult<?> getProjectSummaryByIdService(Integer project_id) {
		try {
			Map<String,String> mapHeader = HttpClientUtil.getBasicHearder();
//			https://172.16.0.11/api/v2.0/projects/6/summary
			System.out.println(url+"/projects/"+project_id+"/summary");
			HttpResponse response = HttpUtils.doGetWithParams(url+"/projects/"+project_id+"/summary", mapHeader);
			String string = EntityUtils.toString(response.getEntity());
			Object jsonObject = JSON.parse(string);
			return RestResult.ok(jsonObject);
		} catch (Exception e) {
			System.err.println(e);
			return RestResult.error(WengeStatusEnum.SYSTEM_ERROR.getMsg());
		}
	}

	@Override
	public RestResult<?> getProjectStorageService() {
		try {
			Map<String,String> mapHeader = HttpClientUtil.getBasicHearder();
//			https://172.16.0.11/api/v2.0/systeminfo/volumes
			System.out.println(url+"/systeminfo/volumes");
			HttpResponse response = HttpUtils.doGetWithHeader(url+"/systeminfo/volumes", mapHeader);
			String string = EntityUtils.toString(response.getEntity());
			Object jsonObject = JSON.parse(string);
			return RestResult.ok(jsonObject);
		} catch (Exception e) {
			System.err.println(e);
			return RestResult.error(WengeStatusEnum.SYSTEM_ERROR.getMsg());
		}
	}

	@Override
	public RestResult<?> getProjectStatisticsService() {
		try {
			Map<String,String> mapHeader = HttpClientUtil.getBasicHearder();
//			https://172.16.0.11/api/v2.0/statistics
			System.out.println(url+"/statistics");
			HttpResponse response = HttpUtils.doGetWithHeader(url+"/statistics", mapHeader);
			String string = EntityUtils.toString(response.getEntity());
			Object jsonObject = JSON.parse(string);
			return RestResult.ok(jsonObject);
		} catch (Exception e) {
			System.err.println(e);
			return RestResult.error(WengeStatusEnum.SYSTEM_ERROR.getMsg());
		}
	}

	@Override
	public RestResult<?> getArtifactsListService(Artifacts artifacts) {
		try {
			Map<String,String> mapHeader = HttpClientUtil.getBasicHearder();
			String param = "page="+artifacts.getPage()+"&page_size="+artifacts.getPage_size();
			if(artifacts.getWith_label()!=null){
				param += "&with_label="+artifacts.getWith_label();
			}
			if(artifacts.getWith_tag()!=null){
				param += "&with_tag="+artifacts.getWith_tag();
			}
			if(artifacts.getWith_scan_overview()!=null){
				param += "&with_scan_overview="+artifacts.getWith_scan_overview();
			}
			String[] nameParam = artifacts.getRepository_name().split("/");
//			https://172.16.0.11/api/v2.0/projects/donson/repositories/kafka-eagle/artifacts?with_tag=true&with_scan_overview=true&with_label=true&page_size=15&page=1
			System.out.println((url+"/projects/"+nameParam[0]+"/repositories/"+nameParam[1]+"/artifacts?"+param));
			HttpResponse response = HttpUtils.doGetWithHeader(url+"/projects/"+nameParam[0]+"/repositories/"+nameParam[1]+"/artifacts?"+param, mapHeader,null);
			String string = EntityUtils.toString(response.getEntity());
			Object jsonObject = JSON.parse(string);
			return RestResult.ok(jsonObject);
		} catch (Exception e) {
			System.err.println(e);
			return RestResult.error(WengeStatusEnum.SYSTEM_ERROR.getMsg());
		}
	}

	@Override
	public RestResult<?> getRepositoriesListService(Repositories repositories) {
		try {
			Map<String,String> mapHeader = HttpClientUtil.getBasicHearder();
			String param = "page="+repositories.getPage()+"&page_size="+repositories.getPage_size();
			if(StringUtils.isNotBlank(repositories.getQ())){
				param += "&q=name%253D~"+repositories.getQ();
			}
//			https://172.16.0.11/api/v2.0/projects/donson/repositories?q=name%253D~jr&page_size=15&page=1
			System.out.println((url+"/projects/"+repositories.getProject_name()+"/repositories?"+param));
			HttpResponse response = HttpUtils.doGetWithHeader(url+"/projects/"+repositories.getProject_name()+"/repositories?"+param, mapHeader,null);
			String string = EntityUtils.toString(response.getEntity());
			Object jsonObject = JSON.parse(string);
			return RestResult.ok(jsonObject);
		} catch (Exception e) {
			System.err.println(e);
			return RestResult.error(WengeStatusEnum.SYSTEM_ERROR.getMsg());
		}
	}

	@Override
	public RestResult<?> getProjectsListService(Projects projects) {
		try {
			Map<String,String> mapHeader = HttpClientUtil.getBasicHearder();
			String param = "page="+projects.getPage()+"&page_size="+projects.getPage_size();
			if(StringUtils.isNotBlank(projects.getName())){
				param += "&name="+projects.getName();
			}
			if(projects.getIs_public()!=null){
				param += "&public="+projects.getIs_public();
			}
			System.out.println((url+"/projects?"+param));
			HttpResponse response = HttpUtils.doGetWithHeader(url+"/projects?"+param, mapHeader,null);
			String string = EntityUtils.toString(response.getEntity());
			Object jsonObject = JSON.parse(string);
			return RestResult.ok(jsonObject);
		} catch (Exception e) {
			System.err.println(e);
			return RestResult.error(WengeStatusEnum.SYSTEM_ERROR.getMsg());
		}
	}



	@Override
	public RestResult<?> addProjectsService(Projects projects) {
		try {
			Map<String,String> mapHeader = HttpClientUtil.getBasicHearder();
			JSONObject json = new JSONObject();
			JSONObject json1 = new JSONObject();
			json1.put("public",projects.getIs_public());
			json.put("metadata", json1);
			json.put("project_name",projects.getProject_name());
			json.put("storage_limit",projects.getStorage_limit());
			System.out.println(url+"/projects");
		HttpResponse response = null;
			System.out.println(JSON.toJSONString(projects));
			response = HttpUtils.doPost(host,path+"/projects", mapHeader,null,JSON.toJSONString(json));
//			HttpResponse response1 = HttpUtils.doPost(host,path+"/projects", mapHeader,null,json);
		String string = null;
			string = EntityUtils.toString(response.getEntity());
		Object jsonObject = JSON.parse(string);
			return RestResult.ok(jsonObject);
		} catch (Exception e) {
			System.err.println(e);
			return RestResult.error(WengeStatusEnum.SYSTEM_ERROR.getMsg());
		}
	}

	@Override
	public RestResult<?> updateProjectsService(Projects projects) {
		return null;
	}

}
