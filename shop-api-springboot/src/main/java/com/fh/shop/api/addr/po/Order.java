package com.fh.shop.api.addr.po;

import lombok.Data;

import java.io.Serializable;
@Data
public class Order implements Serializable {

    private Long id;

    private String addressee;//收件人

    private String receiverAddress;//收件人地址

    private String phone;//收件人电话

    private String status;//状态

    private Long memberId;//外键


}
