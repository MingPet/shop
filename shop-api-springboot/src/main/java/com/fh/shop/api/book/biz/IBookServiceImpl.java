package com.fh.shop.api.book.biz;

import com.fh.shop.api.book.mapper.IBookMapper;
import com.fh.shop.api.book.param.BookQueryParam;
import com.fh.shop.api.book.po.Book;
import com.fh.shop.common.DataTableResult;
import com.fh.shop.common.ServerResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service("bookService")
public class IBookServiceImpl implements IBookService {
    @Resource
    private IBookMapper bookMapper;

    @Override
    public ServerResponse addBook(Book book) {
        bookMapper.insert(book);
        return ServerResponse.success();
    }

    @Override
    public DataTableResult queryList(BookQueryParam bookQueryParam) {
        // 业务逻辑
        // 计算总条数
        Long  count  =  bookMapper.queryListCount(bookQueryParam);
        //获取分页类表
        List<Book> bookList =  bookMapper.queryListPage(bookQueryParam);
        //组装数据
        return new DataTableResult(bookQueryParam.getDraw(),count ,count ,bookList);

    }

    @Override
    public ServerResponse findBook(Long id) {
        Book book = bookMapper.selectById(id);
        return ServerResponse.success(book);
    }

    @Override
    public ServerResponse updateBook(Book book) {
        bookMapper.updateById(book);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse deleteBook(Long id) {
         bookMapper.deleteById(id);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse deleteBatch(String ids) {
        if(StringUtils.isEmpty(ids)){
            return ServerResponse.error();
        }
        List<Long> idsList = Arrays.stream(ids.split(",")).map(x -> Long.parseLong(x)).collect(Collectors.toList());
        bookMapper.deleteBatchIds(idsList);
        return ServerResponse.success();
    }
}
