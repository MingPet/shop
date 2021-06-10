package com.fh.shop.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class SMSUtil {

    private static final String URL = "https://api.netease.im/sms/sendcode.action";
    private static final String APPKEY = "3564b2b1c2eb53c7c70f79683b5aa8d8";
    private static final String APPSECRET = "410d1dab251d";
    /** 短信模板ID*/
    private static final String TEMPLATEID = "19477176";//19472192


    public static String send(String phone) {
        Map<String,String> headers = new HashMap<>();

        String nonce = UUID.randomUUID().toString();
        String curTime = System.currentTimeMillis() + "";
        //String appSecret = APPSECRET;
        String checkSum = CheckSumBuilder.getCheckSum(APPSECRET, nonce, curTime);
        headers.put("AppKey",APPKEY);
        headers.put("Nonce", nonce);
        headers.put("CurTime", curTime);
        headers.put("CheckSum", checkSum);
        Map<String,String> params = new HashMap<>();
        params.put("templateid", TEMPLATEID);
        params.put("mobile",phone);
        String result = HttpClientUtil.sendPost(URL, headers, params);
        return result;

    }

}
