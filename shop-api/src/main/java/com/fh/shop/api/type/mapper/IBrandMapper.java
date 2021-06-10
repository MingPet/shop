package com.fh.shop.api.type.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.api.type.param.BrandQueryParam;
import com.fh.shop.api.type.po.Brand;

import java.util.List;
import java.util.Set;

public interface IBrandMapper extends BaseMapper<Brand> {

    public void addBrand(Brand brand);

    Long findListCount(BrandQueryParam brandQueryParam);

    List<Brand> findPageList(BrandQueryParam brandQueryParam);

    void delete(Long id);

    Brand findBrandById(Long id);

    void update(Brand brand);

    List<String> findBrandListByIdList(List<Long> idList);

    void deleteBatch(List<Long> idList);

    List<Brand> findAllBrand();

    List<Brand> findBrandAllList();

    List<Brand> findBrandListByTypeId(Long typeId);


    List<Brand> findBrandListByTypeIdList(Set<Long> typeIdList);
}
