package com.fh.shop.api;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class TestOss {
    // 阿里云oss上传文件相关属性值
    // 阿里云API的外网域名
    public static final String ENDPOINT = "oss-cn-beijing.aliyuncs.com";
    // 阿里云API的密钥
    public static final String ACCESS_KEY_ID = "LTAI5tMcLMazKzV7oiDy2mgJ";
    // 阿里云API的密钥Access Key Secret
    public static final String ACCESS_KEY_SECRET = "q7cHIFOnLrakK8XCtnRyqTwXNBKJZD";
    // 阿里云API的bucket名称
    public static final String BUCKET_NAME = "fh2008m";
    // 阿里云API的文件夹名称
    public static final String FOLDER = "photo/";
    public static void main(String[] args) {
        // 创建ossclient对象,通过keyid   key secret创建
        OSSClient ossClient = new OSSClient(ENDPOINT, ACCESS_KEY_ID,ACCESS_KEY_SECRET);
        // 上传文件的路径
        String path = "D:\\wallhaven-1.png";
        // 根据路径创建file
        File file = new File(path);
        try {
            // 以输入流的形式上传文件
            InputStream is = new FileInputStream(file);
            // 获取文件名
            String fileName = file.getName();
            // 获取文件大小
            long fileSize = file.length();
            // 创建一个metadata对象
            ObjectMetadata metadata = new ObjectMetadata();
            // 给metadata赋值 			// 文件大小
            metadata.setContentLength(fileSize);
            // 定义文件的类型以及网页编码：决定浏览器是以什么形式什么编码读取文件
            String suffix = fileName.substring(fileName.lastIndexOf("."));
            String str = "";
            if(".jpg".equalsIgnoreCase(suffix)){
                str = "image/jpeg";
            }
            else if(".bmp".equalsIgnoreCase(suffix)){
                str = "image/bmp";
            }
            else if(".png".equalsIgnoreCase(suffix)){
                str = "image/png";
            }
            else if(".gif".equalsIgnoreCase(suffix)){
                str = "image/gif";
            }
            metadata.setContentType(str);
            // 指定文件的名称
            metadata.setContentDisposition(fileName);
            // 上传文件
            ossClient.putObject(BUCKET_NAME,FOLDER+fileName, file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


}
