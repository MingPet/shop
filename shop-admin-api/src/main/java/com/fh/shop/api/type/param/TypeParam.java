package com.fh.shop.api.type.param;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class TypeParam implements Serializable {


    private Long id;

    private String typeName;

    private List<Long> checkedBrandList = new ArrayList<>();

    private List<Long> checkedSpecList = new ArrayList<>();

    private List<AttrParam> attrParamList = new ArrayList<>();

}
