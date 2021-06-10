package com.fh.shop.admin.biz.brand;

import com.fh.shop.common.DataTableResult;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.admin.param.BrandQueryParam;
import com.fh.shop.admin.po.brand.Brand;

public interface IBrandService {

    void addBrand(Brand brand);

    DataTableResult findList(BrandQueryParam brandQueryParam);

    void delete(Long id,String rootRealPath);

    ServerResponse findBrandById(Long id);

    ServerResponse update(Brand brand,String rootRealPath);

    ServerResponse deleteBatch(String ids, String realPath);

    ServerResponse findBrandList(Long cateId);
}
