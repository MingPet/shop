package com.fh.shop.bookdemo.book.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.bookdemo.book.param.BookQueryParam;
import com.fh.shop.bookdemo.book.po.Book;

import java.util.List;

public interface IBookMapper extends BaseMapper<Book> {

    List<Book> findList(BookQueryParam bookQueryParam);

    Long findListCount(BookQueryParam bookQueryParam);
}
