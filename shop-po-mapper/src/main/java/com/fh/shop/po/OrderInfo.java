package com.fh.shop.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderInfo implements Serializable {

    @TableId(type = IdType.INPUT)
    private String id;

    private Long memberId;

    private BigDecimal totalPrice;//总价格

    private Long totalCount;//订单中商品总个数

    private Integer status;//状态 0：未支付【创建订单成功】10：已支付【未发货】20：已发货【待收货】30：交易成功 40：交易关闭

    private Date createTime;//创建时间

    private Date payTime;//支付时间

    private Date sendTime;//发货时间

    private Date receiveTime;//收货时间

    private String recipientName;//收件人姓名

    private String recipientAddr;//收件人地址

    private String recipientPhone;//收件人电话

    private Integer payType;//支付方式 1：微信 2：支付宝

}
