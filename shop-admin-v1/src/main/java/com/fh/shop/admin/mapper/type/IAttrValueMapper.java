package com.fh.shop.admin.mapper.type;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.admin.po.type.AttrValue;

import java.util.ArrayList;
import java.util.List;

public interface IAttrValueMapper extends BaseMapper<AttrValue> {

    public void addBatch(List<AttrValue> attrValueList);


    List<AttrValue> findAttrValueListByAttrIdList(List<Long> attrIdList);

    void deleteByAttrIdList(ArrayList<Long> attrIdList);

    void deleteBatch(List<Long> attrIdLIst);
}
