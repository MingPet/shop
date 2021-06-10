package com.fh.shop.bookdemo.book.param;

import com.fh.shop.bookdemo.book.po.Book;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class BookParam extends Book implements Serializable {

    private List<String> subImages = new ArrayList<>();

}
