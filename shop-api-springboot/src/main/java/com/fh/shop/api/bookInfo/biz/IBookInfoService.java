package com.fh.shop.api.bookInfo.biz;

import com.fh.shop.api.bookInfo.po.BookInfo;
import com.fh.shop.common.BookServerResponse;

public interface IBookInfoService  {

    BookServerResponse addBook(BookInfo bookInfo);
}
