package com.fh.shop.api.brand.biz;

import com.fh.shop.api.brand.param.BrandQueryParam;
import com.fh.shop.common.ServerResponse;

public interface IBrandService {

    ServerResponse addBrand(com.fh.shop.api.brand.po.Brand brand);

    ServerResponse findList(BrandQueryParam brandQueryParam);

    void delete(Long id, String rootRealPath);

    ServerResponse findBrandById(Long id);

    ServerResponse update(com.fh.shop.api.brand.po.Brand brand, String rootRealPath);

    ServerResponse deleteBatch(String ids, String realPath);

    ServerResponse findBrandList(Long cateId);
}
