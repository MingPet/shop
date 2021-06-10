package com.fh.shop.bookdemo.book.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.bookdemo.book.po.BookImage;

import java.util.List;

public interface IBookImageMapper extends BaseMapper<BookImage> {
    void addBatch(List<BookImage> bookImageList);
}
