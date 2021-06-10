package com.fh.shop.api.book.controller;

import com.fh.shop.api.book.biz.IBookService;
import com.fh.shop.api.book.param.BookQueryParam;
import com.fh.shop.api.book.po.Book;
import com.fh.shop.common.DataTableResult;
import com.fh.shop.common.ServerResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@CrossOrigin
@RequestMapping("/api")
@Api(tags = "图书rest接口")
@Slf4j
public class BookRestController {

    @Resource(name = "bookService")
    private IBookService bookService;



    //查询
    @ApiOperation("查询图书")
    @RequestMapping(value="/books",method = RequestMethod.GET)
    public DataTableResult queryList(BookQueryParam bookQueryParam){
        return bookService.queryList(bookQueryParam);
    }

    //新增
    @ApiOperation("新增图书")

    @RequestMapping(value = "/books",method = RequestMethod.POST)
    public ServerResponse addBook(Book book){
        log.info("新增图书info");
        log.debug("新增图书debug");
        return bookService.addBook(book);
    }

    //删除
    @ApiOperation("删除图书")
    @ApiImplicitParam(name = "bookId", value = "图书id", example = "0", dataType = "java.lang.Long",paramType = "path")
    @RequestMapping(value = "/books/{bookId}",method = RequestMethod.DELETE)
    public ServerResponse deleteBook(@PathVariable("bookId") Long id){
        return bookService.deleteBook(id);
    }

    //批量删除
    @ApiOperation("批量删除图书")
    @ApiImplicitParam(name = "ids", value = "图书id集合", example = "0", dataType = "java.lang.String",paramType = "query",required = true)
    @RequestMapping(value="/books",method = RequestMethod.DELETE)
    public ServerResponse deleteBatch(String ids){
        return  bookService.deleteBatch(ids);

    }

    //回显
    @ApiOperation("回显图书")
    @ApiImplicitParam(name = "id", value = "图书id", example = "0", dataType = "java.lang.Long",paramType = "query",required = true)
    @RequestMapping(value="/books/{id}",method = RequestMethod.GET)
    public ServerResponse findBook(@PathVariable Long id){
        return  bookService.findBook(id);
    }

    //更新  @RequestBody将json格式的字符串转换为java对象
    @ApiOperation("更新图书")
    @RequestMapping(value="/books",method = RequestMethod.PUT)
    public ServerResponse updateBook(@RequestBody Book book){
        return  bookService.updateBook(book);
    }


}
