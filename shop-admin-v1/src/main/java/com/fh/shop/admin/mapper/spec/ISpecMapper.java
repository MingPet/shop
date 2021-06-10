package com.fh.shop.admin.mapper.spec;

import com.fh.shop.admin.param.SpecQueryParam;
import com.fh.shop.admin.po.spec.Spec;

import java.util.List;

public interface ISpecMapper {

    public void addSpec(Spec spec);

    Long findListCount(SpecQueryParam specQueryParam);

    List<Spec> findPageList(SpecQueryParam specQueryParam);

    Spec findSpec(Long id);

    void updateSpec(Spec spec);

    void deleteSpec(Long id);

    void deleteBatch(List<Long> idList);

    List<Spec> findAllSpec();

    List<Spec> findSpecAllList();


    List<Spec> findListByTypeId(Long typeId);
}
