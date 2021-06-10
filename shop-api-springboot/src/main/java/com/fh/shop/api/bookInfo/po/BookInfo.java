package com.fh.shop.api.bookInfo.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class BookInfo implements Serializable {

    private Long id;

    private String bookName;

    private String bookPrice;

    private String author;

    @DateTimeFormat(pattern = "yyyy-MM")
    private Date publishDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date insertTime;

}
