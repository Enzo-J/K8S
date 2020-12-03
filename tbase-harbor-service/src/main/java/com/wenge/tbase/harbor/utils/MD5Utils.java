package com.wenge.tbase.harbor.utils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

/**
 * 密匙工具类(包含des加密与md5加密)
 *
 */
public class MD5Utils {

    private final static String DES = "DES";

    private final static String MD5 = "MD5";

    private final static String KEY="op2e3d597ae2a1d32s55adku33d53g484591dadbc382a18340bf834";

    private static MessageDigest digest;
    static{
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public synchronized static String md5(String key) {
        if (key == null || key.trim().equals("")) {
            return null;
        }
        byte[] bytes = digest.digest(key.getBytes());
        StringBuilder output = new StringBuilder(bytes.length);
        for (Byte entry : bytes) {
            output.append(String.format("%02x", entry));
        }
        digest.reset();
        return output.toString();
    }

    /**
     * MD5加密算法
     * @param data
     * @return
     */
    public static String md5Encrypt(String data) {
        String resultString = null;
        try {
            resultString = new String(data);
            MessageDigest md = MessageDigest.getInstance(MD5);
            resultString =byte2hexString(md.digest(resultString.getBytes()));
        } catch (Exception ex) {
        }
        return resultString;
    }


    private  static String byte2hexString(byte[] bytes) {
        StringBuffer bf = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            if ((bytes[i] & 0xff) < 0x10) {
                bf.append("T0");
            }
            bf.append(Long.toString(bytes[i] & 0xff, 16));
        }
        return bf.toString();
    }

    /**
     * Description 根据键值进行加密
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    public static String desEncrypt(String data, String key) throws Exception {
        if (key==null) {
            key=KEY;
        }
        byte[] bt = encrypt(data.getBytes(), key.getBytes());
        String strs = new BASE64Encoder().encode(bt);
        return strs;
    }

    /**
     * Description 根据键值进行解密
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws IOException
     * @throws Exception
     */
    public static String desDecrypt(String data, String key) throws IOException,
            Exception {
        if (data == null){
            return null;
        }
        if (key==null) {
            key=KEY;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] buf = decoder.decodeBuffer(data);
        byte[] bt = decrypt(buf,key.getBytes());
        return new String(bt);
    }

    /**
     * Description 根据键值进行加密
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance(DES);
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
        return cipher.doFinal(data);
    }


    /**
     * Description 根据键值进行解密
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance(DES);
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
        return cipher.doFinal(data);
    }

    //加密
    public static String base64Encrypt(String s) {
        final Base64.Encoder encoder = Base64.getEncoder();
        final String text = s;
        byte[] textByte;
        try {
            textByte = text.getBytes("UTF-8");
            final String encodedText = encoder.encodeToString(textByte);
            return encodedText;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //编码
        return text;
    }

    //解密
    public static String base64Decrypt(String encodedText) {
        final Base64.Decoder decoder = Base64.getDecoder();
        //解码
        try {
            return new String(decoder.decode(encodedText), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodedText;
    }


    //创建一个日期加密串
	public static String getMD5String(String baseURL){
		SimpleDateFormat sformat = new SimpleDateFormat("yyyy-MM-dd");
		String string = baseURL+sformat.format(new Date());
		return  MD5Utils.md5(string);
	}

    public static void main(String[] args) {
        String string = getMD5String("/contact/sendTask");
        System.err.println(string);
    }

}