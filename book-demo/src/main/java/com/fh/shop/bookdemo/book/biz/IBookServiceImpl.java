package com.fh.shop.bookdemo.book.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.bookdemo.book.mapper.IBookImageMapper;
import com.fh.shop.bookdemo.book.mapper.IBookMapper;
import com.fh.shop.bookdemo.book.param.BookParam;
import com.fh.shop.bookdemo.book.param.BookQueryParam;
import com.fh.shop.bookdemo.book.po.Book;
import com.fh.shop.bookdemo.book.po.BookImage;
import com.fh.shop.bookdemo.book.vo.BookVo;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.common.TableResult;
import com.fh.shop.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service("bookService")
@Transactional(rollbackFor = Exception.class)
public class IBookServiceImpl implements IBookService {

    @Autowired
    private IBookMapper bookMapper;

    @Autowired
    private IBookImageMapper bookImageMapper;
    //添加图书
    @Override
    public ServerResponse addBook(BookParam bookParam) {

        Book book = new Book();
        book.setBookName(bookParam.getBookName());
        book.setAutho(bookParam.getAutho());
        book.setMainImage(bookParam.getMainImage());
        book.setPrice(bookParam.getPrice());
        book.setPubDate(bookParam.getPubDate());
        bookMapper.insert(book);

        Long bookId = book.getId();
        List<String> subImages = bookParam.getSubImages();
        if(subImages.size() >0){
            List<BookImage> bookImageList = subImages.stream().map(x -> {
                BookImage bookImage = new BookImage();
                bookImage.setBookId(bookId);
                bookImage.setImagePath(x);
                return bookImage;
            }).collect(Collectors.toList());
            bookImageMapper.addBatch(bookImageList);
        }
        return ServerResponse.success();
    }

    @Override
    @Transactional(readOnly = true)
    public ServerResponse findBook(Long id) {
        Book book = bookMapper.selectById(id);

        QueryWrapper<BookImage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("bookId",id);
        List<BookImage> bookImages = bookImageMapper.selectList(queryWrapper);
        BookVo bookVo = new BookVo();
        bookVo.setMainImage(book.getMainImage());
        bookVo.setBookName(book.getBookName());
        bookVo.setPrice(book.getPrice().toString());
        bookVo.setAutho(book.getAutho());
        bookVo.setPubDate(DateUtil.date2str(book.getPubDate(), DateUtil.FULL_Y_M));
        List<String> subImageList = bookImages.stream().map(x -> x.getImagePath()).collect(Collectors.toList());

        bookVo.setSubImages(subImageList);

        return ServerResponse.success(bookVo);
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

    @Override
    @Transactional(readOnly = true)
    public ServerResponse findList(BookQueryParam bookQueryParam) {


        //List<Book> books = bookMapper.selectList(null);
        //获取总条数
        Long totalCount = bookMapper.findListCount(bookQueryParam);

        //获取分页列表
        List<Book> books  = bookMapper.findList(bookQueryParam);
        List<BookVo> bookVoList = books.stream().map(x -> {
            BookVo bookVo = new BookVo();
            bookVo.setId(x.getId());
            bookVo.setBookName(x.getBookName());
            bookVo.setAutho(x.getAutho());
            bookVo.setPrice(x.getPrice().toString());
            bookVo.setPubDate(DateUtil.date2str(x.getPubDate(), DateUtil.FULL_Y_M));
            bookVo.setMainImage(x.getMainImage());
            return bookVo;
        }).collect(Collectors.toList());

        TableResult tableResult = new TableResult(totalCount,bookVoList);
        return ServerResponse.success(tableResult);
    }


}
