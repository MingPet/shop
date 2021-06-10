package com.fh.shop;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;

public class HttpClientTest {

    @Test
    public void test1(){
        //打开浏览器客户端
        CloseableHttpClient client = HttpClientBuilder.create().build();
        //输入url 在浏览器上发送的都是get请求
        HttpGet httpGet = new HttpGet("https://www.jd.com/");

        CloseableHttpResponse response = null;
        FileWriter fileWriter = null;
        try {
            //发送请求
            response = client.execute(httpGet);
            //获取响应内容
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity, "utf-8");
            fileWriter = new FileWriter("D://jd.html");
            fileWriter.write(result);
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if(fileWriter != null){
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(response != null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(httpGet != null){
                httpGet.releaseConnection();
            }
            if(client != null){
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
