package com.wenge.tbase.k8s.utils;

public class StringUtil {
    public static boolean isIntString(String str) {
        try {
            Integer.valueOf(str);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(isIntString("2"));
    }
}
