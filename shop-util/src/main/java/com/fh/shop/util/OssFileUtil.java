package com.fh.shop.util;

import com.aliyun.oss.OSSClient;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class OssFileUtil {
    // 阿里云oss上传文件相关属性值
    // 阿里云API的外网域名
    public static final String ENDPOINT = "oss-cn-beijing.aliyuncs.com";
    // 阿里云API的密钥
    public static final String ACCESS_KEY_ID ="LTAI5tMcLMazKzV7oiDy2mgJ";
    // 阿里云API的密钥Access Key Secret
    public static final String ACCESS_KEY_SECRET = "q7cHIFOnLrakK8XCtnRyqTwXNBKJZD";
    // 阿里云API的bucket名称
    public static final String BUCKET_NAME =  "fh2008m";
    // 阿里云API的文件夹名称
    public static final String FOLDER = "photo/";
    public static final String URL="https://fh2008m.oss-cn-beijing.aliyuncs.com/";


    public static String uploadFile(MultipartFile file){
        OSSClient ossClient=null;
        InputStream is=null;
        String filePath =null;

        try {
            //创建ossclient对象 key secret 创建
            ossClient=new OSSClient(ENDPOINT,ACCESS_KEY_ID,ACCESS_KEY_SECRET);
            //获取文件名
            String fileName = file.getOriginalFilename();
            //获取后缀
            String suffix = fileName.substring(fileName.lastIndexOf("."));
            //获取文件新名称
            String newFileName =  UUID.randomUUID()+suffix;

            is = file.getInputStream();

            //创建一个已当前时间为文件的文件夹
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
            String folderName = simpleDateFormat.format(new Date());

            //BUCKET_NAME 文件 file
            ossClient.putObject(BUCKET_NAME,folderName+"/"+newFileName,is);

            filePath = URL+folderName+"/"+newFileName;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(ossClient != null){
                ossClient.shutdown();
            }
        }


        return filePath;
    }


    public static void deleteFile(String fileName){
        OSSClient ossClient = new OSSClient(ENDPOINT,ACCESS_KEY_ID,ACCESS_KEY_SECRET);
        //把图片路径替换为空
        String obj = fileName.replace(URL, "");
        //同过文件名删除图片
        ossClient.deleteObject(BUCKET_NAME,obj);
        ossClient.shutdown();


    }

}



