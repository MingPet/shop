package com.fh.shop.api.brand.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.api.brand.param.BrandQueryParam;
import com.fh.shop.api.brand.po.Brand;
import com.fh.shop.common.ServerResponse;

import java.util.List;
import java.util.Set;

public interface IBrandMapper extends BaseMapper<Brand> {

    ServerResponse addBrand(Brand brand);

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
