package com.fh.shop.api.type.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.api.type.po.Attr;

import java.util.List;

public interface IAttrMapper extends BaseMapper<Attr> {

    public void addAttr(Attr attr);

    List<Attr> findAattrListByTypeId(Long id);

    void deleteByTypeId(Long id);

    void deleteBatch(List<Long> idList);

    //List<Attr> findAttrBatchId(List<Long> idList);

    List<Long> findAttrIds(List<Long> idList);

    List<Attr> findAttrBatchId(List<Long> idList);
}
