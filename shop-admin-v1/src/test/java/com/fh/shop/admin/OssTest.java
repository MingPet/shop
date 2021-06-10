package com.fh.shop.admin;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.fh.shop.util.DateUtil;
import com.fh.shop.util.FileUtil;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

public class OssTest {

    @Test
    public  void test2(){
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
        String endpoint = "oss-cn-beijing.aliyuncs.com";
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = "LTAI5tMcLMazKzV7oiDy2mgJ";
        String accessKeySecret = "q7cHIFOnLrakK8XCtnRyqTwXNBKJZD";
        OSS ossClient = null;
        String bucketName = "fh2008m";
        String url = "https://fh2008m.oss-cn-beijing.aliyuncs.com/";

        String objectName ="photo/wallhaven-57.png";
        // 创建OSSClient实例。
       ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
        ossClient.deleteObject(bucketName, objectName);

        // 关闭OSSClient。
        ossClient.shutdown();

    }

    @Test
    public void test1() {
        // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
        String endpoint = "oss-cn-beijing.aliyuncs.com";
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = "LTAI5tMcLMazKzV7oiDy2mgJ";
        String accessKeySecret = "q7cHIFOnLrakK8XCtnRyqTwXNBKJZD";
        OSS ossClient = null;
        String bucketName = "fh2008m";
        String url = "https://fh2008m.oss-cn-beijing.aliyuncs.com/";

        try {

            // 创建OSSClient实例。
            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            String fileName = "wallhaven-57.png";
            // 填写本地文件的完整路径。如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
            InputStream inputStream = new FileInputStream("D:\\"+fileName);

            // 填写Bucket名称和Object完整路径。Object完整路径中不能包含Bucket名称。
            String uuid = UUID.randomUUID().toString();
            String newFileName = uuid + FileUtil.getSuffix(fileName);

            //把每天存的文件放到一个单独的文件夹中，文件夹名字日期名 2020-02-12
            String datePath = DateUtil.date2str(new Date(), DateUtil.FULL_Y_M_D);
            String objectName = datePath+"/"+newFileName;
            ossClient.putObject(bucketName, objectName, inputStream);
            System.out.println(url + objectName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw  new  RuntimeException(e);
        } finally {
            if(ossClient != null){
                // 关闭OSSClient。
                ossClient.shutdown();
            }
        }


    }

}
