package com.wenge.tbase.harbor.utilscors;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @Date: 2019/9/5 15:33
 * @Description: http请求工具类
 */
@Slf4j
public class HttpUtils {

	public final static String HTTP = "http";
	public final static String HTTPS = "https";
	public final static String HTTP_URL_PREFIX = "http://";
	public final static String HTTPS_URL_PREFIX = "https://";


	/**
	 * 携带请求参数请求，超时时间为连接池默认时间
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static HttpResponse doGetWithParams(String url, Map<String, String> params)
			throws Exception {
		return doGet(url, "", Maps.newHashMap(), params, null);
	}

	/**
	 * 携带请求参数请求，如果timeout为null或者<=0,超时时间为Http连接池中指定的值
	 * @param url
	 * @param params
	 * @param timeout
	 * @return
	 * @throws Exception
	 */
	public static HttpResponse doGetWithParams(String url, Map<String, String> params, Integer timeout)
			throws Exception {
		return doGet(url, "", Maps.newHashMap(), params, timeout);
	}


	/**
	 * 携带请求参数以及header,如果timeout为null或者<=0,超时时间为Http连接池中指定的值
	 * @param url
	 * @param headers
	 * @param params
	 * @param timeout
	 * @return
	 * @throws Exception
	 */
	public static HttpResponse doGet(String url, Map<String, String> headers, Map<String, String> params, Integer timeout)
			throws Exception {
		return doGet(url, "", headers, params, timeout);
	}

	/**
	 * 携带请求参数以及header,如果timeout为null或者<=0,超时时间为Http连接池中指定的值
	 * @param host 网站域名
	 * @param uri uri部分
	 * @param headers header
	 * @param params 参数
	 * @return
	 * @throws Exception
	 */
	public static HttpResponse doGet(String host, String uri, Map<String, String> headers, Map<String, String> params, Integer timeout)
			throws Exception {
		HttpClient httpClient = HttpClientUtil.getHttpClient();
		HttpGet request = new HttpGet(buildUrl(host, uri, params));
		if (headers != null) {
			for (Map.Entry<String, String> e : headers.entrySet()) {
				request.addHeader(e.getKey(), e.getValue());
			}
		}
		//设置timeout，覆盖默认值
		setRequestConfig(request, timeout);
		return httpClient.execute(request);
	}


	/**
	 * 根据指定timeout请求，如果timeout为null或者<=0,超时时间为Http连接池中指定的值
	 * @param url
	 * @param timeout
	 * @return
	 * @throws Exception
	 */
	public static HttpResponse doGet(String url, Integer timeout)
			throws Exception {
		return doGet(url, "", Maps.newHashMap(), Maps.newHashMap(), timeout);
	}

	/**
	 * 超时时间为连接池默认时间
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static HttpResponse doGet(String url)
			throws Exception {
		return doGet(url, "", Maps.newHashMap(), Maps.newHashMap(), null);
	}

	/**
	 * GET请求，超时时间为Http连接池中指定的值。
	 * @param url
	 * @param headers
	 * @return
	 * @throws Exception
	 */
	public static HttpResponse doGetWithHeader(String url, Map<String, String> headers) throws Exception {
		return doGet(url, "", headers, Maps.newHashMap(), null);
	}

	/**
	 * GET请求，如果timeout为null或者<=0,超时时间为Http连接池中指定的值
	 * @param url
	 * @param headers
	 * @param timeout 超时时间
	 * @return
	 * @throws Exception
	 */
	public static HttpResponse doGetWithHeader(String url, Map<String, String> headers, Integer timeout) throws Exception {
		return doGet(url, "", headers, Maps.newHashMap(), timeout);
	}

	/**
	 * Post 请求
	 *
	 * @param host
	 * @param path
	 * @param headers
	 * @param querys
	 * @param body
	 * @return
	 * @throws Exception
	 */
	public static HttpResponse doPost(String host, String path, Map<String, String> headers, Map<String, String> querys,
									  String body) throws Exception {
		HttpClient httpClient = HttpClientUtil.getHttpClient();

		String url = buildUrl(host, path, querys);
		HttpPost request = new HttpPost(url);
		for (Map.Entry<String, String> e : headers.entrySet()) {
			request.addHeader(e.getKey(), e.getValue());
		}
		if (!StringUtils.isEmpty(body)) {
			request.setEntity(new StringEntity(body, "utf-8"));
		}
		long start = System.currentTimeMillis();
		HttpResponse response = httpClient.execute(request);
		log.info("请求url:{},耗时:{}ms", url, System.currentTimeMillis() - start);
		return response;
	}

	/**
	 * Post 请求
	 *
	 * @param host
	 * @param path
	 * @param headers
	 * @param querys
	 * @param body
	 * @return
	 * @throws Exception
	 */
	public static HttpResponse doPost(String host, String path, Map<String, String> headers, Map<String, String> querys,
									  byte[] body) throws Exception {
		HttpClient httpClient = HttpClientUtil.getHttpClient();
		HttpPost request = new HttpPost(buildUrl(host, path, querys));
		for (Map.Entry<String, String> e : headers.entrySet()) {
			request.addHeader(e.getKey(), e.getValue());
		}

		if (body != null) {
			request.setEntity(new ByteArrayEntity(body));
		}

		return httpClient.execute(request);
	}

	/**
	 * 配置请求超时时间
	 * @param base
	 * @param timeout 超时时间,s
	 */
	private static void setRequestConfig(HttpRequestBase base, Integer timeout) {
		if (timeout != null && timeout > 0) {
			int time = timeout * 1000;
			// 设置请求和传输超时时间
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(time).setConnectTimeout(time).build();
			base.setConfig(requestConfig);
		}
	}

	/**
	 * 构建get请求URL
	 * @param host
	 * @param path
	 * @param params
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static String buildUrl(String host, String path, Map<String, String> params)
			throws UnsupportedEncodingException {
		StringBuilder sbUrl = new StringBuilder();
		params = params == null ? Maps.newHashMap() : params;
		for (String key : params.keySet()) {
			String val = params.get(key);
			sbUrl.append(key);
			sbUrl.append("=");
			sbUrl.append(URLEncoder.encode(val, "utf-8"));
			sbUrl.append("&");
		}
		if (sbUrl.length() > 0) {
			sbUrl.deleteCharAt(sbUrl.length() - 1);
			sbUrl.insert(0, "?");
		}
		if (StringUtils.isNotBlank(path)) {
			sbUrl.insert(0, path);
		}
		sbUrl.insert(0, host);
		return sbUrl.toString();
	}

	/**
	 * 补充header信息
	 * @param headers
	 */
	public static void fillHeader(Map<String, String> headers) {

	}


	public static void main(String[] args) throws Exception {
//		try {
//			HttpResponse response = doGet("http://pili-live-hls.wdit.com.cn/wditlive/fs_ggpd.m3u8");
//			String str = EntityUtils.toString(response.getEntity());
//			System.out.println(str);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		Map<String, String> map = Maps.newHashMap();
		map.put("Authorization", "Basic " + Base64.encodeBase64String("admin:Szwg%2020".getBytes()));
//		HttpResponse response = HttpUtils.doGetWithHeader("https://172.16.0.11/api/v2.0/system/gc/schedule", map);

		HttpResponse response = HttpUtils.doGetWithHeader("https://172.16.0.11/api/v2.0/users/current/", map);

		System.out.println(EntityUtils.toString(response.getEntity()));
	}
}