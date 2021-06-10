package com.fh.shop.common;

public enum ResponseEnum {
    MEMBER_IS_NULL(5000,"会员名为空"),
    PAY_ORDER_IS_ERROR(8500,"支付订单不存在"),
    PAY_STATUS_IS_ERROR(8501,"订单状态错误"),


    MEMBER_PHONE_IS_NULL(5001,"手机号为空"),
    MEMBER_PASSWORD_IS_NULL(5002,"密码为空"),
    MEMBER_NICKNAME_IS_NULL(5003,"昵称为空"),
    MEMBER_MAIL_IS_NULL(5004,"邮箱为空"),
    MEMBER_SMS_CODE_IS_ERROR(5005,"验证码错误!!!"),
    MEMBER_PHONE_IS_ERROR(5006,"手机号格式错误"),
    MEMBER_INFO_IS_NULL(5007,"会员信息为空"),
    MEMBER_INFO_IS_EXIST(5008,"会员已存在"),
    MEMBER_LOGIN_INFO_IS_EXIST(5009,"登录信息为空！"),
    MEMBER_LOGIN_MEMBER_NAME_NOT_EXIST(5010,"用户名不存在！"),
    MEMBER_LOGIN_PASSWORD_IS_ERROR(5011,"密码错误！"),
    MEMBER_MEMBER_NAME_IS_NULL(5012,"会员名为空！"),
    MEMBER_PHONE_IS_EXIST(5014,"手机号已存在"),
    MEMBER_MAIL_IS_EXIST(5016,"邮箱已存在"),
    MEMBER_MAIL_FORMAT_ERROR(5017,"邮箱格式不正确"),
    MEMBER_FIND_PASSWORD_IS_NULL(5200,"信息为空"),
    MEMBER_FIND_PASSWORD_CODE_ERROR(5201,"验证码错误"),
    MEMBER_FIND_PASSWORD_MAIL_ERROR(5201,"邮箱错误"),
    MEMBER_UPDATE_PASSWORD_IS_NULL(5300,"会员信息为空"),
    MEMBER_UPDATE_PASSWORD_TWO_IS_ERROR(5302,"两次输入的密码不一致"),
    MEMBER_OLD_PASSWORD_IS_ERROR(5301,"旧密码错误"),
    MEMBER_OLD_INACTIVE(5303,"用户未激活"),
    MEMBER_ACTIVE_MAIL_IS_NULL(5304,"激活邮箱为空"),
    CART_SKU_IS_NULL(6000,"商品不存在"),
    CART_IS_UP_DOWN(6001,"商品下架"),
    CART_SKU_STOCK_IS_ERROR(6002,"商品库存错误"),
    CART_SKU_IS_LIMIT(6003,"商品限购"),
    CART_SKU_ERROR(6004,"操作不合法"),
    CART_BATCH_DELETE_NO_SELECT(6005,"请选择要删除的数据"),

    ORDER_ADD_CONSIGNEE_IS_NULL(6100,"收件人信息不能为空"),

    ORDER_INFO_STOCK_IS_ERROR(7000,"库存不足"),
    ORDER_SJR_IS_NULL(7001,"请选择收件人"),
    ORDER_STATUS_IS_ERROR(7002,"操作错误"),


    TOKEN_IS_ERROR(8000,"请求错误"),


    MEMBER_LOGIN_TOKEN_IS_MISS(5500,"头信息丢失！"),
    MEMBER_LOGIN_TOKEN_IS_NOT_FULL(5501,"头信息不完整！"),
    MEMBER_LOGIN_TOKEN_IS_FALL(5502,"验签失败！"),
    MEMBER_LOGIN_TOKEN_IS_TIME_OUT(5503,"登录超时！"),

    DELETE_IS_ERROR(2000,"删除数据异常"),

    USERNAME_IS_EMPTY(1000,"用户名为空"),
    PASSWORD_IS_EMPTY(1001,"密码为空"),
    USERNAME_IS_ERROR(1002,"用户名错误"),
    PASSWORD_IS_ERROR(1003,"密码错误"),
    TWO_PASSWORD_IS_ERROR(1004,"两次输入的密码不一致")
    ;

    private int code;

    private String message;

    //必须是私有的
    private ResponseEnum(int code,String message){
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
