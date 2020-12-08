package com.wenge.tbase.harbor.test;//package com.wenge.tbase.harbor.test;
import com.wenge.tbase.harbor.service.impl.HarborRequestImpl;
import com.wenge.tbase.harbor.utils.MD5Utils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: chf
 * @methodName:
 * @description: 測試方法
 * @param: null
 * @date: 14:04 2020/11/26
 * @return:
 */
public class TestMethod {

    private static RestTemplate restTemplate;

//    String url = "https://172.16.0.11/api/v2.0/projects/1/scanner";
//    String url = "https://172.16.0.11/api/v2.0/users/current";

    public static void main1(String[] args) throws Exception {
//        TestRiQingAPI_SaleOrder();
//        String url = "http://172.16.0.11/api/v2.0/projects/1/scanner";
//        Map<String, Object> params = new HashMap<>();
//        params.put("md5", MD5Utils.getMD5String("/contact/sendTaskNew"));
//        params.put("type", "SMS");
//        String rsp = sendPostRequest(url);
//        System.out.println(rsp);

        HarborRequestImpl harborRequest = new HarborRequestImpl();
        harborRequest.queryImagesTagsByImageName(null);
    }

    //创建一个日期加密串
    public static String getMD5String(String baseURL){
        SimpleDateFormat sformat = new SimpleDateFormat("yyyy-MM-dd");
        String string = baseURL+sformat.format(new Date());
        return  MD5Utils.md5(string);
    }

    public static String sendPostRequest(String url){
        try {
//            String url = "http://127.0.0.1:11001/contact/taskSend";
            HttpHeaders headers = new HttpHeaders();
            headers.set("Cookie","_gorilla_csrf=MTYwNjM3MDI1OHxJbnB4U1dOU00yb3lSV2htVm1OM2FGQjVZMDlvTDJWV1JtWXplVWx5Y1VoNVZ5OVhTbkZNY0VkMFFWazlJZ289fB-h-9NENFGCqeKvamWwp41HYFStYOuiQW53atSamoUl; sid=9eb4a628385203d5e638655e3aa7d0cd; __csrf=Aor0w8L5PoP8/fdnuuBXRFcaEcW9i/+gLY6RLnGYy8jMKOiEug8slCmO/yhzI/a5sl9uuTUlXlJ2exiGy95/zg==");
            headers.setContentType(MediaType.APPLICATION_JSON);
            Map<String, Object> params = new HashMap<>();
//            params.put("md5", MD5Utils.getMD5String("/contact/sendTaskNew"));
//            params.put("type", "SMS");
            System.out.println(params);
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<Map<String, Object>>(params,headers);
            ResponseEntity<String> request = restTemplate.postForEntity(url, httpEntity,String.class);
            return request.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("- post request ex - ",e);
        }
    }



    public static void TestRiQingAPI_SaleOrder() throws Exception {

//        String postData = getJson();
        //String url = "https://*****";
        String url = "https://172.16.0.11/api/v2.0/projects/1/scanner";
//        String url = "https://172.16.0.11/api/v2.0/users/current";
        HttpsURLConnection conn = null;
        OutputStream out = null;
        String rsp = null;
//        byte[] byteArray = postData.getBytes("utf-8");
        try {
            URL uri = new URL(url);
            conn = (HttpsURLConnection) uri.openConnection();
            //忽略证书验证--Begin
//            conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
            //忽略证书验证--End
            conn.setRequestMethod("GET");
//            conn.setDoInput(true);
//            conn.setDoOutput(true);
//            conn.setRequestProperty("Host", uri.getHost());
//            conn.setRequestProperty("Content-Type", "application/json");
//            out = conn.getOutputStream();
////            out.write(byteArray);
//            out.close();
            if(conn.getResponseCode()==200) {
                rsp = getStreamAsString(conn.getInputStream(), "utf-8");
            }else {
                rsp = getStreamAsString(conn.getErrorStream(), "utf-8");
            }

            System.out.println(rsp);

        } catch (Exception e) {
            if(null!=out)
                out.close();
            e.printStackTrace();

        }


    }


    private static String getJson() {
        return "{" + "\"name\"" + ":" + "\"robo_blogs_zh123\"" + "}";
    }

    private static String getStreamAsString(InputStream stream, String charset) throws IOException {
        try {
            Reader reader = new InputStreamReader(stream, charset);
            StringBuilder response = new StringBuilder();

            final char[] buff = new char[1024];
            int read = 0;
            while ((read = reader.read(buff)) > 0) {
                response.append(buff, 0, read);
            }

            return response.toString();
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }

    public static void main(String[] args) throws Exception {

//        boolean result = sendPost("https://172.16.0.11/c/login","principal=admin&password=Szwg%2020","密码错误");
            JsoupPost();
//        System.err.println(result);
    }


    //访问远程登录action并获取返回的信息
    public static boolean sendPost(String url, String param, String temp) {
        PrintWriter out = null ;
        BufferedReader in = null ;
        boolean result = true ;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty( "accept" , "*/*" );
            conn.setRequestProperty( "connection" , "Keep-Alive" );
            conn.setRequestProperty( "user-agent" , "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)" );
            // 发送POST请求必须设置如下两行
            conn.setDoOutput( true );
            conn.setDoInput( true );
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader( new InputStreamReader(conn.getInputStream(), "UTF-8" ));
            String line;
            while ((line = in.readLine()) != null ) {
                if (temp.equals((line.trim()))) {
                    System.out.println( "错误的line:" +line);
                    result = false ;
                }
            }
        } catch (Exception e) {
            result = false ;

            System.out.println( "发送 POST 请求出现异常！" +e);
            e.printStackTrace();
        } finally {
            try {
                if (out!= null ){
                    out.close();
                }
                if (in!= null ){
                    in.close();
                }
            } catch (IOException ex){
                System.out.println( ex);
                ex.printStackTrace();
            }
        }
        return result;
    }


    public static void JsoupPost() throws Exception {
        Connection connect = Jsoup.connect("https://172.16.0.11/c/login");
        connect.sslSocketFactory(new SSLSocketFactory() {
            @Override
            public String[] getDefaultCipherSuites() {
                return new String[0];
            }

            @Override
            public String[] getSupportedCipherSuites() {
                return new String[0];
            }

            @Override
            public Socket createSocket(Socket socket, String s, int i, boolean b) throws IOException {
                return null;
            }

            @Override
            public Socket createSocket(String s, int i) throws IOException, UnknownHostException {
                return null;
            }

            @Override
            public Socket createSocket(String s, int i, InetAddress inetAddress, int i1) throws IOException, UnknownHostException {
                return null;
            }

            @Override
            public Socket createSocket(InetAddress inetAddress, int i) throws IOException {
                return null;
            }

            @Override
            public Socket createSocket(InetAddress inetAddress, int i, InetAddress inetAddress1, int i1) throws IOException {
                return null;
            }
        });
        // 带参数开始
        connect.data("principal", "admin");
        connect.data("password", "Szwg%2020");
        // 带参数结束
        Document document = connect.post();
        System.out.println(document.toString());

    }
}
