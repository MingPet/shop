package com.fh.shop.admin.vo.type;

import com.fh.shop.admin.po.brand.Brand;
import com.fh.shop.admin.po.spec.Spec;
import com.fh.shop.admin.po.type.Type;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EditTypeVo implements Serializable {

    private String typeName;

    private Type type = new Type();

    private List<Long> brandIdList  = new ArrayList<>();

    private List<Long> specIdList = new ArrayList<>();

    private List<Spec> specList = new ArrayList<>();

    private List<Brand> brandList = new ArrayList<>();

    private List<AttrVo> attrVoList = new ArrayList<>();


    public List<AttrVo> getAttrVoList() {
        return attrVoList;
    }

    public void setAttrVoList(List<AttrVo> attrVoList) {
        this.attrVoList = attrVoList;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Long> getBrandIdList() {
        return brandIdList;
    }

    public void setBrandIdList(List<Long> brandIdList) {
        this.brandIdList = brandIdList;
    }

    public List<Long> getSpecIdList() {
        return specIdList;
    }

    public void setSpecIdList(List<Long> specIdList) {
        this.specIdList = specIdList;
    }

    public List<Spec> getSpecList() {
        return specList;
    }

    public void setSpecList(List<Spec> specList) {
        this.specList = specList;
    }

    public List<Brand> getBrandList() {
        return brandList;
    }

    public void setBrandList(List<Brand> brandList) {
        this.brandList = brandList;
    }
}
