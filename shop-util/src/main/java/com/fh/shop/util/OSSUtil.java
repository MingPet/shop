package com.fh.shop.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.DeleteObjectsRequest;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class OSSUtil {
    // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
    private static String endpoint = "oss-cn-beijing.aliyuncs.com";
    // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
    private static String accessKeyId = "LTAI5tMcLMazKzV7oiDy2mgJ";
    private static String accessKeySecret = "q7cHIFOnLrakK8XCtnRyqTwXNBKJZD";
    private static String bucketName = "fh2008m";
    private static String url = "https://fh2008m.oss-cn-beijing.aliyuncs.com/";

    public static void deleteFiles(List<String> filePaths) {
        List<String> imagePaths = filePaths.stream().map(x -> x.replace(url, "")).collect(Collectors.toList());
        OSS ossClient = null;
        try {
            // 创建OSSClient实例。
            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
            ossClient.deleteObjects(new DeleteObjectsRequest(bucketName).withKeys(imagePaths));
        } finally {
            if (null != ossClient) {
                // 关闭OSSClient。
                ossClient.shutdown();
            }
        }
    }

//    //oss批量删除图片
//    public static void deleteFiles(List<String> filePaths){
//        List<String> imagePaths = filePaths.stream().map(x -> x.replace(url, "")).collect(Collectors.toList());
//        OSS ossClient = null;
//
//        try {
//            // 创建OSSClient实例。
//            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
//
//            // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
//            ossClient.deleteObject(new DeleteObjectsRequest(bucketName).withKeys(imagePaths));
//        } finally {
//            if(null != ossClient){
//                // 关闭OSSClient。
//                ossClient.shutdown();
//            }
//        }
//
//
//    }

    //oss删除图片
    public static void deleteFile(String filePath){
        OSS ossClient = null;

        String objectName = filePath.replace(url,"");
        try {
            // 创建OSSClient实例。
            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
            ossClient.deleteObject(bucketName, objectName);
        } finally {
            if(null != ossClient){
                // 关闭OSSClient。
                ossClient.shutdown();
            }
        }


    }

    //oss图片上传
    public static String upload(String fileName,InputStream inputStream) {


        OSS ossClient = null;
        String res;
        try {
            // 创建OSSClient实例。
            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);



            // 填写Bucket名称和Object完整路径。Object完整路径中不能包含Bucket名称。
            String uuid = UUID.randomUUID().toString();
            String newFileName = uuid + FileUtil.getSuffix(fileName);

            //把每天存的文件放到一个单独的文件夹中，文件夹名字日期名 2020-02-12
            String datePath = DateUtil.date2str(new Date(), DateUtil.FULL_Y_M_D);
            String objectName = datePath + "/" + newFileName;
            ossClient.putObject(bucketName, objectName, inputStream);
            res = url + objectName;
            return res;
        } finally {
            if(inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(ossClient!=null){
                //关闭OSSClient
                ossClient.shutdown();
            }
        }

    }
}
