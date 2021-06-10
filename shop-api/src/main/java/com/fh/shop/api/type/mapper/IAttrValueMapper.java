package com.fh.shop.api.type.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.api.type.po.AttrValue;

import java.util.ArrayList;
import java.util.List;

public interface IAttrValueMapper extends BaseMapper<AttrValue> {

    public void addBatch(List<AttrValue> attrValueList);


    List<AttrValue> findAttrValueListByAttrIdList(List<Long> attrIdList);

    void deleteByAttrIdList(ArrayList<Long> attrIdList);

    void deleteBatch(List<Long> attrIdLIst);

}
