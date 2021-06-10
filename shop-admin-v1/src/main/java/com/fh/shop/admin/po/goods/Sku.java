package com.fh.shop.admin.po.goods;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class Sku implements Serializable {

    private Long id;
    private Long spuId;
    private String skuName;
    private BigDecimal price;
    private Integer stock;
    private String specInfo;
    private String image;//冗余字段 用来存放默认图片的路径
    private Long colorId;

    private String  isUp;
    private String isNew;
    private String isRec;


}
