package com.fh.shop.common;

public class KeyUtil {

    public static String buildMemberKey(Long id){
        return "member:"+id;
    }

    public static String buildImageCodekey(String id){
        return "image:code:"+id;
    }

    public static String buildActiveMemberkey(String id){
        return "active:member:"+id;
    }

    public static String buildCartkey(Long memberId){
        return "cart:"+memberId;
    }

    public static String buildTokenKey(String token){
        return "token:"+token;
    }

}
