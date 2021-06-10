package com.fh.shop.common;

import lombok.Data;

@Data
public class BookServerResponse {

    private int responseCode;

    private String responseMsg;

    private Object data;

    private  BookServerResponse(){}

    private BookServerResponse(int responseCode, String responseMsg, Object data) {
        this.responseCode = responseCode;
        this.responseMsg = responseMsg;
        this.data = data;
    }

    public static BookServerResponse success(){
        return new BookServerResponse(200,"ok",null);
    }

    public static BookServerResponse error(){
        return new BookServerResponse(499,"error",null);
    }

    public static BookServerResponse success(Object data){
        return new BookServerResponse(200,"ok",data);
    }

    public static BookServerResponse error(int code,String msg){
        return new BookServerResponse(code,msg,null);
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public Object getData() {
        return data;
    }
}
