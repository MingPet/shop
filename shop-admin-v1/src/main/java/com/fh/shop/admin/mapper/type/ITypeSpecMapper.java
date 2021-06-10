package com.fh.shop.admin.mapper.type;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.admin.po.type.TypeSpec;

import java.util.List;

public interface ITypeSpecMapper extends BaseMapper<TypeSpec> {

    void addTypeSpec(List<TypeSpec> specIdsList);

    List<Long> findIds(Long id);

    void deleteSpecByTypeId(Long id);

    void deleteBatch(List<Long> idList);
}
