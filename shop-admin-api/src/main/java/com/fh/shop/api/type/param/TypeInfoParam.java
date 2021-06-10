package com.fh.shop.api.type.param;

import com.fh.shop.api.type.po.Type;
import com.fh.shop.api.type.po.TypeBrand;
import com.fh.shop.api.type.po.TypeSpec;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class TypeInfoParam implements Serializable {

    private Type type = new Type();

    private List<TypeBrand> typeBrandList = new ArrayList<>();

    private List<TypeSpec> typeSpecList = new ArrayList<>();

    private List<TypeAttrParam> typeAttrParamList = new ArrayList<>();

    

}
