package com.fh.shop.api.spec.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.api.spec.po.SpecValue;

import java.util.List;

public interface ISpecValueMapper  extends BaseMapper<SpecValue> {

    public void addSpecValue(SpecValue specValue);


    List<SpecValue> findSpecValueByid(Long id);

    void deleteSpecValueById(Long id);

    void deleteBatch(List<Long> idList);



    void addBatch(List<SpecValue> specValueList);
}
