package com.fh.shop.util;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static final String FULL_YEAR = "yyyy-MM-dd HH:mm:ss";
    public static final String FULL_Y_M_D = "yyyy-MM-dd";
    public static final String FULL_Y_M = "yyyy-MM";
    public static Date addMinute(Date date,int minutes){
        DateTime dateTime = new DateTime(date);
        DateTime newDateTime = dateTime.plusMinutes(minutes);
        return newDateTime.toDate();
    }


    public static String date2str(Date date,String pattern){

        if(date == null){
            return "";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

}
