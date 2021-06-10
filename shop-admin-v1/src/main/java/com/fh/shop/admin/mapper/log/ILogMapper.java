package com.fh.shop.admin.mapper.log;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.admin.param.LogQueryParam;
import com.fh.shop.admin.po.log.Log;

import java.util.List;

public interface ILogMapper extends BaseMapper<Log> {

    void addLog(Log log);


    Long findListCount(LogQueryParam logQueryParam);

    List<Log> findList(LogQueryParam logQueryParam);

    List<Log> findAllList(LogQueryParam logQueryParam);
}

