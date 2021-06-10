package com.fh.shop.api.spec.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.api.spec.param.SpecQueryParam;
import com.fh.shop.api.spec.po.Spec;

import java.util.List;

public interface ISpecMapper extends BaseMapper<Spec> {

    void addSpec(Spec spec);

    Long findListCount(SpecQueryParam specQueryParam);

    List<Spec> findPageList(SpecQueryParam specQueryParam);

    Spec findSpec(Long id);

    void updateSpec(Spec spec);

    void deleteSpec(Long id);

    void deleteBatch(List<Long> idList);

    List<Spec> findAllSpec();

    List<Spec> findSpecAllList();


    List<Spec> findListByTypeId(Long typeId);

    Long queryListCount(SpecQueryParam specQueryParam);

    List<Spec> queryListPage(SpecQueryParam specQueryParam);
}
