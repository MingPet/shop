package com.fh.shop.admin.po.goods;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class Spu implements Serializable {

    private Long id;
    private String spuName;
    private BigDecimal price;
    private Integer stock;
    private Long cate1;
    private Long cate2;
    private Long cate3;
    private Long brandId;
    private String attrInfo;
    private String specInfo;
    private String brandName;
    private String cateName;

    private String  isUp;
    private String isNew;
    private String isRec;


}
