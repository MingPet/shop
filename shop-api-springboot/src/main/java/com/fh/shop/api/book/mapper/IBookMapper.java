package com.fh.shop.api.book.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.api.book.param.BookQueryParam;
import com.fh.shop.api.book.po.Book;

import java.util.List;

public interface IBookMapper extends BaseMapper<Book> {
    Long queryListCount(BookQueryParam bookQueryParam);

    List<Book> queryListPage(BookQueryParam bookQueryParam);

}
