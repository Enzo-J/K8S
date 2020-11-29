package com.wenge.tbase.commons.utils;

import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;

/**
 * @ClassName: AESUtils
 * @Description: AESUtils
 * @Author: Wang XingPeng
 * @Date: 2020/8/25 10:22
 */
public class AESUtils {

    private static AES aes = new AES(Mode.CTS, Padding.PKCS5Padding, "wenge11111111111".getBytes(), "0102030405060708".getBytes());

    public static String encryptHex(String password) {
        return aes.encryptHex(password);
    }

    public static String decryptStr(String password) {
        return aes.decryptStr(password);
    }
}
