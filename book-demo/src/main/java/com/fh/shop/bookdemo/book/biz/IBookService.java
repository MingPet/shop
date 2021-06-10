package com.fh.shop.bookdemo.book.biz;

import com.fh.shop.bookdemo.book.param.BookParam;
import com.fh.shop.bookdemo.book.param.BookQueryParam;
import com.fh.shop.bookdemo.book.po.Book;
import com.fh.shop.common.ServerResponse;

public interface IBookService {
    ServerResponse addBook(BookParam bookParam);

    ServerResponse findBook(Long id);

    ServerResponse updateBook(Book book);

    ServerResponse deleteBook(Long id);

    ServerResponse deleteBatch(String ids);

    ServerResponse findList(BookQueryParam bookQueryParam);

}
