//package com.wenge.tbase.harbor.utilsTest;
//
//import com.alibaba.fastjson.JSONObject;
//import com.sun.net.httpserver.Headers;
//import com.wenge.tbase.harbor.bean.LoginUserVo;
//import com.wenge.tbase.harbor.bean.ResultObject;
//import org.apache.commons.lang.StringUtils;
//import org.apache.http.HttpResponse;
//import org.apache.http.HttpStatus;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.config.RequestConfig;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.*;
//import org.apache.http.client.utils.URIBuilder;
//import org.apache.http.conn.ssl.NoopHostnameVerifier;
//import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.impl.client.LaxRedirectStrategy;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.ssl.SSLContexts;
//import org.apache.http.ssl.TrustStrategy;
//import org.apache.http.util.EntityUtils;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//
//import javax.net.ssl.SSLContext;
//import javax.net.ssl.TrustManager;
//import javax.net.ssl.X509TrustManager;
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.nio.charset.StandardCharsets;
//import java.security.KeyManagementException;
//import java.security.KeyStoreException;
//import java.security.NoSuchAlgorithmException;
//import java.security.cert.X509Certificate;
//import java.util.*;
//
///**
// * httpClient工具类
// */
//@Component
//public class HttpClientUtils {
//
//    //编码格式
//    @Value("${harbor.encoding}")
//    private String ENCODING;
//
//    //设置连接超时，单位毫秒
//    @Value("${harbor.connect_timeout}")
//    private int CONNECT_TIMEOUT;
//
//    //设置读取数据超时时间，单位毫秒
//    @Value("${harbor.socket_timeout}")
//    private int SOCKET_TIMEOUT;
//
//    //Harbor url
//    @Value("${harbor.url}")
//    private String HOST_PORT;
//
//    //Harbor的登录地址
//    @Value("${harbor.login_address}")
//    private String HARBOR_LOGIN_ADDRESS;
//
//    //请求头 用户代理
//    @Value("${harbor.user_agent}")
//    private String USER_AGENT;
//
//    //Harbor登录用户名
//    @Value("${harbor.username}")
//    private String HARBOR_USERNAME;
//
//    //Harbor登录密码
//    @Value("${harbor.password}")
//    private String HARBOR_PASSWORD;
//
//
//    /**
//     * Authorization Basic认证
//     *
//     * @return
//     */
//    private static Headers createHeaders(String username, String password) {
//        return new Headers() {
//            {
//                String auth = username + ":" + password;
//                String authHeader = "Basic " + Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.US_ASCII));
//                set("Authorization", authHeader);
//            }
//        };
//    }
//    /**
//     * 登录操作
//     *
//     * @throws Exception
//     * @return
//     */
//    public CloseableHttpClient login() throws Exception {
//        CloseableHttpClient httpClient = HttpClients.createDefault();
////        HttpClientBuilder httpClient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy());
//        //忽略SSL请求认证
//        httpClient = (CloseableHttpClient) wrapClient((HttpClient) httpClient);
//        HttpPost httpPost = new HttpPost(HARBOR_LOGIN_ADDRESS);
//        httpPost.setHeader("User-Agent", USER_AGENT);
//        //登录表单提交的数据
//        List<NameValuePair> list = new ArrayList<NameValuePair>();
//        list.add(new BasicNameValuePair("principal", HARBOR_USERNAME));
//        list.add(new BasicNameValuePair("password", HARBOR_PASSWORD));
//        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list);
//        httpPost.setEntity(entity);
//        //相当于按下登录按钮
//        CloseableHttpResponse response = (CloseableHttpResponse) ((HttpClient) httpClient).execute(httpPost);
//        return httpClient;
//    }
//
//
////    @Test
//    public HttpClient givenRedirectingPOST_whenConsumingUrlWhichRedirectsWithPOST_thenRedirected()
//            throws ClientProtocolException, IOException {
//        HttpClient httpClient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();
//        httpClient = (CloseableHttpClient) wrapClient((HttpClient) httpClient);
//        HttpPost httpPost = new HttpPost("http://172.16.0.11/harbor/sign-in?signout=true&redirect_url=%2Fharbor%2Fprojects");
//        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.66 Safari/537.36");
//        //登录表单提交的数据
//        List<NameValuePair> list = new ArrayList<NameValuePair>();
//        list.add(new BasicNameValuePair("principal", "admin"));
//        list.add(new BasicNameValuePair("password", "Szwg%2020"));
//        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list);
//        httpPost.setEntity(entity);
//
//        HttpResponse response = httpClient.execute(httpPost);
////        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
//        return httpClient;
//    }
//
//    /**
//     * 释放资源
//     *
//     * @param response
//     * @param httpClient
//     * @throws Exception
//     */
//    public void release(CloseableHttpResponse response, CloseableHttpClient httpClient) throws Exception {
//        if (response != null) {
//            response.close();
//        }
//        if (httpClient != null) {
//            httpClient.close();
//        }
//    }
//
//
//    /**
//     * 执行请求并封装结果
//     *
//     * @param httpResponse
//     * @param httpClient
//     * @param httpMethod
//     * @return
//     * @throws Exception
//     */
//    public ResultObject getHttpClientResult(CloseableHttpResponse httpResponse, CloseableHttpClient httpClient, HttpRequestBase httpMethod) throws Exception {
//        //执行请求
//        httpResponse = httpClient.execute(httpMethod);
//        Map<String, Object> map = new HashMap<>();
//        //封装结果
//        if (httpResponse != null && httpResponse.getStatusLine() != null) {
//            String records = EntityUtils.toString(httpResponse.getEntity(), ENCODING);
//            if (StringUtils.isNotBlank(records)) {
//                map.put("records",records);
//            }
//            return new ResultObject(httpResponse.getStatusLine().getStatusCode(), "", map);
//        }
//        return new ResultObject(HttpStatus.SC_INTERNAL_SERVER_ERROR, "请求失败", map);
//    }
//
//    /**
//     * 发送get请求
//     *
//     * @param uri
//     * @param params
//     * @throws Exception
//     */
//    public ResultObject get(String uri, Map<String, String> params) throws Exception {
////        CloseableHttpClient httpClient = (CloseableHttpClient)login();
////        CloseableHttpClient httpClient =HttpClients.createDefault();
//        CloseableHttpClient httpClient = (CloseableHttpClient) givenRedirectingPOST_whenConsumingUrlWhichRedirectsWithPOST_thenRedirected();
////        //忽略SSL请求认证
////        httpClient = (CloseableHttpClient) wrapClient(httpClient);
////        HttpPost httpPost = new HttpPost(HARBOR_LOGIN_ADDRESS);
////        httpPost.setHeader("User-Agent", USER_AGENT);
////        //登录表单提交的数据
////        List<NameValuePair> list = new ArrayList<NameValuePair>();
////        list.add(new BasicNameValuePair("principal", HARBOR_USERNAME));
////        list.add(new BasicNameValuePair("password", HARBOR_PASSWORD));
////        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list);
//////        entity.setChunked(true);
////        httpPost.setEntity(entity);
//        URIBuilder uriBuilder = new URIBuilder(HOST_PORT + uri);
//        if (params.size() > 0) {
//            for (Map.Entry<String, String> m : params.entrySet()) {
//                uriBuilder.setParameter(m.getKey(), m.getValue());
//            }
//        }
//        HttpGet get = new HttpGet(uriBuilder.build());
//        RequestConfig requestCongfig = RequestConfig.custom().setConnectionRequestTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
//        get.setConfig(requestCongfig);
//        CloseableHttpResponse response = null;
//        ResultObject resultObject = getHttpClientResult(response, httpClient, get);
//        release(response, httpClient);
//        return resultObject;
//    }
//
//
//    // 该地址有301转发，为了不影响其他的地方，这里使用自定义的restTemplate。
//// 实现请求自动转发需要创建HttpClient时设置重定向策略。
//// 生产环境使用系统代理，由于使用的是HttpClient工厂 代理也需要使用HttpClient的代理模式
////    @Test
//    public void hello() throws UnsupportedEncodingException {
//        RestTemplate restTemplate = new RestTemplate();
//        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
//        HttpClient httpClient;
////        if (AntdConstant.ENV_PROD.equals(profiles)) {
////            HttpHost proxy = new HttpHost(antdProperties.getProxy().getIp(), antdProperties.getProxy().getPort());
////            httpClient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).setProxy(proxy).build();
////        } else {
//            httpClient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();
////        }
//        httpClient = (CloseableHttpClient) wrapClient(httpClient);
//        factory.setHttpClient(httpClient);
//        restTemplate.setRequestFactory(factory);
//
////        HttpPost httpPost = new HttpPost();
////        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.66 Safari/537.36");
////        //登录表单提交的数据
////        List<NameValuePair> list = new ArrayList<NameValuePair>();
////        list.add(new BasicNameValuePair("principal", "admin"));
////        list.add(new BasicNameValuePair("password", "Szwg%2020"));
////        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list);
////        httpPost.setEntity(entity);
//        LoginUserVo loginUserVo = new LoginUserVo();
//        loginUserVo.setPrincipal("admin");
//        loginUserVo.setPassword("Szwg%2020");
//
////        restTemplate.execute("http://172.16.0.11/c/login", HttpMethod.POST, (RequestCallback) httpPost, null);
////        ResponseEntity<List<Repositories>> resp = restTemplate.getForEntity("http://172.16.0.11/c/login", HttpMethod.POST, httpPost);
//        Object result = restTemplate.postForEntity("http://172.16.0.11/c/login", loginUserVo, LoginUserVo.class);
//
//
//        System.err.println("===="+result);
//    }
//
//
//    /**
//     * 发送 post请求
//     *
//     * @param uri
//     * @param params
//     * @throws Exception
//     */
//    public ResultObject post(String uri, Map<String, String> params) throws Exception {
//        CloseableHttpClient httpClient = login();
//        HttpPost post = new HttpPost(HOST_PORT + uri);
//        RequestConfig requestCongfig = RequestConfig.custom().setConnectionRequestTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
//        post.setConfig(requestCongfig);
//        String json = JSONObject.toJSONString(params);
//        //post  Request Payload
//        StringEntity stringEntity = new StringEntity(json, "application/json", ENCODING);
//        post.setEntity(stringEntity);
//        CloseableHttpResponse response = null;
//        ResultObject resultObject = getHttpClientResult(response, httpClient, post);
//        release(response, httpClient);
//        return resultObject;
//    }
//
//    /**
//     * 发送delete请求
//     *
//     * @param uri
//     * @param params
//     * @throws Exception
//     */
//    public ResultObject delete(String uri, Map<String, String> params) throws Exception {
//        CloseableHttpClient httpClient = login();
//        URIBuilder uriBuilder = new URIBuilder(HOST_PORT + uri);
//        if (params.size() > 0) {
//            for (Map.Entry<String, String> m : params.entrySet()) {
//                uriBuilder.setParameter(m.getKey(), m.getValue());
//            }
//        }
//        HttpDelete delete = new HttpDelete(uriBuilder.build());
//        RequestConfig requestCongfig = RequestConfig.custom().setConnectionRequestTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
//        delete.setConfig(requestCongfig);
//        CloseableHttpResponse response = null;
//        ResultObject resultObject = getHttpClientResult(response, httpClient, delete);
//        release(response, httpClient);
//        return resultObject;
//
//    }
//
//    /**
//     * 发送put请求
//     *
//     * @param uri
//     * @param params
//     * @throws Exception
//     */
//    public ResultObject put(String uri, Map<String, String> params) throws Exception {
//        CloseableHttpClient httpClient = login();
//        URIBuilder uriBuilder = new URIBuilder(HOST_PORT + uri);
//        if (params.size() > 0) {
//            for (Map.Entry<String, String> m : params.entrySet()) {
//                uriBuilder.setParameter(m.getKey(), m.getValue());
//            }
//        }
//        HttpPut put = new HttpPut(uriBuilder.build());
//        RequestConfig requestCongfig = RequestConfig.custom().setConnectionRequestTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
//        put.setConfig(requestCongfig);
//        CloseableHttpResponse response = null;
//        ResultObject resultObject = getHttpClientResult(response, httpClient, put);
//        release(response, httpClient);
//        return resultObject;
//    }
//
//
//    private HttpComponentsClientHttpRequestFactory generateHttpRequestFactory()
//            throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException {
//        TrustStrategy acceptingTrustStrategy = (x509Certificates, authType) -> true;
//        SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
//        SSLConnectionSocketFactory connectionSocketFactory = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
//
//        HttpClientBuilder httpClientBuilder = HttpClients.custom();
//        httpClientBuilder.setSSLSocketFactory(connectionSocketFactory);
//        CloseableHttpClient httpClient = httpClientBuilder.build();
//        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
//        factory.setHttpClient(httpClient);
//        return factory;
//    }
//
//    /**
//     * 避免HttpClient的”SSLPeerUnverifiedException: peer not authenticated”异常
//     * 不用导入SSL证书
//     * @param base
//     * @return
//     */
//    public static HttpClient wrapClient(HttpClient base) {
//        try {
//            SSLContext ctx = SSLContext.getInstance("TLS");
//            X509TrustManager tm = new X509TrustManager() {
//                @Override
//                public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws java.security.cert.CertificateException {
//
//                }
//
//                @Override
//                public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws java.security.cert.CertificateException {
//
//                }
//
//                @Override
//                public X509Certificate[] getAcceptedIssuers() {
//                    return new X509Certificate[0];
//                }
//
//            };
//            ctx.init(null, new TrustManager[] { tm }, null);
//            SSLConnectionSocketFactory ssf = new SSLConnectionSocketFactory(ctx,NoopHostnameVerifier.INSTANCE);
//            CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(ssf).build();
//            return httpclient;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return HttpClients.createDefault();
//        }
//    }
//
//
//
//
//
////    public HttpHeaders login(LoginUserVo loginUserVo) {
////        HttpHeaders headers = new HttpHeaders();
////        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
////        params.add("principal", StringUtils.isEmpty(loginUserVo.getPrincipal()) ? "admin" : loginUserVo.getPrincipal());
////        params.add("password", StringUtils.isEmpty(loginUserVo.getPrincipal()) ? "Szwg%2020" : loginUserVo.getPassword());
////        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);
////        HttpHeaders httpHeaders = SSLRestTemplateUtil.getInstance().postForEntity("https://172.16.0.11/c/login", requestEntity, String.class).getHeaders();
////        return httpHeaders;
////    }
//
//
////    private HttpClient httpClient = null;
////    @SuppressWarnings("unused")
////    private HttpClient postClient = null;
////    private HttpResponse httpResponse = null;
////    private List<NameValuePair> list = null;
////    public void initRequest() {
////        // 设置组件参数, HTTP协议的版本:1.1
////        HttpParams params = new BasicHttpParams();
////        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
////        HttpProtocolParams.setUserAgent(params, "HttpComponents/1.1");
////        HttpProtocolParams.setUseExpectContinue(params, true);
////        // //权限设置
////        // //凭证注册器
////        // CredentialsProvider credsProvider = new BasicCredentialsProvider();
////        // //注册1 somehost主机名 任意PORT 指定用户U1 密码P1
////        // credsProvider.setCredentials(
////        // new AuthScope("somehost", AuthScope.ANY_PORT),
////        // new UsernamePasswordCredentials("u1", "p1"));
////        // 初始化HttpClient对象
////        // 设置登录参数
////        list = new ArrayList<NameValuePair>();
////        list.add(new BasicNameValuePair("admin", "Szwg%2020"));// 登录参数
////        httpClient = new DefaultHttpClient(params);
////
////    }
////
////    public static void main(String[] args) {
////        HttpClientUtils httpClientUtils = new HttpClientUtils();
////        httpClientUtils.initRequest();
////        httpClientUtils.testGetCurrentUser();
////        httpClientUtils.testGetCurrentUser2();
////    }
////    private HttpRequestBase curlSetHeader(HttpRequestBase tog, String content_type) {
////        if (!content_type.trim().equals("")){
////            tog.setHeader("Content-Type", "application/json");
////            tog.setHeader("Authorization", "Basic YWRtaW46YWRtaW4=");
////            tog.setHeader("Host", "172.16.0.11");
////            tog.setHeader("User-Agent", "curl/7.29.0");
////            tog.setHeader("accept", "application/json");
////        }
////        for(Header h:tog.getAllHeaders()) {
////            System.out.println(h.getName()+":"+h.getValue());
////        }
////        return tog;
////    }
////
//////    @Test
////    public void testGetCurrentUser() {
////        int code = 0;
////        String responseEntity = "啥都没";
////        HttpGet get;
////        //忽略SSL请求认证
////        httpClient = (CloseableHttpClient) wrapClient(httpClient);
////        try {
//////            String url = "https://172.16.0.11/api/v2.0/users/current";
////            String url = "https://172.16.0.11/c/login";
////            get = new HttpGet(url);
////            get=(HttpGet) curlSetHeader(get,"");
////            httpResponse = httpClient.execute(get);
////            @SuppressWarnings("unused")
////            HttpEntity httpEntity = httpResponse.getEntity();
////            code = httpResponse.getStatusLine().getStatusCode();
////            System.out.println("状态码:" + code);
////            responseEntity = EntityUtils.toString(httpResponse.getEntity());
////            System.out.println("响应体:" + responseEntity);
////        } catch (Exception e) {
////            // TODO Auto-generated catch block
////            e.printStackTrace();
////        }
////    }
////
////    public void testGetCurrentUser2() {
////        int code = 0;
////        String responseEntity = "啥都没";
////        HttpGet get;
////        //忽略SSL请求认证
////        httpClient = (CloseableHttpClient) wrapClient(httpClient);
////        try {
////            String url = "https://172.16.0.11/api/v2.0/users/current";
//////            String url = "https://172.16.0.11/c/login";
////            get = new HttpGet(url);
////            get=(HttpGet) curlSetHeader(get,"");
////            httpResponse = httpClient.execute(get);
////            @SuppressWarnings("unused")
////            HttpEntity httpEntity = httpResponse.getEntity();
////            code = httpResponse.getStatusLine().getStatusCode();
////            System.out.println("状态码:" + code);
////            responseEntity = EntityUtils.toString(httpResponse.getEntity());
////            System.out.println("响应体:" + responseEntity);
////        } catch (Exception e) {
////            // TODO Auto-generated catch block
////            e.printStackTrace();
////        }
////    }
//
//}
