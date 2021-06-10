package com.fh.shop.api.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author wzq
 * @description
 * @date 2021/4/20 15:24
 */
public class Verify {

    /*public static void main(String[] args) {
        String telNum = "89889766";
        boolean mobiPhoneNum = isMobiPhoneNum(telNum);
        System.out.println(mobiPhoneNum);
    }*/

    /**
     * 手机号通用判断【推荐】
     * @param telNum
     * @return
     */
    public static boolean isMobiPhoneNum(String telNum){
        String regex = "^((13[0-9])|(15[0-9])|(18[0-9]))\\d{8}$";
        Pattern p = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(telNum);
        return m.matches();
    }

    /**
     * 判断输入的邮箱格式是否正确
     * @param mail 输入的邮箱地址
     * @return 返回邮箱地址是否正确
     */
    public static boolean isMail(String mail) {
        boolean flag = false;
        String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p;
        Matcher m;
        p = Pattern.compile(regEx1);
        m = p.matcher(mail);
        if(m.matches()){
            flag = true;
        }
        return flag;
    }

}
