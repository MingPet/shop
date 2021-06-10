package com.fh.shop.bookdemo.book.controller;

import com.fh.shop.bookdemo.book.biz.IBookService;
import com.fh.shop.bookdemo.book.param.BookParam;
import com.fh.shop.bookdemo.book.param.BookQueryParam;
import com.fh.shop.bookdemo.book.po.Book;
import com.fh.shop.common.ServerResponse;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/books")
@Api(tags = "图书接口")
@CrossOrigin
public class BookController {

    @Resource(name = "bookService")
    private IBookService bookService;

    //添加图书
    @ResponseBody
    @PostMapping(value = "/add")
    public ServerResponse add(@RequestBody BookParam bookParam){

        System.out.println("cccccccccccccccccssssssssssssss");
        return bookService.addBook(bookParam);


    }

    //回显
    @GetMapping("/{id}")
    public ServerResponse findBook(@PathVariable Long id){
        return  bookService.findBook(id);

    }
    //修改

    @PutMapping
    public ServerResponse updateBook(@RequestBody Book book){
        return  bookService.updateBook(book);
    }

    //删除
    @DeleteMapping
    public ServerResponse deleteBook(Long id){
        return  bookService.deleteBook(id);
    }

    //批删
    @PostMapping("/deleteBatch")
    public ServerResponse deleteBatch(String ids){
        return  bookService.deleteBatch(ids);
    }

    //查询
    @GetMapping("/findList")
    public ServerResponse findList(BookQueryParam bookQueryParam){
        return  bookService.findList(bookQueryParam);
    }


}
