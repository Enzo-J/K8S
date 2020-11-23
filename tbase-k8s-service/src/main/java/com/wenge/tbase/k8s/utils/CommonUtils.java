package com.wenge.tbase.k8s.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public  class CommonUtils {
    public static double formatDouble2(Double d){
        BigDecimal bg=new BigDecimal(d).setScale(2, RoundingMode.DOWN);
        return bg.doubleValue();
    }

}
