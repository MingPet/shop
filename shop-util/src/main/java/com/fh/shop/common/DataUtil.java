package com.fh.shop.common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataUtil {

    public static final String PULL_YEAR="yyyy-MM-dd HH:mm";

    public static final String Y_N_D="yyyy-MM-dd";

    public static String Date2str(Date date ,String pattern){
        if (date==null){
            return "";
        }
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }
}
