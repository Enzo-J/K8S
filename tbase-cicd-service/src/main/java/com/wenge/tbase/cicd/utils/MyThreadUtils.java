package com.wenge.tbase.cicd.utils;

/**
 * @ClassName: ThreadUtils
 * @Description: ThreadUtils
 * @Author: Wang XingPeng
 * @Date: 2020/12/21 14:50
 */
public class MyThreadUtils {

    public static void sleep(Integer time) {
        try {
            Thread.sleep(time * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
