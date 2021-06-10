package com.fh.shop.bookdemo.book.po;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Book  {

    private Long id;

    private String bookName;

    private BigDecimal price;//价格


    private Date pubDate;//出版时间 精确到月

    private String autho;//作者

    private String mainImage;//主图片


}
