package com.fh.shop.api.type.vo;

import com.fh.shop.api.brand.po.Brand;
import com.fh.shop.api.spec.po.Spec;
import com.fh.shop.api.type.param.TypeParam;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class TypeInfoVo extends TypeParam implements Serializable {


    private List<Brand> brandList = new ArrayList<>();

    private List<Spec> specList = new ArrayList<>();

}
