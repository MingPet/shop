package com.fh.shop.vo;

import lombok.Data;

import java.io.Serializable;
@Data
public class CartItemVo implements Serializable {

    private Long skuId;

    private String skuImage;

    private String skuName;

    private String price;

    private Long count;

    private String subPrice;


}
