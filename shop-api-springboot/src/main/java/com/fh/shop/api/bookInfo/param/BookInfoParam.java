package com.fh.shop.api.bookInfo.param;

import lombok.Data;

import java.io.Serializable;

@Data
public class BookInfoParam implements Serializable {

    private String bookName;

    private String bookPrice;

    private String author;

    private String publishDate;

}
