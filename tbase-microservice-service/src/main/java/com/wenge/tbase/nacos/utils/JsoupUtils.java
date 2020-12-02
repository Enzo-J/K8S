package com.wenge.tbase.nacos.utils;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

import com.alibaba.fastjson.JSONObject;

/**
 * 请求类
 */
public class JsoupUtils {
	/**
	 * get 请求
	 * @param url
	 * @return
	 */
	public static JSONObject get(String url) {
		JSONObject result = null;
		int count = 0;
		while (true) {
			if (count > 5) {
				break;
			}
			try {
				Response response = Jsoup.connect(url).maxBodySize(0).ignoreContentType(true).ignoreHttpErrors(true).timeout(50000).execute();
				int statusCode = response.statusCode();
				if (statusCode == 200) {
					String text = response.body();
					result = JSONObject.parseObject(text);
					break;
				}
				Thread.sleep(20 * 1000);
			} catch (Exception e) {
				System.out.println("第 " + (count + 1) + "次 url:" + url + "请求失败，即将重新请求！");
				e.printStackTrace();
			} finally {
				count++;
			}
		}
		return result;
	}

	/**
	 * get 请求
	 * 
	 * @param url
	 * @return
	 */
	public static JSONObject getJson(String url, Map<String, String> params) throws IOException {
		JSONObject result = null;
		if (params != null && params.size() > 0) {
			url = url + "?";
			for (Map.Entry<String, String> entry : params.entrySet()) {
				System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
				url = url + entry.getKey() + "=" + entry.getValue() + "&";
			}
			url = url.substring(0, url.length() - 1);
		}
		System.out.println("url:" + url);
		Response response = Jsoup.connect(url).ignoreContentType(true).timeout(60000).execute();
		String text = response.body();
		result = JSONObject.parseObject(text);
		return result;
	}

	/**
	 * get 请求
	 * 
	 * @param url
	 * @return
	 * @throws Exception 
	 */
	public static String get(String url, Map<String, String> params) throws Exception {
		String result = null;
			if (params != null && params.size() > 0) {
				url = url + "?";
				for (Map.Entry<String, String> entry : params.entrySet()) {
					System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
					url = url + entry.getKey() + "=" + entry.getValue() + "&";
				}
				url = url.substring(0, url.length() - 1);
			}
			System.out.println("url:" + url);
			Response response = Jsoup.connect(url).ignoreContentType(true).timeout(60000).execute();
			String text = response.body();
			result = StringEscapeUtils.unescapeJava(text);
		return result;
	}

	/**
	 * post 请求
	 * 
	 * @param url
	 * @return
	 */
	public static String post(String url, Map<String, String> params) {
		String result = null;
		try {
			Connection connection = Jsoup.connect(url).ignoreContentType(true).method(Method.POST);
			if (params != null && params.size() > 0) {
				for (Map.Entry<String, String> entry : params.entrySet()) {
					System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
				}
				connection.data(params);
			}
			Response response = connection.timeout(6000).execute();
			result = response.body();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static String post(String url, Map<String, String> params,String headerKey,String headerValue) {
		String result = null;
		try {
			Connection connection = Jsoup.connect(url).ignoreContentType(true).method(Method.POST).header(headerKey, headerValue);
			if (params != null && params.size() > 0) {
				for (Map.Entry<String, String> entry : params.entrySet()) {
					System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
				}
				connection.data(params);
			}
			Response response = connection.timeout(50000).execute();
			result = response.body();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	/*delete 请求
	 * 
	 * @param url
	 * @return
	 */
	public static String delete(String url, Map<String, String> params) {
		String result = null;
		try {
			Connection connection = Jsoup.connect(url).ignoreContentType(true).method(Method.DELETE);
			if (params != null && params.size() > 0) {
				for (Map.Entry<String, String> entry : params.entrySet()) {
					System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
				}
				connection.data(params);
			}
			Response response = connection.timeout(50000).execute();
			result = response.body();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/*delete 请求
	 * 
	 * @param url
	 * @return
	 */
	public static String put(String url, Map<String, String> params) {
		String result = null;
		try {
			Connection connection = Jsoup.connect(url).ignoreContentType(true).method(Method.PUT);
			if (params != null && params.size() > 0) {
				for (Map.Entry<String, String> entry : params.entrySet()) {
					System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
				}
				connection.data(params);
			}
			Response response = connection.timeout(50000).execute();
			result = response.body();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
