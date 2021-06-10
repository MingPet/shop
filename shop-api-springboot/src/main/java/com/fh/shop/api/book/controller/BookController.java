package com.fh.shop.api.book.controller;

import com.fh.shop.api.book.biz.IBookService;
import com.fh.shop.api.book.param.BookQueryParam;
import com.fh.shop.api.book.po.Book;
import com.fh.shop.common.DataTableResult;
import com.fh.shop.common.ServerResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/api/book")
@CrossOrigin
public class BookController {

    @Resource(name="bookService")
    private IBookService bookService;

    @ResponseBody
    @RequestMapping(value = "/add",method=RequestMethod.POST)
    public ServerResponse add(Book book){

        return bookService.addBook(book);

    }
    @ResponseBody
    @RequestMapping(value="/queryList",method = RequestMethod.POST)
    public DataTableResult queryList(BookQueryParam bookQueryParam){
        DataTableResult dataTableResult = bookService.queryList(bookQueryParam);
        return  dataTableResult;
    }

    @ResponseBody
    @RequestMapping(value="/findBook",method = RequestMethod.POST)
    public ServerResponse findBook(Long id){
        return  bookService.findBook(id);

    }
    @ResponseBody
    @RequestMapping(value="/updateBook",method = RequestMethod.POST)
    public ServerResponse updateBook(Book book){
        return  bookService.updateBook(book);

    }
    @ResponseBody
    @RequestMapping(value="/deleteBook",method = RequestMethod.POST)
    public ServerResponse deleteBook(Long id){
        return  bookService.deleteBook(id);

    }

    @ResponseBody
    @RequestMapping(value="/deleteBatch",method = RequestMethod.POST)
    public ServerResponse deleteBatch(String ids){
        return  bookService.deleteBatch(ids);
    }



}
