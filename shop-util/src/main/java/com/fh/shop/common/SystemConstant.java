package com.fh.shop.common;

public final class SystemConstant {
    public static final String CURR_USER = "user";
    public static final String LOGIN_URL = "/index.jsp";
    public static final String UPLOAD_PATH = "/upload/";
    public static final String UPLOAD_File_PATH = "d:/upload/";

    public interface ORDER_STATUS {
        int WATT_PAY = 0;
        int PAY_SUCCESS = 10;
        int TRAND_CLOSE = 40;
    }
    public interface MESSAGE_LOG_STATUS {
        int SENDING = 0;//投递中
        int SEND_SUCCESS = 1;//投递成功
        int SEND_FALL = 2;//投递失败
        int CONSUME_SUCCESS = 3;//消费成功
        int EXCHANGE_QUEUE_FALL = 4 ;//交换机到消息异常
    }

    public static final int RETRY_COUNT = 3;

}
