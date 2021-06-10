package com.fh.shop;

import com.fh.shop.util.CheckSumBuilder;
import com.fh.shop.util.SMSUtil;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SMSTest {
    @Test
    public void test2(){
        SMSUtil.send("18239591336");
    }

    @Test
    public void test1(){
        CloseableHttpClient client = HttpClientBuilder.create().build();
        //接口的请求方式
        HttpPost httpPost = new HttpPost("https://api.netease.im/sms/sendcode.action");
        //参数
        httpPost.addHeader("AppKey","3564b2b1c2eb53c7c70f79683b5aa8d8");
        String nonce = UUID.randomUUID().toString();
        httpPost.addHeader("Nonce", nonce);
        String curTime = System.currentTimeMillis() + "";
        httpPost.addHeader("CurTime", curTime);//转化为字符串
        String appSecret = "410d1dab251d";
        httpPost.addHeader("AppSecret", appSecret);
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce, curTime);
        httpPost.addHeader("CheckSum",checkSum);

        CloseableHttpResponse response = null;
        try {
        //传递参数
        List<BasicNameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("mobile","18239591336"));
        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(params);

        httpPost.setEntity(urlEncodedFormEntity);
        //发送请求
            response = client.execute(httpPost);
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity, "utf-8");
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if(response!=null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(httpPost!=null){
                httpPost.releaseConnection();
            }
            if(client!=null){
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
