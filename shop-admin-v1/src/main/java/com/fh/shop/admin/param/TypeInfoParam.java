package com.fh.shop.admin.param;

import com.fh.shop.admin.po.type.*;
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
