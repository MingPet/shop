package com.fh.shop.api.type.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.api.type.po.TypeBrand;

import java.util.List;

public interface ITypeBrandMapper extends BaseMapper<TypeBrand> {

   

    List<Long> findIds(Long id);

    void deleteBrandByTypeId(Long id);

    void deleteBatch(List<Long> idList);

    void addTypeBrand(List<TypeBrand> typeBrandList);

}
