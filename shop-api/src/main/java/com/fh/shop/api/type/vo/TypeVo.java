package com.fh.shop.api.type.vo;

import com.fh.shop.api.spec.po.Spec;
import com.fh.shop.api.type.po.Brand;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class TypeVo implements Serializable {

    private List<Brand> brandList = new ArrayList<>();
    private List<Spec> specList = new ArrayList<>();
}




