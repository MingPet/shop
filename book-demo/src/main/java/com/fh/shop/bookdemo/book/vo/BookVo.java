package com.fh.shop.bookdemo.book.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BookVo {
    private Long id;

    private String bookName;

    private String price;

    private String pubDate;

    private String autho;

    private String mainImage;

    private List<String> subImages = new ArrayList<>();
}
