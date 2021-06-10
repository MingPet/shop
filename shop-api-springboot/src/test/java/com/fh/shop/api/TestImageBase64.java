package com.fh.shop.api;

import org.junit.Test;

import java.io.*;
import java.util.Base64;

public class TestImageBase64 {

    @Test
    public void test1(){
        File file = new File("D:\\/wallhaven-1.jpg");
        byte[] imageBase64 = File2byte(file);
        String imgbase64 = Base64.getEncoder().encodeToString(imageBase64);
        System.out.println(imgbase64);
    }

    public static byte[] File2byte(File tradeFile){
        byte[] buffer = null;
        try
        {
            FileInputStream fis = new FileInputStream(tradeFile);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1)
            {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return buffer;
    }
}
