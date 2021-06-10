package com.fh.shop.po;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class OrderItem implements Serializable {

    private Long id;

    private String orderInfoId;

    private Long memberId;

    private Long skuId;

    private String skuName;

    private BigDecimal skuPrice;

    private Long skuCount;

    private String skuImage;

    private BigDecimal subPrice;



}
