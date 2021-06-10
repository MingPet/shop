package com.fh.shop.admin.param;

import java.io.Serializable;

public class TypeParam implements Serializable {

    private Long id;

    private String typeName;

    private String brandIds;

    private String specIds;

    private String attrNames;

    private String attrValues;


    public String getAttrNames() {
        return attrNames;
    }

    public void setAttrNames(String attrNames) {
        this.attrNames = attrNames;
    }

    public String getAttrValues() {
        return attrValues;
    }

    public void setAttrValues(String attrValues) {
        this.attrValues = attrValues;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getBrandIds() {
        return brandIds;
    }

    public void setBrandIds(String brandIds) {
        this.brandIds = brandIds;
    }

    public String getSpecIds() {
        return specIds;
    }

    public void setSpecIds(String specIds) {
        this.specIds = specIds;
    }
}
