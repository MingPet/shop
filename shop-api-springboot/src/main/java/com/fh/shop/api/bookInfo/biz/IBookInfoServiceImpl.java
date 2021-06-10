package com.fh.shop.api.bookInfo.biz;

import com.fh.shop.api.bookInfo.mapper.IBookInfoMapper;
import com.fh.shop.api.bookInfo.po.BookInfo;
import com.fh.shop.common.BookServerResponse;
import com.fh.shop.common.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service("bookInfoService")
@Transactional(rollbackFor = Exception.class)
public class IBookInfoServiceImpl implements IBookInfoService {

    @Autowired
    private IBookInfoMapper bookInfoMapper;

    @Override
    public BookServerResponse addBook(BookInfo bookInfo) {
        bookInfo.setInsertTime(new Date());
//        bookInfo.setPublishDate(DataUtil.Date2str());
        bookInfoMapper.insert(bookInfo);
        return BookServerResponse.success();
    }
}
