package com.fh.shop.api.bookInfo.controller;

import com.fh.shop.api.bookInfo.biz.IBookInfoService;
import com.fh.shop.api.bookInfo.po.BookInfo;
import com.fh.shop.common.BookServerResponse;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api")
@Api(tags = "MinioController图书2接口", description = "MinIO对象存储管理")
public class BookInfoController {

    @Resource(name = "bookInfoService")
    private IBookInfoService bookInfoService;


    @PostMapping("/book/addBook")
    public BookServerResponse addBook(@RequestBody BookInfo bookInfo){
        return bookInfoService.addBook(bookInfo);

    }


}
