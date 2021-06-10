package com.fh.shop.bookdemo.book;

import com.fh.shop.common.ServerResponse;
import com.fh.shop.common.SystemConstant;
import com.fh.shop.util.FileUtil;
import com.fh.shop.util.OSSUtil;
import com.fh.shop.util.OssFileUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Controller
@CrossOrigin
@RequestMapping("/file")
public class FileController extends  BaseController{

    /**
     * 图片上传OSs
     */
    @RequestMapping(value = "/uploadOss", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse uploadOss(@RequestParam MultipartFile imageFile){
        String s = OssFileUtil.uploadFile(imageFile);
        System.out.println(s);
        return ServerResponse.success(s);
    }


    @RequestMapping(value = "/uploadImage",method = RequestMethod.POST)
    @ResponseBody
    public Map uploadImage(@RequestParam("file") MultipartFile imageFile, HttpServletRequest request){

        HashMap result = new HashMap();

        try {
            InputStream inputStream = imageFile.getInputStream();
            //原始的文件名
            String originalFilename = imageFile.getOriginalFilename();
            // 怎么根据相对路径获取绝对路径
            String realPath = getRealPath(SystemConstant.UPLOAD_PATH,request);
            //调用工具类进行拷贝以及重命名
            String uploadedFileName = FileUtil.copyFile(inputStream, originalFilename, realPath);
            // 将上传后的路径响应给前台[相对路径 文件夹的名字+文件的名字]
            result.put("data", SystemConstant.UPLOAD_PATH+uploadedFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    @RequestMapping(value = "/uploadImages",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse uploadImages(@RequestParam MultipartFile[] imageFiles, HttpServletRequest request){

        try {
            StringBuilder uploadPaths = new StringBuilder();
            for (MultipartFile imageFile : imageFiles) {
//                InputStream inputStream = imageFile.getInputStream();
//                //原始的文件名
//                String originalFilename = imageFile.getOriginalFilename();
//                // 怎么根据相对路径获取绝对路径
//                String realPath = getRealPath(SystemConstant.UPLOAD_PATH,request);
//                //调用工具类进行拷贝以及重命名
//                String uploadedFileName = FileUtil.copyFile(inputStream, originalFilename, realPath);
//                // 将上传后的路径响应给前台[相对路径 文件夹的名字+文件的名字]
//                uploadPaths.append(",").append(SystemConstant.UPLOAD_PATH+uploadedFileName);
                InputStream inputStream = imageFile.getInputStream();
                //原始的文件名
                String originalFilename = imageFile.getOriginalFilename();
                String uploadFilePath = OSSUtil.upload(originalFilename, inputStream);
                uploadPaths.append(",").append(uploadFilePath);
            }

            return ServerResponse.success(uploadPaths.toString());
        } catch (IOException e) {
            //遇到异常处理方式
            //打印异常
            //抛异常
            e.printStackTrace();
//            return ServerResponse.error();
            throw new RuntimeException(e);
        }
    }





    @RequestMapping(value = "/uploadExcel",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse uploadExcel(@RequestParam MultipartFile excelFile, HttpServletRequest request){

        try {
            InputStream inputStream = excelFile.getInputStream();
            //原始的文件名
            String originalFilename = excelFile.getOriginalFilename();
            //调用工具类进行拷贝以及重命名
            String uploadedFileName = FileUtil.copyFile(inputStream, originalFilename, SystemConstant.UPLOAD_File_PATH);
            // 将上传后的路径响应给前台[相对路径 文件夹的名字+文件的名字]

            return ServerResponse.success(SystemConstant.UPLOAD_File_PATH + uploadedFileName);
        } catch (IOException e) {
            //遇到异常处理方式
            //打印异常
            //抛异常
            e.printStackTrace();
//            return ServerResponse.error();
            throw new RuntimeException(e);
        }
    }
}
