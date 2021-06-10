package com.fh.shop.common;


import java.io.Serializable;

public class ServerResponse<T> implements Serializable {

    private int code;

    private String message;

    private T data;

    private ServerResponse(){}
    private ServerResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static ServerResponse success(){

        return new ServerResponse(200,"ok",null);
    }
    public static ServerResponse error(){
        return new ServerResponse(100,"error",null);
    }
    public static <T> ServerResponse<T> success(T data){
        return new ServerResponse(200,"ok",data);
    }

    public static ServerResponse error(int code,String message){
        return new ServerResponse(code,message,null);
    }

    public static ServerResponse error(ResponseEnum responseEnum){
        return new ServerResponse(responseEnum.getCode(),responseEnum.getMessage(),null);
    }

    public static ServerResponse error(ResponseEnum responseEnum,Object data){
        return new ServerResponse(responseEnum.getCode(),responseEnum.getMessage(),data);
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
