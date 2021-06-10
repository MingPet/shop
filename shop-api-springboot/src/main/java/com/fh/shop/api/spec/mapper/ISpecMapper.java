package com.fh.shop.api.spec.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.api.spec.po.Spec;

import java.util.List;

public interface ISpecMapper extends BaseMapper<Spec> {

    public void addSpec(Spec spec);

    Long queryListCount(SpecQueryParam specQueryParam);


    List<Spec> queryListPage(SpecQueryParam specQueryParam);

    Spec findSpec(Long id);

    void updateSpec(Spec spec);

    void deleteSpec(Long id);

    void deleteBatch(List<Long> idList);

    List<Spec> findAllSpecList();

    List<Spec> findByIdTypeId(Long typeId);


}

