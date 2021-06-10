package com.fh.shop.util;

import java.math.BigDecimal;

public class BigdecimalUtil {

    public static BigDecimal mul(String s1,String s2){
        BigDecimal b1 = new BigDecimal(s1);
        BigDecimal b2 = new BigDecimal(s2);
        return b1.multiply(b2).setScale(2);//b1.multiply(b2).setScale(2) = b1 * b2 小数位2
    }
}
