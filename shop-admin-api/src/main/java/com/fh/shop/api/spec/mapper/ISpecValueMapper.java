package com.fh.shop.api.spec.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.api.spec.po.SpecValue;

import java.util.List;

public interface ISpecValueMapper extends BaseMapper<SpecValue> {

    void addSpecValue(SpecValue specValue);

    List<SpecValue> findSpecValueBySpecId(Long id);

    void deleteSpecValueBySpecId(Long id);

    void deleteBatch(List<Long> idList);

    void addBatch(List<SpecValue> specValueList);

    List<SpecValue> findSpecValueByid(Long id);

    void deleteSpecValueById(Long id);
}
