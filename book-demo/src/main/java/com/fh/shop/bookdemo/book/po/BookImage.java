package com.fh.shop.bookdemo.book.po;

import lombok.Data;

import java.io.Serializable;
@Data
public class BookImage implements Serializable {

    private Long id;

    private String imagePath;

    private Long bookId;


}
