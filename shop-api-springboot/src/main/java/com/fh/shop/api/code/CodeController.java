package com.fh.shop.api.code;

import com.fh.shop.api.common.KeyUtil;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.util.RedisUtil;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@Api(tags = "验证码接口")
public class CodeController {
    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private HttpServletRequest request;


    @GetMapping("/code")
    public ServerResponse code(String id){
        //生成文字码
        String text = defaultKaptcha.createText();
        //存储到redis中
        //request.getSession().setAttribute("code",text);
        if(StringUtils.isEmpty(id)){
            id = UUID.randomUUID().toString();
        }

        RedisUtil.setEx(KeyUtil.buildImageCodekey(id),text,5*60);
        //根据文字码生成对应的图片码
        BufferedImage bufferedImage = defaultKaptcha.createImage(text);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            //将动态生成的图片图片 写到ByteArrayOutputStream
            ImageIO.write(bufferedImage, "jpg", bos);
            //将ByteArrayOutputStream 转换为对应的字节数组
            byte[] bytes = bos.toByteArray();
            //将字节数组进行base64编码
            String imageBase64 = Base64.getEncoder().encodeToString(bytes);
            Map<String,String> result = new HashMap<>();
            result.put("id",id);
            result.put("imageBase64",imageBase64);
            return ServerResponse.success(result);
        } catch (IOException e) {
            e.printStackTrace();
            throw  new RuntimeException(e);
        }


    }
    @GetMapping("/code2")
    public void code2() {
        //生成文字码
        String text = defaultKaptcha.createText();
        //存储
        //request.getSession().setAttribute("code",text);
        //存储到redis中
        String uuid = UUID.randomUUID().toString();
        RedisUtil.setEx(KeyUtil.buildImageCodekey(uuid),text,5*60);

        //根据文字码生成对应的图片码
        BufferedImage bufferedImage = defaultKaptcha.createImage(text);
        ServletOutputStream outputStream = null;

        try {
            // Set to expire far in the past.
            response.setDateHeader("Expires", 0);
            // Set standard HTTP/1.1 no-cache headers.
            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
            response.addHeader("Cache-Control", "post-check=0, pre-check=0");
            // Set standard HTTP/1.0 no-cache header.
            response.setHeader("Pragma", "no-cache");
            // return a jpeg
            response.setContentType("image/jpeg");


            outputStream = response.getOutputStream();
            //将图片打印到浏览器 将图片写到outputStream输出流
            ImageIO.write(bufferedImage, "jpg", outputStream);


            outputStream.flush();
            System.out.println(text);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



}

