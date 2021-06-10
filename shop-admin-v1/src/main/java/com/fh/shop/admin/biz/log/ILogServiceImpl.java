package com.fh.shop.admin.biz.log;



import com.fh.shop.common.DataTableResult;
import com.fh.shop.admin.mapper.log.ILogMapper;
import com.fh.shop.admin.param.LogQueryParam;
import com.fh.shop.admin.po.log.Log;
import com.fh.shop.util.DateUtil;
import com.fh.shop.admin.vo.log.LogVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("logService")
public class ILogServiceImpl implements ILogService{

    @Autowired
    private ILogMapper logMapper;

    public void addLog(Log log){
        logMapper.addLog(log);
    }

    @Override
    public DataTableResult findList(LogQueryParam logQueryParam) {

        //获取总条数
        Long count = logMapper.findListCount(logQueryParam);
        //获取分页后得数据
        List<Log> logList = logMapper.findList(logQueryParam);

        //po转vo
        List<LogVo> logVolist = new ArrayList<>();
        for (Log log : logList) {
            LogVo logVo = new LogVo();
            logVo.setId(log.getId());
            logVo.setUserName(log.getUserName());
            logVo.setRealName(log.getRealName());
            logVo.setInfo(log.getInfo());
            logVo.setParamInfo(log.getParamInfo());
            logVo.setInsertTime(DateUtil.date2str(log.getInsertTime(),DateUtil.FULL_YEAR));
            logVolist.add(logVo);

        }


        //组装结果
        return new DataTableResult(logQueryParam.getDraw(),count,count,logVolist);


    }

    @Override
    public List<Log> findAllList(LogQueryParam logQueryParam) {
        List<Log> logList = logMapper.findAllList(logQueryParam);
        return logList;
    }


}
