package com.fh.shop.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {

    public static void excelDownload(XSSFWorkbook wirthExcelWB, HttpServletRequest request,HttpServletResponse response, String fileName) {
        OutputStream out = null;
        try {
        	
        	//解决下载文件名中文乱码问题
        	if(request.getHeader("User-agent").toLowerCase().indexOf("firefox")!=-1){
        		fileName = new String(fileName.getBytes("utf-8"),"iso-8859-1");
        	}else{
        		fileName = URLEncoder.encode(fileName,"utf-8");
        	}
        	
            out = response.getOutputStream();
            // 让浏览器识别是什么类型的文件
            response.reset(); // 重点突出
            response.setCharacterEncoding("UTF-8"); // 重点突出
            response.setContentType("application/x-msdownload");// 不同类型的文件对应不同的MIME类型
                                                                // // 重点突出
            // inline在浏览器中直接显示，不提示用户下载
            // attachment弹出对话框，提示用户进行下载保存本地
            // 默认为inline方式
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            wirthExcelWB.write(out);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != out) {
                try {
                    out.close();
                    out = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    /**
     * 文件下载
     * @param file
     * @param fileName
     * @param response
     */
    public static void downloadFile(File file, String fileName, HttpServletRequest request, HttpServletResponse response) throws IOException {
        OutputStream os = response.getOutputStream();
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        BufferedOutputStream bos = new BufferedOutputStream(os);
        //解决下载文件名中文乱码问题
        if(request.getHeader("User-Agent").toLowerCase().indexOf("firefox")!=-1){
            fileName = new String(fileName.getBytes("utf-8"),"iso-8859-1");
        }else{
            fileName = URLEncoder.encode(fileName,"utf-8");
        }
        response.reset(); // 重点突出
        response.setCharacterEncoding("UTF-8"); // 重点突出
        response.setContentType("application/x-msdownload");// 不同类型的文件对应不同的MIME类型
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        //定义一个长度为4096的字节数组
        byte[] bytes = new byte[4096];
        //先读他个4096字节
        int read = bis.read(bytes);
        while(read > 0){
            bos.write(bytes,0,read);
            read = bis.read(bytes);
        }
        bos.close();
        bis.close();
    }
}
