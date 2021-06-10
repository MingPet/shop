package com.fh.shop.admin.mapper.type;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.admin.po.type.TypeBrand;

import java.util.List;

public interface ITypeBrandMapper extends BaseMapper<TypeBrand> {

    void addTypeBrand(List<TypeBrand> typeBrandList);

    List<Long> findIds(Long id);

    void deleteBrandByTypeId(Long id);

    void deleteBatch(List<Long> idList);
}
