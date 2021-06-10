package com.fh.shop.common;

public class Info<T> {

    public T data;



    public  Info(T data){
        this.data = data;
    }

    public static <T> Info<T> success(T data){
        return new Info(data);
    }

    public T getData(){
        return data;
    }


    public void setData(T data){
        this.data = data;
    }


}
