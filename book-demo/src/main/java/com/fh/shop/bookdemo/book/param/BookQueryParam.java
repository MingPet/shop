package com.fh.shop.bookdemo.book.param;

import com.fh.shop.common.Page;
import lombok.Data;

import java.io.Serializable;

@Data
public class BookQueryParam extends Page implements Serializable {



    private String bookName;

    private String minPrice;

    private String maxPrice;
}
