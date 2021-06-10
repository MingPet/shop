package com.fh.shop.admin.param;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class SpuQueryParam extends Page implements Serializable {

    private String spuName;

    private BigDecimal minPrice;

    private BigDecimal maxPrice;

    private Integer minStock;

    private Integer maxStock;

    private Long brandId;

    private Long cate1;

    private Long cate2;

    private Long cate3;
}
