package com.fh.shop.admin.controller;

import com.fh.shop.common.ServerResponse;
import com.fh.shop.util.OssFileUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/ossfile")
@CrossOrigin
public class OssFileController {
    //上传图片
    @PostMapping("/upload")
    public ServerResponse upload(MultipartFile file){
        String s = OssFileUtil.uploadFile(file);
        System.out.println(s);
        return ServerResponse.success(s);
    }

    //取消时删除方法
    @PostMapping("/delFile")
    public void delFile(String[] cun){
        //判断数组不能为空
        if(cun !=null){
            for (String s : cun) {
                if(StringUtils.isNotBlank(s)){
                    OssFileUtil.deleteFile(s);
                }
            }
        }

    }


    //删除图片upload
    @RequestMapping("deleteFile")
    public void deleteFile(String images) {
        //批量时删除图片
        if(images!=null ) {
            //删除图片
            String split[] = images.split(",");
            for (int i = 0; i <split.length ; i++) {
                OssFileUtil.deleteFile(split[i]);
            }


        }

    }

}
