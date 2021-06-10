package com.fh.shop.api.book.controller;

import com.fh.shop.api.book.biz.IBookService;
import com.fh.shop.api.book.param.BookQueryParam;
import com.fh.shop.api.book.po.Book;
import com.fh.shop.common.DataTableResult;
import com.fh.shop.common.ServerResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@CrossOrigin
public class BookRestController {

    @Resource(name = "bookService")
    private IBookService bookService;



    //查询
    @RequestMapping(value="/books",method = RequestMethod.GET)
    public DataTableResult queryList(BookQueryParam bookQueryParam){
        return bookService.queryList(bookQueryParam);
    }

    //新增
    @RequestMapping(value = "/books",method = RequestMethod.POST)
    public ServerResponse addBook(Book book){
        return bookService.addBook(book);
    }

    //删除
    @RequestMapping(value = "/books/{bookId}",method = RequestMethod.DELETE)
    public ServerResponse deleteBook(@PathVariable("bookId") Long id){
        return bookService.deleteBook(id);
    }

    //批量删除
    @RequestMapping(value="/books",method = RequestMethod.DELETE)
    public ServerResponse deleteBatch(String ids){
        return  bookService.deleteBatch(ids);

    }

    //回显
    @RequestMapping(value="/books/{id}",method = RequestMethod.GET)
    public ServerResponse findBook(@PathVariable Long id){
        return  bookService.findBook(id);
    }

    //更新  @RequestBody将json格式的字符串转换为java对象
    @RequestMapping(value="/books",method = RequestMethod.PUT)
    public ServerResponse updateBook(@RequestBody Book book){
        return  bookService.updateBook(book);
    }


}
