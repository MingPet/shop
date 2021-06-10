package com.fh.shop.api.order.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderInfoVo implements Serializable {

   private String id;

   private Long memberId;

   private String totalPrice;//总价格

   private Long totalCount;//订单中商品总个数

   private Integer status;//状态 0：未支付【创建订单成功】10：已支付【未发货】20：已发货【待收货】30：交易成功 40：交易关闭

   private String createTime;//创建时间

   private String recipientName;//收件人姓名

   private String recipientAddr;//收件人地址

   private String recipientPhone;//收件人电话

}
