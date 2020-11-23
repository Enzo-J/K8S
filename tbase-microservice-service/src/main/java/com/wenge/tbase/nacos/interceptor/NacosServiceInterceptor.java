package com.wenge.tbase.nacos.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
public class NacosServiceInterceptor implements HandlerInterceptor{
    @Override
    public boolean preHandle(HttpServletRequest request,HttpServletResponse response,Object handler) throws Exception {
        //判断header里STORE_BUCKET_KEY是否存在
//    	String storeBucketKey=request.getHeader(Constant.STORE_BUCKET_KEY);
//    	System.out.println("bucket_key--------------->"+storeBucketKey);
//        if (StringUtils.isNotBlank(storeBucketKey)) {
//        	return true;
////           if(storeBucketKey.equals("sby-f133b16a") || keyExists(storeBucketKey)){
////        	   return true;
////           }
//        }      
        return false;
    }
    
    /**
     * 判断存储bucket 是否存在
     * @param key
     * @return true 存在，false 不存在
     */
    private boolean keyExists(String key){
//    	boolean result=false;
//    	try {
//			 result=wosClient.bucketExists(key);			
//		} catch (InvalidKeyException | InvalidBucketNameException | NoSuchAlgorithmException | InsufficientDataException
//				| NoResponseException | ErrorResponseException | InternalException | InvalidResponseException
//				| IOException | XmlPullParserException e) {
//			e.printStackTrace();
//			throw new WengeException(ErrorType.STORE_KEY_ILLEGAL);
//		}
    	return false;
    }
}
