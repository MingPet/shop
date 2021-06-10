package com.fh.shop.api.type.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.api.type.param.TypeQueryParam;
import com.fh.shop.api.type.po.Type;

import java.util.List;

public interface ITypeMapper extends BaseMapper<Type> {

    void addType(Type type);

    Long findCount(TypeQueryParam typeQueryParam);

    List<Type> findList(TypeQueryParam typeQueryParam);

    Type findType(Long id);

    void updateType(Type type);

    void deleteTypeById(Long id);

    void deleteBatch(List<Long> idList);

    List<Type> findAllTypeList();
}
