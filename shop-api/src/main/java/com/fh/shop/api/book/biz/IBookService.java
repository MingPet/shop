package com.fh.shop.api.book.biz;

import com.fh.shop.api.book.param.BookQueryParam;
import com.fh.shop.api.book.po.Book;
import com.fh.shop.common.DataTableResult;
import com.fh.shop.common.ServerResponse;

public interface IBookService {
    ServerResponse addBook(Book book);

    DataTableResult queryList(BookQueryParam bookQueryParam);

    ServerResponse findBook(Long id);

    ServerResponse updateBook(Book book);

    ServerResponse deleteBook(Long id);

    ServerResponse deleteBatch(String ids);
}
